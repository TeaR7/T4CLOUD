package com.t4cloud.t.user.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.EasyPoiUtil;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


/**
 * 用户表 控制器
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-19
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "用户表", tags = "用户表接口", position = 10)
@RequestMapping("/SysUser")
public class SysUserController extends T4Controller<SysUser, ISysUserService> {

    /**
     * 详情
     */
    @AutoLog(value = "用户表-详情", operateType = 4)
    @GetMapping("/detail")
    @RequiresPermissions("user:SysUser:VIEW")
    @ApiOperation(position = 1, value = "用户表-详情", notes = "传入sysUser")
    public R<SysUser> detail(SysUser sysUser, HttpServletRequest req) {
        QueryWrapper<SysUser> sysUserQueryWapper = T4Query.initQuery(sysUser, req.getParameterMap());
        SysUser detail = service.getOne(sysUserQueryWapper);
        return R.ok("用户表-详情查询成功", detail);
    }

    /**
     * 全部列表 用户表
     */
    @AutoLog(value = "用户表-全部列表", operateType = 4)
    @GetMapping("/list")
    @RequiresPermissions("user:SysUser:VIEW")
    @ApiOperation(position = 2, value = "用户表-全部列表", notes = "传入sysUser")
    public R<List<SysUser>> list(SysUser sysUser, HttpServletRequest req) {
        QueryWrapper<SysUser> sysUserQueryWapper = T4Query.initQuery(sysUser, req.getParameterMap());
        List<SysUser> list = service.list(sysUserQueryWapper);
        return R.ok("用户表-全部列表查询成功", list);
    }

    /**
     * 分页查询 用户表
     */
    @AutoLog(value = "用户表-分页查询", operateType = 4)
    @GetMapping("/page")
    @RequiresPermissions("user:SysUser:VIEW")
    @ApiOperation(position = 3, value = "用户表-分页查询", notes = "传入sysUser")
    public R<IPage<SysUser>> page(SysUser sysUser,
                                  @ApiParam(name = "pageNo", required = false)
                                  @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                  @ApiParam(name = "pageSize", required = false)
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  HttpServletRequest req) {
        QueryWrapper<SysUser> sysUserQueryWapper = T4Query.initQuery(sysUser, req.getParameterMap());
        IPage<SysUser> pages = service.page(new Page<>(pageNo, pageSize), sysUserQueryWapper);
        return R.ok("用户表-分页查询查询成功", pages);
    }

    /**
     * 新增 用户表
     */
    @AutoLog(value = "用户表-新增", operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("user:SysUser:ADD")
    @ApiOperation(position = 4, value = "用户表-新增", notes = "传入sysUser")
    public R save(@Valid @RequestBody SysUser sysUser, BindingResult bindingResult) {
        return R.ok("用户表-新增成功", service.save(sysUser));
    }

    /**
     * 修改 用户表
     */
    @AutoLog(value = "用户表-修改", operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"user:SysUser:ADD", "user:SysUser:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "用户表-修改", notes = "传入sysUser")
    public R update(@Valid @RequestBody SysUser sysUser, BindingResult bindingResult) {
        return R.ok("用户表-修改成功", service.updateById(sysUser));
    }


    /**
     * 删除 用户表
     */
    @AutoLog(value = "用户表-删除", operateType = 2)
    @DeleteMapping("/delete")
    @RequiresRoles("ADMIN")
    @RequiresPermissions("user:SysUser:DELETE")
    @ApiOperation(position = 8, value = "用户表-删除", notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.ok("用户表-删除成功", service.removeByIds(Arrays.asList(ids.split(","))));
    }


    @AutoLog(value = "用户表-导出", operateType = 4)
    @GetMapping("/export")
    @RequiresPermissions("user:SysUser:EXPORT")
    @ApiOperation(position = 9, value = "用户表-导出", notes = "传入查询条件")
    public void export(SysUser sysUser,
                       @ApiParam("指定导出数据的id") @RequestParam(required = false) String selectedRowKeys,
                       @ApiParam("需要导出列") @RequestParam(required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<SysUser> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(sysUser, req.getParameterMap()) : new QueryWrapper<SysUser>().in("id", selectedRowKeys.split(","));
        super.exportExl("用户表", selectedColKeys, queryWrapper);
    }


    @AutoLog(value = "用户表-导入", operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("user:SysUser:IMPORT")
    @ApiOperation(position = 10, value = "用户表-导入", notes = "传入查询条件")
    public R importXls(@ApiParam("文件对象") MultipartFile file) {
        return R.ok("用户表-导入成功", service.saveBatch(EasyPoiUtil.importExcel(file, SysUser.class)));
    }


}
