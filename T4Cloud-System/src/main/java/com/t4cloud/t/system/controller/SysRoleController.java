package com.t4cloud.t.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.LoginUser;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.UserUtil;
import com.t4cloud.t.system.entity.SysRole;
import com.t4cloud.t.system.service.ISysRoleService;
import com.t4cloud.t.system.service.ISysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 角色表 控制器
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "角色表", tags = "角色表接口", position = 11)
@RequestMapping("/SysRole")
public class SysRoleController extends T4Controller<SysRole, ISysRoleService> {

    @Autowired
    private final ISysUserRoleService sysUserRoleService;

    /**
     * 详情
     */
    @AutoLog(value = "角色表-详情", operateType = 4)
    @GetMapping("/detail")
    @RequiresPermissions("system:SysRole:VIEW")
    @ApiOperation(position = 1, value = "角色表-详情", notes = "传入sysRole")
    public R<SysRole> detail(SysRole sysRole, HttpServletRequest req) {
        QueryWrapper<SysRole> sysRoleQueryWrapper = T4Query.initQuery(sysRole, req.getParameterMap());
        SysRole detail = service.getOne(sysRoleQueryWrapper);
        return R.ok("角色表-详情查询成功", detail);
    }

    /**
     * 全部列表 角色表
     */
    @AutoLog(value = "角色表-全部列表", operateType = 4)
    @GetMapping("/list")
    @RequiresPermissions("system:SysRole:VIEW")
    @ApiOperation(position = 2, value = "角色表-全部列表", notes = "传入sysRole")
    public R<List<SysRole>> list(SysRole sysRole, HttpServletRequest req) {
        QueryWrapper<SysRole> sysRoleQueryWrapper = T4Query.initQuery(sysRole, req.getParameterMap());
        List<SysRole> list = service.list(sysRoleQueryWrapper);
        return R.ok("角色表-全部列表查询成功", list);
    }

    /**
     * 分页查询 角色表
     */
    @AutoLog(value = "角色表-分页查询", operateType = 4)
    @GetMapping("/page")
    @RequiresPermissions("system:SysRole:VIEW")
    @ApiOperation(position = 3, value = "角色表-分页查询", notes = "传入sysRole")
    public R<IPage<SysRole>> page(SysRole sysRole,
                                  @ApiParam(name = "pageNo", required = false)
                                  @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                  @ApiParam(name = "pageSize", required = false)
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  HttpServletRequest req) {
        QueryWrapper<SysRole> sysRoleQueryWrapper = T4Query.initQuery(sysRole, req.getParameterMap());
        IPage<SysRole> pages = service.page(new Page<>(pageNo, pageSize), sysRoleQueryWrapper);
        return R.ok("角色表-分页查询查询成功", pages);
    }

    /**
     * 新增 角色表
     */
    @AutoLog(value = "角色表-新增", operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("system:SysRole:ADD")
    @ApiOperation(position = 4, value = "角色表-新增", notes = "传入sysRole")
    public R save(@Valid @RequestBody SysRole sysRole, BindingResult bindingResult) {

        return R.ok("角色表-新增成功", service.save(sysRole));
    }

    /**
     * 修改 角色表
     */
    @AutoLog(value = "角色表-修改", operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"system:SysRole:ADD", "system:SysRole:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "角色表-修改", notes = "传入sysRole")
    public R update(@Valid @RequestBody SysRole sysRole, BindingResult bindingResult) {
        return R.ok("角色表-修改成功", service.updateById(sysRole));
    }


    /**
     * 删除 角色表
     */
    @AutoLog(value = "角色表-删除", operateType = 2)
    @DeleteMapping("/delete")
    @RequiresRoles("ADMIN")
    @RequiresPermissions("system:SysRole:DELETE")
    @ApiOperation(position = 8, value = "角色表-删除", notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.ok("角色表-删除成功", service.removeByIds(Arrays.asList(ids.split(","))));
    }


    /**
     * 获取用户角色权限
     */
    @AutoLog(value = "获取用户角色权限")
    @GetMapping("/userRole")
    @ApiOperation(position = 9, value = "角色表-获取用户角色权限", notes = "通过TOKEN获取用户角色权限")
    public R userRole() {
        LoginUser currentUser = UserUtil.getCurrentUser();
        //获取用户角色的绑定关系
        List<SysRole> sysRoleList = sysUserRoleService.getUserRoleList(currentUser.getId());
        return R.ok("获取用户角色权限成功", sysRoleList);
    }

    /**
     * 校验角色编码唯一
     */
    @AutoLog(value = "角色码-确认重复信息")
    @GetMapping("/check")
    @RequiresPermissions("system:SysDict:ADD")
    @ApiOperation(position = 11, value = "角色码-确认重复信息", notes = "传入查询属性名和属性值")
    public R check(
            @ApiParam("属性名") @RequestParam("key") String key,
            @ApiParam("属性值") @RequestParam("value") String value) {
        Boolean checkProp = checkProp(key, value);
        return R.ok(key + "：" + value + (checkProp ? "可用，没有冲突" : "不可用，该数据已存在"), checkProp);
    }

    /**
     * 获取APP-USER的用户角色
     */
    @AutoLog(value = "角色码-获取APP-USER的用户角色")
    @GetMapping("/appUserRole")
    @ApiOperation(position = 11, value = "角色码-获取APP-USER的用户角色", notes = "传入role - code")
    public R<SysRole> appUserRole(@RequestParam String roleCode) {
        return R.ok("角色获取成功", service.appUserRole(roleCode));
    }


}
