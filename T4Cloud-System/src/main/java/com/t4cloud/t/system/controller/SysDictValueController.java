package com.t4cloud.t.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.annotation.cache.DictCacheEvict;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.EasyPoiUtil;
import com.t4cloud.t.system.entity.SysDictValue;
import com.t4cloud.t.system.service.ISysDictValueService;
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
 * 字典详细值 控制器
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
@Api(value = "字典详细值" , tags = "字典详细值接口" , position = 15)
@RequestMapping("/SysDictValue")
public class SysDictValueController extends T4Controller<SysDictValue, ISysDictValueService> {

    /**
     * 详情
     */
    @AutoLog(value = "字典详细值-详情")
    @GetMapping("/detail")
    @RequiresPermissions("system:SysDictValue:VIEW")
    @ApiOperation(position = 1, value = "字典详细值-详情" , notes = "传入sysDictValue")
    public R<SysDictValue> detail(SysDictValue sysDictValue, HttpServletRequest req) {
        QueryWrapper<SysDictValue> sysDictValueQueryWrapper = T4Query.initQuery(sysDictValue, req.getParameterMap());
        SysDictValue detail = service.getOne(sysDictValueQueryWrapper);
        return R.ok("字典详细值-详情查询成功" , detail);
    }

    /**
     * 全部列表 字典详细值
     */
    @AutoLog(value = "字典详细值-全部列表")
    @GetMapping("/list")
    @RequiresPermissions("system:SysDictValue:VIEW")
    @ApiOperation(position = 2, value = "字典详细值-全部列表" , notes = "传入sysDictValue")
    public R<List<SysDictValue>> list(SysDictValue sysDictValue, HttpServletRequest req) {
        QueryWrapper<SysDictValue> sysDictValueQueryWrapper = T4Query.initQuery(sysDictValue, req.getParameterMap());
        List<SysDictValue> list = service.list(sysDictValueQueryWrapper);
        return R.ok("字典详细值-全部列表查询成功" , list);
    }

    /**
     * 分页查询 字典详细值
     */
    @AutoLog(value = "字典详细值-分页查询")
    @GetMapping("/page")
    @RequiresPermissions("system:SysDictValue:VIEW")
    @ApiOperation(position = 3, value = "字典详细值-分页查询" , notes = "传入sysDictValue")
    public R<IPage<SysDictValue>> page(SysDictValue sysDictValue,
                                       @ApiParam(name = "pageNo")
                                       @RequestParam(name = "pageNo" , required = false, defaultValue = "1") Integer pageNo,
                                       @ApiParam(name = "pageSize")
                                       @RequestParam(name = "pageSize" , required = false, defaultValue = "10") Integer pageSize,
                                       HttpServletRequest req) {
        QueryWrapper<SysDictValue> sysDictValueQueryWrapper = T4Query.initQuery(sysDictValue, req.getParameterMap());
        IPage<SysDictValue> pages = service.page(new Page<>(pageNo, pageSize), sysDictValueQueryWrapper);
        return R.ok("字典详细值-分页查询查询成功" , pages);
    }

    /**
     * 新增 字典详细值
     */
    @AutoLog(value = "字典详细值-新增" , operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("system:SysDictValue:ADD")
    @ApiOperation(position = 4, value = "字典详细值-新增" , notes = "传入sysDictValue")
    @DictCacheEvict
    public R save(@Valid @RequestBody SysDictValue sysDictValue, BindingResult bindingResult) {
        return R.ok("字典详细值-新增成功" , service.save(sysDictValue));
    }

    /**
     * 修改 字典详细值
     */
    @AutoLog(value = "字典详细值-修改" , operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"system:SysDictValue:ADD" , "system:SysDictValue:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "字典详细值-修改" , notes = "传入sysDictValue")
    @DictCacheEvict
    public R update(@RequestBody SysDictValue sysDictValue) {
        return R.ok("字典详细值-修改成功" , service.updateById(sysDictValue));
    }


    /**
     * 删除 字典详细值
     */
    @AutoLog(value = "字典详细值-删除" , operateType = 2)
    @DeleteMapping("/delete")
    @RequiresRoles("ADMIN")
    @RequiresPermissions("system:SysDictValue:DELETE")
    @ApiOperation(position = 8, value = "字典详细值-删除" , notes = "传入ids")
    @DictCacheEvict
    public R delete(@ApiParam(value = "主键集合" , required = true) @RequestParam String ids) {
        return R.ok("字典详细值-删除成功" , service.removeByIds(Arrays.asList(ids.split(","))));

    }


    /**
     * 导出 字典详细值
     */
    @AutoLog(value = "字典详细值-导出")
    @GetMapping("/export")
    @RequiresPermissions("system:SysDictValue:EXPORT")
    @ApiOperation(position = 9, value = "字典详细值-导出" , notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
    public void export(SysDictValue sysDictValue,
                       @ApiParam("选中导出的数据") @RequestParam(required = false) String selectedRowKeys,
                       @ApiParam("自定义导出列") @RequestParam(required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<SysDictValue> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(sysDictValue, req.getParameterMap()) : new QueryWrapper<SysDictValue>().in("id" , selectedRowKeys.split(","));
        super.exportExl("字典详细值" , selectedColKeys, queryWrapper);
    }

    /**
     * 导入 字典详细值
     */
    @AutoLog(value = "字典详细值-导入" , operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("system:SysDictValue:IMPORT")
    @ApiOperation(position = 10, value = "字典详细值-导入" , notes = "传入填好的模板文件即可")
    @DictCacheEvict
    public R importXls(@ApiParam("EXCEL文件") MultipartFile file) {
        return R.ok("字典详细值-导入成功" , service.saveBatch(EasyPoiUtil.importExcel(file, SysDictValue.class)));
    }

}
