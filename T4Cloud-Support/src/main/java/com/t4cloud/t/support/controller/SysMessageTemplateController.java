package com.t4cloud.t.support.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.EasyPoiUtil;
import com.t4cloud.t.support.entity.SysMessageTemplate;
import com.t4cloud.t.support.service.ISysMessageTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 消息模板 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-16 
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "消息模板", tags = "消息模板接口", position = 10)
@RequestMapping("/SysMessageTemplate")
public class SysMessageTemplateController extends T4Controller<SysMessageTemplate, ISysMessageTemplateService> {

	/**
	 * 详情
	 */
    @AutoLog(value = "消息模板-详情")
	@GetMapping("/detail")
	@RequiresPermissions("support:SysMessageTemplate:VIEW")
	@ApiOperation(position = 1, value = "消息模板-详情", notes = "传入sysMessageTemplate")
	public R<SysMessageTemplate> detail(SysMessageTemplate sysMessageTemplate, HttpServletRequest req) {
		QueryWrapper<SysMessageTemplate> sysMessageTemplateQueryWapper = T4Query.initQuery(sysMessageTemplate, req.getParameterMap());
		SysMessageTemplate detail = service.getOne(sysMessageTemplateQueryWapper);
		return R.ok("消息模板-详情查询成功", detail);
	}

	/**
	 * 全部列表 消息模板
	 */
    @AutoLog(value = "消息模板-全部列表")
	@GetMapping("/list")
	@RequiresPermissions("support:SysMessageTemplate:VIEW")
	@ApiOperation(position = 2, value = "消息模板-全部列表", notes = "传入sysMessageTemplate")
	public R<List<SysMessageTemplate>> list(SysMessageTemplate sysMessageTemplate, HttpServletRequest req) {
		QueryWrapper<SysMessageTemplate> sysMessageTemplateQueryWapper = T4Query.initQuery(sysMessageTemplate, req.getParameterMap());
		List<SysMessageTemplate> list = service.list(sysMessageTemplateQueryWapper);
		return R.ok("消息模板-全部列表查询成功", list);
	}

	/**
	 * 分页查询 消息模板
	 */
	@AutoLog(value = "消息模板-分页查询")
	@GetMapping("/page")
	@RequiresPermissions("support:SysMessageTemplate:VIEW")
	@ApiOperation(position = 3, value = "消息模板-分页查询", notes = "传入sysMessageTemplate")
	public R<IPage<SysMessageTemplate>> page(SysMessageTemplate sysMessageTemplate,
                                             @ApiParam(name = "pageNo")
									@RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                             @ApiParam(name = "pageSize")
									@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                             HttpServletRequest req) {
			QueryWrapper<SysMessageTemplate> sysMessageTemplateQueryWapper = T4Query.initQuery(sysMessageTemplate, req.getParameterMap());
			IPage<SysMessageTemplate> pages = service.page(new Page<>(pageNo, pageSize), sysMessageTemplateQueryWapper);
			return R.ok("消息模板-分页查询查询成功", pages);
	}

	/**
	 * 新增 消息模板
	 */
    @AutoLog(value = "消息模板-新增", operateType = 1)
	@PutMapping("/save")
	@RequiresPermissions("support:SysMessageTemplate:ADD")
	@ApiOperation(position = 4, value = "消息模板-新增", notes = "传入sysMessageTemplate")
	public R save(@Valid @RequestBody SysMessageTemplate sysMessageTemplate, BindingResult bindingResult) {
		return R.ok("消息模板-新增成功", service.save(sysMessageTemplate));
	}

	/**
	 * 修改 消息模板
	 */
    @AutoLog(value = "消息模板-修改", operateType = 3)
    @PostMapping("/update")
	@RequiresPermissions(value = {"support:SysMessageTemplate:ADD","support:SysMessageTemplate:EDIT"}, logical = Logical.OR)
	@ApiOperation(position = 5, value = "消息模板-修改", notes = "传入sysMessageTemplate")
	public R update(@RequestBody SysMessageTemplate sysMessageTemplate) {
		return R.ok("消息模板-修改成功", service.updateById(sysMessageTemplate));
	}


	/**
	 * 删除 消息模板
	 */
    @AutoLog(value = "消息模板-删除", operateType = 2)
	@DeleteMapping("/delete")
	@RequiresPermissions("support:SysMessageTemplate:DELETE")
	@ApiOperation(position = 8, value = "消息模板-删除", notes = "传入ids")
	public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam(name = "ids") String ids) {
		return R.ok("消息模板-删除成功", service.removeByIds(Arrays.asList(ids.split(","))));

	}


	/**
	 * 导出 消息模板
	 */
	@AutoLog(value = "消息模板-导出")
	@GetMapping("/export")
	@RequiresPermissions("support:SysMessageTemplate:EXPORT")
	@ApiOperation(position = 9, value = "消息模板-导出", notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
	public void export(SysMessageTemplate sysMessageTemplate,
					   @ApiParam("选中导出的数据") @RequestParam(name = "selectedRowKeys", required = false) String selectedRowKeys,
					   @ApiParam("自定义导出列") @RequestParam(name = "selectedColKeys", required = false) String selectedColKeys,
		               HttpServletRequest req) {
		QueryWrapper<SysMessageTemplate> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(sysMessageTemplate, req.getParameterMap()) : new QueryWrapper<SysMessageTemplate>().in("id", selectedRowKeys.split(","));
		super.exportExl("消息模板", selectedColKeys, queryWrapper);
	}

	/**
	 * 导入 消息模板
	 */
	@AutoLog(value = "消息模板-导入", operateType = 1)
	@PutMapping("/import")
	@RequiresPermissions("support:SysMessageTemplate:IMPORT")
	@ApiOperation(position = 10, value = "消息模板-导入", notes = "传入填好的模板文件即可")
	public R importXls(@ApiParam("EXCEL文件") MultipartFile file) {
		return R.ok("消息模板-导入成功", service.saveBatch(EasyPoiUtil.importExcel(file, SysMessageTemplate.class)));
	}

	@AutoLog(value = "消息模板-确认重复信息")
	@GetMapping("/check")
	@RequiresPermissions("support:SysMessageTemplate:ADD")
	@ApiOperation(position = 11, value = "消息模板-确认重复信息", notes = "传入查询属性名和属性值")
	public R check(
			@ApiParam("属性名") @RequestParam("key") String key,
			@ApiParam("属性值") @RequestParam("value") String value) {
		Boolean checkProp = checkProp(key, value);
		return R.ok(key + "：" + value + (checkProp ? "可用，无重名项" : "不可用，存在冲突"), checkProp);
	}

}
