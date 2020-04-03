package com.t4cloud.t.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.annotation.cache.DictCacheEvict;
import com.t4cloud.t.base.annotation.cache.DictCacheable;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.EasyPoiUtil;
import com.t4cloud.t.system.entity.SysDict;
import com.t4cloud.t.system.entity.SysDictValue;
import com.t4cloud.t.system.service.ISysDictService;
import com.t4cloud.t.system.service.ISysDictValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 字典 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-04
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "字典", tags = "字典接口", position = 14)
@RequestMapping("/SysDict")
public class SysDictController extends T4Controller<SysDict, ISysDictService> {


    @Autowired
    private final ISysDictValueService sysDictValueService;

    /**
     * 详情
     */
    @AutoLog(value = "字典-详情")
    @GetMapping("/detail")
    @RequiresPermissions("system:SysDict:VIEW")
    @ApiOperation(position = 1, value = "字典-详情", notes = "传入sysDict")
    public R<SysDict> detail(SysDict sysDict, HttpServletRequest req) {
        QueryWrapper<SysDict> sysDictQueryWapper = T4Query.initQuery(sysDict, req.getParameterMap());
        SysDict detail = service.getOne(sysDictQueryWapper);
        return R.ok("字典-详情查询成功", detail);
    }

    /**
     * 全部列表 字典
     */
    @AutoLog(value = "字典-全部列表")
    @GetMapping("/list")
    @RequiresPermissions("system:SysDict:VIEW")
    @ApiOperation(position = 2, value = "字典-全部列表", notes = "传入sysDict")
    public R<List<SysDict>> list(SysDict sysDict, HttpServletRequest req) {
        QueryWrapper<SysDict> sysDictQueryWapper = T4Query.initQuery(sysDict, req.getParameterMap());
        List<SysDict> list = service.list(sysDictQueryWapper);
        return R.ok("字典-全部列表查询成功", list);
    }

    /**
     * 分页查询 字典
     */
    @AutoLog(value = "字典-分页查询")
    @GetMapping("/page")
    @RequiresPermissions("system:SysDict:VIEW")
    @ApiOperation(position = 3, value = "字典-分页查询", notes = "传入sysDict")
    public R<IPage<SysDict>> page(SysDict sysDict,
                                  @ApiParam(name = "pageNo")
                                  @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                  @ApiParam(name = "pageSize")
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  HttpServletRequest req) {
        QueryWrapper<SysDict> sysDictQueryWapper = T4Query.initQuery(sysDict, req.getParameterMap());
        IPage<SysDict> pages = service.page(new Page<>(pageNo, pageSize), sysDictQueryWapper);
        return R.ok("字典-分页查询查询成功", pages);
    }

    /**
     * 新增 字典
     */
    @AutoLog(value = "字典-新增", operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("system:SysDict:ADD")
    @ApiOperation(position = 4, value = "字典-新增", notes = "传入sysDict")
    public R save(@Valid @RequestBody SysDict sysDict, BindingResult bindingResult) {
        return R.ok("字典-新增成功", service.save(sysDict));
    }

    /**
     * 修改 字典
     */
    @AutoLog(value = "字典-修改", operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"system:SysDict:ADD", "system:SysDict:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "字典-修改", notes = "传入sysDict")
    @DictCacheEvict
    public R update(@RequestBody SysDict sysDict) {
        return R.ok("字典-修改成功", service.updateById(sysDict));
    }


    /**
     * 删除 字典
     */
    @AutoLog(value = "字典-删除", operateType = 2)
    @DeleteMapping("/delete")
    @RequiresPermissions("system:SysDict:DELETE")
    @ApiOperation(position = 8, value = "字典-删除", notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.ok("字典-删除成功", service.removeByIds(Arrays.asList(ids.split(","))));

    }


    /**
     * 导出 字典
     */
    @AutoLog(value = "字典-导出")
    @GetMapping("/export")
    @RequiresPermissions("system:SysDict:EXPORT")
    @ApiOperation(position = 9, value = "字典-导出", notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
    public void export(SysDict sysDict,
                       @ApiParam("选中导出的数据") @RequestParam(required = false) String selectedRowKeys,
                       @ApiParam("自定义导出列") @RequestParam(required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<SysDict> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(sysDict, req.getParameterMap()) : new QueryWrapper<SysDict>().in("id", selectedRowKeys.split(","));
        super.exportExl("字典", selectedColKeys, queryWrapper);
    }

    /**
     * 导入 字典
     */
    @AutoLog(value = "字典-导入", operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("system:SysDict:IMPORT")
    @ApiOperation(position = 10, value = "字典-导入", notes = "传入填好的模板文件即可")
    public R importXls(@ApiParam("EXCEL文件") MultipartFile file) {
        return R.ok("字典-导入成功", service.saveBatch(EasyPoiUtil.importExcel(file, SysDict.class)));
    }

    @AutoLog(value = "字典-确认重复信息")
    @GetMapping("/check")
    @RequiresPermissions("system:SysDict:ADD")
    @ApiOperation(position = 11, value = "字典-确认重复信息", notes = "传入查询属性名和属性值")
    public R check(
            @ApiParam("属性名") @RequestParam("key") String key,
            @ApiParam("属性值") @RequestParam("value") String value) {
        Boolean checkProp = checkProp(key, value);
        return R.ok(key + "：" + value + (checkProp ? "可用，无重名项" : "不可用，存在冲突"), checkProp);
    }

    /**
     * 查询所有值
     */
    @AutoLog(value = "字典-查询所有值")
    @GetMapping("/dictValue/{code}")
    @ApiOperation(position = 20, value = "字典-查询所有值", notes = "传入字典对应的CODE")
    @DictCacheable
    public R<List<SysDictValue>> dictValue(@ApiParam("字典对应的CODE") @PathVariable("code") String code) {
        SysDict sysDict = service.lambdaQuery().eq(SysDict::getCode, code).one();
        if (sysDict == null) {
            return R.error("该字典不存在");
        }
        List<SysDictValue> sysDictValueList = sysDictValueService.lambdaQuery().select(SysDictValue::getText, SysDictValue::getValue, SysDictValue::getPosition, SysDictValue::getStatus).eq(SysDictValue::getDictId, sysDict.getId()).list();
        return R.ok("字典-查询所有值成功", sysDictValueList);
    }

}
