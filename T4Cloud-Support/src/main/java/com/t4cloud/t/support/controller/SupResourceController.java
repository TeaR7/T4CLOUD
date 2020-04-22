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
import com.t4cloud.t.support.entity.SupResource;
import com.t4cloud.t.support.service.ISupResourceService;
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
 * 资源管理表 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-06
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "资源管理表" , tags = "资源管理表接口" , position = 4)
@RequestMapping("/SupResource")
public class SupResourceController extends T4Controller<SupResource, ISupResourceService> {

    /**
     * 详情
     */
    @AutoLog(value = "资源管理表-详情")
    @GetMapping("/detail")
    @RequiresPermissions("support:SupResource:VIEW")
    @ApiOperation(position = 1, value = "资源管理表-详情" , notes = "传入SupResource")
    public R<SupResource> detail(SupResource supResource, HttpServletRequest req) {
        QueryWrapper<SupResource> supResourceQueryWrapper = T4Query.initQuery(supResource, req.getParameterMap());
        SupResource detail = service.getOne(supResourceQueryWrapper);
        return R.ok("资源管理表-详情查询成功" , detail);
    }

    /**
     * 全部列表 资源管理表
     */
    @AutoLog(value = "资源管理表-全部列表")
    @GetMapping("/list")
    @RequiresPermissions("support:SupResource:VIEW")
    @ApiOperation(position = 2, value = "资源管理表-全部列表" , notes = "传入SupResource")
    public R<List<SupResource>> list(SupResource supResource, HttpServletRequest req) {
        QueryWrapper<SupResource> supResourceQueryWrapper = T4Query.initQuery(supResource, req.getParameterMap());
        List<SupResource> list = service.list(supResourceQueryWrapper);
        return R.ok("资源管理表-全部列表查询成功" , list);
    }

    /**
     * 分页查询 资源管理表
     */
    @AutoLog(value = "资源管理表-分页查询")
    @GetMapping("/page")
    @RequiresPermissions("support:SupResource:VIEW")
    @ApiOperation(position = 3, value = "资源管理表-分页查询" , notes = "传入SupResource")
    public R<IPage<SupResource>> page(SupResource supResource,
                                      @ApiParam(name = "pageNo")
                                      @RequestParam(name = "pageNo" , required = false, defaultValue = "1") Integer pageNo,
                                      @ApiParam(name = "pageSize")
                                      @RequestParam(name = "pageSize" , required = false, defaultValue = "10") Integer pageSize,
                                      HttpServletRequest req) {
        QueryWrapper<SupResource> supResourceQueryWrapper = T4Query.initQuery(supResource, req.getParameterMap());
        IPage<SupResource> pages = service.page(new Page<>(pageNo, pageSize), supResourceQueryWrapper);
        return R.ok("资源管理表-分页查询查询成功" , pages);
    }

    /**
     * 新增 资源管理表
     */
    @AutoLog(value = "资源管理表-新增" , operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("support:SupResource:ADD")
    @ApiOperation(position = 4, value = "资源管理表-新增" , notes = "传入SupResource")
    public R save(@Valid @RequestBody SupResource supResource, BindingResult bindingResult) {
        return R.ok("资源管理表-新增成功" , service.save(supResource));
    }

    /**
     * 修改 资源管理表
     */
    @AutoLog(value = "资源管理表-修改" , operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"support:SupResource:ADD" , "support:SupResource:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "资源管理表-修改" , notes = "传入SupResource")
    public R update(@Valid @RequestBody SupResource supResource, BindingResult bindingResult) {
        return R.ok("资源管理表-修改成功" , service.updateById(supResource));
    }


    /**
     * 删除 资源管理表
     */
    @AutoLog(value = "资源管理表-删除" , operateType = 2)
    @DeleteMapping("/delete")
    @RequiresPermissions("support:SupResource:DELETE")
    @ApiOperation(position = 8, value = "资源管理表-删除" , notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合" , required = true) @RequestParam(name = "ids") String ids) {
        return R.ok("资源管理表-删除成功" , service.removeByIds(Arrays.asList(ids.split(","))));

    }


    /**
     * 导出 资源管理表
     */
    @AutoLog(value = "资源管理表-导出")
    @GetMapping("/export")
    @RequiresPermissions("support:SupResource:EXPORT")
    @ApiOperation(position = 9, value = "资源管理表-导出" , notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
    public void export(SupResource supResource,
                       @ApiParam("选中导出的数据") @RequestParam(name = "selectedRowKeys" , required = false) String selectedRowKeys,
                       @ApiParam("自定义导出列") @RequestParam(name = "selectedColKeys" , required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<SupResource> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(supResource, req.getParameterMap()) : new QueryWrapper<SupResource>().in("id" , selectedRowKeys.split(","));
        super.exportExl("资源管理表" , selectedColKeys, queryWrapper);
    }

    /**
     * 导入 资源管理表
     */
    @AutoLog(value = "资源管理表-导入" , operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("support:SupResource:IMPORT")
    @ApiOperation(position = 10, value = "资源管理表-导入" , notes = "传入填好的模板文件即可")
    public R importXls(@ApiParam("EXCEL文件") MultipartFile file) {
        return R.ok("资源管理表-导入成功" , service.saveBatch(EasyPoiUtil.importExcel(file, SupResource.class)));
    }

}
