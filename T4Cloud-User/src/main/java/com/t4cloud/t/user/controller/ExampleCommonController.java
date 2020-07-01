package com.t4cloud.t.user.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.annotation.RSA;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.EasyPoiUtil;
import com.t4cloud.t.user.entity.ExampleCommon;
import com.t4cloud.t.user.service.IExampleCommonService;
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
 * 通用示例 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-31
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "通用示例" , tags = "通用示例接口" , position = 10)
@RequestMapping("/ExampleCommon")
public class ExampleCommonController extends T4Controller<ExampleCommon, IExampleCommonService> {

    /**
     * 详情
     */
    @AutoLog(value = "通用示例-详情")
    @GetMapping("/detail")
    @RequiresPermissions("user:ExampleCommon:VIEW")
    @ApiOperation(position = 1, value = "通用示例-详情" , notes = "传入exampleCommon")
    public R<ExampleCommon> detail(@RSA ExampleCommon exampleCommon, HttpServletRequest req) {
        QueryWrapper<ExampleCommon> exampleCommonQueryWrapper = T4Query.initQuery(exampleCommon, req.getParameterMap());
        ExampleCommon detail = service.getOne(exampleCommonQueryWrapper);
        return R.ok("通用示例-详情查询成功" , detail);
    }

    /**
     * 全部列表 通用示例
     */
    @AutoLog(value = "通用示例-全部列表")
    @GetMapping("/list")
    @RequiresPermissions("user:ExampleCommon:VIEW")
    @ApiOperation(position = 2, value = "通用示例-全部列表" , notes = "传入exampleCommon")
    public R<List<ExampleCommon>> list(@RSA ExampleCommon exampleCommon, HttpServletRequest req) {
        QueryWrapper<ExampleCommon> exampleCommonQueryWrapper = T4Query.initQuery(exampleCommon, req.getParameterMap());
        List<ExampleCommon> list = service.list(exampleCommonQueryWrapper);
        return R.ok("通用示例-全部列表查询成功" , list);
    }

    /**
     * 分页查询 通用示例
     */
    @AutoLog(value = "通用示例-分页查询")
    @GetMapping("/page")
    @RequiresPermissions("user:ExampleCommon:VIEW")
    @ApiOperation(position = 3, value = "通用示例-分页查询" , notes = "传入exampleCommon")
    public R<IPage<ExampleCommon>> page(@RSA ExampleCommon exampleCommon,
                                        @ApiParam(name = "pageNo")
                                        @RequestParam(name = "pageNo" , required = false, defaultValue = "1") Integer pageNo,
                                        @ApiParam(name = "pageSize")
                                        @RequestParam(name = "pageSize" , required = false, defaultValue = "10") Integer pageSize,
                                        HttpServletRequest req) {
        QueryWrapper<ExampleCommon> exampleCommonQueryWrapper = T4Query.initQuery(exampleCommon, req.getParameterMap());
        IPage<ExampleCommon> pages = service.page(new Page<>(pageNo, pageSize), exampleCommonQueryWrapper);
        return R.ok("通用示例-分页查询查询成功" , pages);
    }

    /**
     * 新增 通用示例
     */
    @AutoLog(value = "通用示例-新增" , operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("user:ExampleCommon:ADD")
    @ApiOperation(position = 4, value = "通用示例-新增" , notes = "传入exampleCommon")
    public R save(@Valid @RequestBody @RSA ExampleCommon exampleCommon, BindingResult bindingResult) {
        return R.ok("通用示例-新增成功" , service.save(exampleCommon));
    }

    /**
     * 修改 通用示例
     */
    @AutoLog(value = "通用示例-修改" , operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"user:ExampleCommon:ADD" , "user:ExampleCommon:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "通用示例-修改" , notes = "传入exampleCommon")
    public R update(@RequestBody @RSA ExampleCommon exampleCommon) {
        return R.ok("通用示例-修改成功" , service.updateById(exampleCommon));
    }


    /**
     * 删除 通用示例
     */
    @AutoLog(value = "通用示例-删除" , operateType = 2)
    @DeleteMapping("/delete")
    @RequiresPermissions("user:ExampleCommon:DELETE")
    @ApiOperation(position = 8, value = "通用示例-删除" , notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合" , required = true) @RequestParam(name = "ids") String ids) {
        return R.ok("通用示例-删除成功" , service.removeByIds(Arrays.asList(ids.split(","))));

    }


    /**
     * 导出 通用示例
     */
    @AutoLog(value = "通用示例-导出")
    @GetMapping("/export")
    @RequiresPermissions("user:ExampleCommon:EXPORT")
    @ApiOperation(position = 9, value = "通用示例-导出" , notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
    public void export(@RSA ExampleCommon exampleCommon,
                       @ApiParam("选中导出的数据") @RequestParam(name = "selectedRowKeys" , required = false) String selectedRowKeys,
                       @ApiParam("自定义导出列") @RequestParam(name = "selectedColKeys" , required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<ExampleCommon> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(exampleCommon, req.getParameterMap()) : new QueryWrapper<ExampleCommon>().in("id" , selectedRowKeys.split(","));
        super.exportExl("通用示例" , selectedColKeys, queryWrapper);
    }

    /**
     * 导入 通用示例
     */
    @AutoLog(value = "通用示例-导入" , operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("user:ExampleCommon:IMPORT")
    @ApiOperation(position = 10, value = "通用示例-导入" , notes = "传入填好的模板文件即可")
    public R importXls(@ApiParam("EXCEL文件") MultipartFile file) {
        return R.ok("通用示例-导入成功" , service.saveBatch(EasyPoiUtil.importExcel(file, ExampleCommon.class)));
    }

}
