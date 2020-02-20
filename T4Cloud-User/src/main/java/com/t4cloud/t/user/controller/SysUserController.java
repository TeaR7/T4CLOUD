package com.t4cloud.t.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.service.ISysUserService;
import com.t4cloud.t.base.controller.T4Controller;

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
@Api(value = "用户表", tags = "用户表接口")
@RequestMapping("/SysUser")
public class SysUserController extends T4Controller<SysUser, ISysUserService> {

	/**
	 * 详情
	 */
    @AutoLog(value = "用户表-详情", operateType = 4)
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@RequiresPermissions(value = {"user:SysUser:VIEW"})
	@ApiOperation(value = "用户表-详情", notes = "传入sysUser")
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
	@ApiOperationSupport(order = 2)
	@RequiresPermissions(value = {"user:SysUser:VIEW"})
	@ApiOperation(value = "用户表-全部列表", notes = "传入sysUser")
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
	@ApiOperationSupport(order = 3)
	@RequiresPermissions(value = {"user:SysUser:VIEW"})
	@ApiOperation(value = "用户表-分页查询", notes = "传入sysUser")
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
	@ApiOperationSupport(order = 4)
	@RequiresPermissions(value = {"user:SysUser:ADD"})
	@ApiOperation(value = "用户表-新增", notes = "传入sysUser")
	public R save(@Valid @RequestBody SysUser sysUser) {
		return R.ok("用户表-新增成功", service.save(sysUser));
	}

	/**
	 * 修改 用户表
	 */
    @AutoLog(value = "用户表-修改", operateType = 3)
    @PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@RequiresPermissions(value = {"user:SysUser:ADD","user:SysUser:EDIT"}, logical = Logical.OR)
	@ApiOperation(value = "用户表-修改", notes = "传入sysUser")
	public R update(@Valid @RequestBody SysUser sysUser) {
		return R.ok("用户表-修改成功", service.updateById(sysUser));
	}


	/**
	 * 删除 用户表
	 */
    @AutoLog(value = "用户表-删除", operateType = 2)
	@DeleteMapping("/delete")
	@ApiOperationSupport(order = 8)
	@RequiresRoles("ADMIN")
	@RequiresPermissions(value = {"user:SysUser:DELETE"})
	@ApiOperation(value = "用户表-删除", notes = "传入ids")
	public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.ok("用户表-删除成功", service.removeByIds(Arrays.asList(ids.split(","))));
	}


}
