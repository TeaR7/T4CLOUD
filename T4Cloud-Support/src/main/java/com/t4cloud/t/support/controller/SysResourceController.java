package com.t4cloud.t.support.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.support.entity.SysResource;
import com.t4cloud.t.support.service.ISysResourceService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 资源管理表 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-02-24 
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "资源管理表", tags = "资源管理表接口", position = 10)
@RequestMapping("/SysResource")
public class SysResourceController extends T4Controller<SysResource, ISysResourceService> {

	/**
	 * 详情
	 */
    @AutoLog(value = "资源管理表-详情", operateType = 4)
	@GetMapping("/detail")
	@RequiresPermissions("support:SysResource:VIEW")
	@ApiOperation(position = 1, value = "资源管理表-详情", notes = "传入sysResource")
	public R<SysResource> detail(SysResource sysResource, HttpServletRequest req) {
		QueryWrapper<SysResource> sysResourceQueryWapper = T4Query.initQuery(sysResource, req.getParameterMap());
		SysResource detail = service.getOne(sysResourceQueryWapper);
		return R.ok("资源管理表-详情查询成功", detail);
	}

	/**
	 * 全部列表 资源管理表
	 */
    @AutoLog(value = "资源管理表-全部列表", operateType = 4)
	@GetMapping("/list")
	@RequiresPermissions("support:SysResource:VIEW")
	@ApiOperation(position = 2, value = "资源管理表-全部列表", notes = "传入sysResource")
	public R<List<SysResource>> list(SysResource sysResource, HttpServletRequest req) {
		QueryWrapper<SysResource> sysResourceQueryWapper = T4Query.initQuery(sysResource, req.getParameterMap());
		List<SysResource> list = service.list(sysResourceQueryWapper);
		return R.ok("资源管理表-全部列表查询成功", list);
	}

	/**
	 * 分页查询 资源管理表
	 */
    @AutoLog(value = "资源管理表-分页查询", operateType = 4)
	@GetMapping("/page")
	@RequiresPermissions("support:SysResource:VIEW")
	@ApiOperation(position = 3, value = "资源管理表-分页查询", notes = "传入sysResource")
	public R<IPage<SysResource>> page(SysResource sysResource,
										@ApiParam(name = "pageNo", required = false)
										@RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
										@ApiParam(name = "pageSize", required = false)
										@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
										HttpServletRequest req) {
		QueryWrapper<SysResource> sysResourceQueryWapper = T4Query.initQuery(sysResource, req.getParameterMap());
		IPage<SysResource> pages = service.page(new Page<>(pageNo, pageSize), sysResourceQueryWapper);
		return R.ok("资源管理表-分页查询查询成功", pages);
	}

	/**
	 * 新增 资源管理表
	 */
    @AutoLog(value = "资源管理表-新增", operateType = 1)
	@PutMapping("/save")
	@RequiresPermissions("support:SysResource:ADD")
	@ApiOperation(position = 4, value = "资源管理表-新增", notes = "传入sysResource")
	public R save(@Valid @RequestBody SysResource sysResource, BindingResult bindingResult) {
		return R.ok("资源管理表-新增成功", service.save(sysResource));
	}

	/**
	 * 修改 资源管理表
	 */
    @AutoLog(value = "资源管理表-修改", operateType = 3)
    @PostMapping("/update")
	@RequiresPermissions(value = {"support:SysResource:ADD","support:SysResource:EDIT"}, logical = Logical.OR)
	@ApiOperation(position = 5, value = "资源管理表-修改", notes = "传入sysResource")
	public R update(@Valid @RequestBody SysResource sysResource, BindingResult bindingResult) {
		return R.ok("资源管理表-修改成功", service.updateById(sysResource));
	}


	/**
	 * 删除 资源管理表
	 */
    @AutoLog(value = "资源管理表-删除", operateType = 2)
	@DeleteMapping("/delete")
	@RequiresRoles("ADMIN")
	@RequiresPermissions("support:SysResource:DELETE")
	@ApiOperation(position = 8, value = "资源管理表-删除", notes = "传入ids")
	public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.ok("资源管理表-删除成功", service.removeByIds(Arrays.asList(ids.split(","))));
	}


	/**
	 * 导出 资源管理表
	 */
	@AutoLog(value = "资源管理表-导出", operateType = 4)
	@GetMapping("/export")
	@RequiresPermissions("support:SysResource:EXPORT")
	@ApiOperation(position = 9, value = "资源管理表-导出", notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
	public void export(SysResource sysResource,
					   @ApiParam("选中导出的数据") @RequestParam(required = false) String selectedRowKeys,
					   @ApiParam("自定义导出列") @RequestParam(required = false) String selectedColKeys,
		               HttpServletResponse resp, HttpServletRequest req) {
		QueryWrapper<SysResource> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(sysResource, req.getParameterMap()) : new QueryWrapper<SysResource>().in("id", selectedRowKeys.split(","));
		List<SysResource> list = service.list(queryWrapper);
//		super.exportExl("资源管理表", list, selectedColKeys, resp);
	}

}
