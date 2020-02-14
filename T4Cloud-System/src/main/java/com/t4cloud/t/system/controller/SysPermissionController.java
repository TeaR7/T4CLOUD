package com.t4cloud.t.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.LoginUser;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.UserUtil;
import com.t4cloud.t.system.entity.SysPermission;
import com.t4cloud.t.system.service.ISysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表 控制器
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-13
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "菜单权限表", tags = "菜单权限表接口")
@RequestMapping("/SysPermission")
public class SysPermissionController extends T4Controller<SysPermission, ISysPermissionService> {

    /**
     * 详情
     */
    @AutoLog(value = "菜单权限表-详情", operateType = 4)
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @RequiresPermissions(value = {"system:SysPermission:VIEW"})
    @ApiOperation(value = "菜单权限表-详情", notes = "传入sysPermission")
    public R<SysPermission> detail(SysPermission sysPermission, HttpServletRequest req) {
        QueryWrapper<SysPermission> sysPermissionQueryWapper = T4Query.initQuery(sysPermission, req.getParameterMap());
        SysPermission detail = service.getOne(sysPermissionQueryWapper);
        return R.ok("菜单权限表-详情查询成功", detail);
    }

    /**
     * 全部列表 菜单权限表
     */
    @AutoLog(value = "菜单权限表-全部列表", operateType = 4)
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @RequiresPermissions(value = {"system:SysPermission:VIEW"})
    @ApiOperation(value = "菜单权限表-全部列表", notes = "传入sysPermission")
    public R<List<SysPermission>> list(SysPermission sysPermission, HttpServletRequest req) {
        QueryWrapper<SysPermission> sysPermissionQueryWapper = T4Query.initQuery(sysPermission, req.getParameterMap());
        List<SysPermission> list = service.list(sysPermissionQueryWapper);
        return R.ok("菜单权限表-全部列表查询成功", list);
    }

    /**
     * 分页查询 菜单权限表
     */
    @AutoLog(value = "菜单权限表-分页查询", operateType = 4)
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @RequiresPermissions(value = {"system:SysPermission:VIEW"})
    @ApiOperation(value = "菜单权限表-分页查询", notes = "传入sysPermission")
    public R<IPage<SysPermission>> page(SysPermission sysPermission,
                                        @ApiParam(name = "pageNo", required = false)
                                        @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                        @ApiParam(name = "pageSize", required = false)
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                        HttpServletRequest req) {
        QueryWrapper<SysPermission> sysPermissionQueryWapper = T4Query.initQuery(sysPermission, req.getParameterMap());
        IPage<SysPermission> pages = service.page(new Page<>(pageNo, pageSize), sysPermissionQueryWapper);
        return R.ok("菜单权限表-分页查询查询成功", pages);
    }

    /**
     * 新增 菜单权限表
     */
    @AutoLog(value = "菜单权限表-新增", operateType = 1)
    @PutMapping("/save")
    @ApiOperationSupport(order = 4)
    @RequiresPermissions(value = {"system:SysPermission:ADD"})
    @ApiOperation(value = "菜单权限表-新增", notes = "传入sysPermission")
    public R save(@Valid @RequestBody SysPermission sysPermission) {
        return R.ok("菜单权限表-新增成功", service.save(sysPermission));
    }

    /**
     * 修改 菜单权限表
     */
    @AutoLog(value = "菜单权限表-修改", operateType = 3)
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @RequiresPermissions(value = {"system:SysPermission:ADD", "system:SysPermission:EDIT"}, logical = Logical.OR)
    @ApiOperation(value = "菜单权限表-修改", notes = "传入sysPermission")
    public R update(@Valid @RequestBody SysPermission sysPermission) {
        return R.ok("菜单权限表-修改成功", service.updateById(sysPermission));
    }


    /**
     * 删除 菜单权限表
     */
    @AutoLog(value = "菜单权限表-删除", operateType = 2)
    @DeleteMapping("/delete")
    @ApiOperationSupport(order = 8)
    @RequiresRoles("ADMIN")
    @RequiresPermissions(value = {"system:SysPermission:DELETE"})
    @ApiOperation(value = "菜单权限表-删除", notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.ok("菜单权限表-删除成功", service.removeByIds(Arrays.asList(ids.split(","))));
    }


    /**
     * 详情
     */
    @AutoLog(value = "获取用户菜单权限", operateType = 4)
    @GetMapping("/userPermission")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "获取用户菜单权限", notes = "通过TOKEN获取用户菜单权限")
    public R<List<SysPermission>> userPermission() {
        LoginUser currentUser = UserUtil.getCurrentUser();

        //查询用户有的所有权限
        List<SysPermission> metaList = service.queryByUsername(currentUser.getUsername());

        //添加首页路由
        boolean hasHome = false;
        for (SysPermission sysPermission : metaList) {
            if ("首页".equals(sysPermission.getName())) {
                hasHome = true;
                break;
            }
        }
        if (!hasHome) {
            metaList.add(0, SysPermission.getHomePage());
        }


        // 将数据处理成树状结构

        //获取根目录
        List<SysPermission> menuList = metaList.stream().filter(item -> StringUtils.isEmpty(item.getParentId())).collect(Collectors.toList());
        //排序
        if (menuList == null || menuList.size() == 0) {
            menuList = new ArrayList<>();
        } else {
            menuList.stream().sorted(Comparator.comparing(SysPermission:: getSortNo)).collect(Collectors.toList());
        }
        findChildren(menuList, metaList);

        return R.ok("用户权限查询成功", menuList);
    }

    /**
     * 将权限列表转换为树状结构
     *
     * @param list    树状结构一级节点
     * @param allData 原权限列表
     *                <p>
     * @return void
     * --------------------
     * @author TeaR
     * @date 2020/2/13 14:33
     */
    private void findChildren(List<SysPermission> list, List<SysPermission> allData) {
        for (SysPermission sysPermission : list) {
            List<SysPermission> permissions = allData.stream().filter(item -> sysPermission.getId().equalsIgnoreCase(item.getParentId())).collect(Collectors.toList());
            if (permissions == null || permissions.size() == 0) {
                continue;
            }
            //排序
            permissions = permissions.stream().sorted(Comparator.comparing(SysPermission:: getSortNo)).collect(Collectors.toList());
            findChildren(permissions, allData);
            sysPermission.setChildren(permissions);
        }
    }


}
