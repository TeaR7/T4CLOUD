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
import com.t4cloud.t.support.entity.SupMessageTemplate;
import com.t4cloud.t.support.service.ISupMessageTemplateService;
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
@Api(value = "消息模板" , tags = "消息模板接口" , position = 3)
@RequestMapping("/SupMessageTemplate")
public class SupMessageTemplateController extends T4Controller<SupMessageTemplate, ISupMessageTemplateService> {

    /**
     * 详情
     */
    @AutoLog(value = "消息模板-详情")
    @GetMapping("/detail")
    @RequiresPermissions("support:SupMessageTemplate:VIEW")
    @ApiOperation(position = 1, value = "消息模板-详情" , notes = "传入supMessageTemplate")
    public R<SupMessageTemplate> detail(SupMessageTemplate supMessageTemplate, HttpServletRequest req) {
        QueryWrapper<SupMessageTemplate> supMessageTemplateQueryWrapper = T4Query.initQuery(supMessageTemplate, req.getParameterMap());
        SupMessageTemplate detail = service.getOne(supMessageTemplateQueryWrapper);
        return R.ok("消息模板-详情查询成功" , detail);
    }

    /**
     * 全部列表 消息模板
     */
    @AutoLog(value = "消息模板-全部列表")
    @GetMapping("/list")
    @RequiresPermissions("support:SupMessageTemplate:VIEW")
    @ApiOperation(position = 2, value = "消息模板-全部列表" , notes = "传入supMessageTemplate")
    public R<List<SupMessageTemplate>> list(SupMessageTemplate supMessageTemplate, HttpServletRequest req) {
        QueryWrapper<SupMessageTemplate> supMessageTemplateQueryWrapper = T4Query.initQuery(supMessageTemplate, req.getParameterMap());
        List<SupMessageTemplate> list = service.list(supMessageTemplateQueryWrapper);
        return R.ok("消息模板-全部列表查询成功" , list);
    }

    /**
     * 分页查询 消息模板
     */
    @AutoLog(value = "消息模板-分页查询")
    @GetMapping("/page")
    @RequiresPermissions("support:SupMessageTemplate:VIEW")
    @ApiOperation(position = 3, value = "消息模板-分页查询" , notes = "传入supMessageTemplate")
    public R<IPage<SupMessageTemplate>> page(SupMessageTemplate supMessageTemplate,
                                             @ApiParam(name = "pageNo")
                                             @RequestParam(name = "pageNo" , required = false, defaultValue = "1") Integer pageNo,
                                             @ApiParam(name = "pageSize")
                                             @RequestParam(name = "pageSize" , required = false, defaultValue = "10") Integer pageSize,
                                             HttpServletRequest req) {
        QueryWrapper<SupMessageTemplate> supMessageTemplateQueryWrapper = T4Query.initQuery(supMessageTemplate, req.getParameterMap());
        IPage<SupMessageTemplate> pages = service.page(new Page<>(pageNo, pageSize), supMessageTemplateQueryWrapper);
        return R.ok("消息模板-分页查询查询成功" , pages);
    }

    /**
     * 新增 消息模板
     */
    @AutoLog(value = "消息模板-新增" , operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("support:SupMessageTemplate:ADD")
    @ApiOperation(position = 4, value = "消息模板-新增" , notes = "传入supMessageTemplate")
    public R save(@Valid @RequestBody SupMessageTemplate supMessageTemplate, BindingResult bindingResult) {
        return R.ok("消息模板-新增成功" , service.save(supMessageTemplate));
    }

    /**
     * 修改 消息模板
     */
    @AutoLog(value = "消息模板-修改" , operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"support:SupMessageTemplate:ADD" , "support:SupMessageTemplate:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "消息模板-修改" , notes = "传入supMessageTemplate")
    public R update(@RequestBody SupMessageTemplate supMessageTemplate) {
        return R.ok("消息模板-修改成功" , service.updateById(supMessageTemplate));
    }


    /**
     * 删除 消息模板
     */
    @AutoLog(value = "消息模板-删除" , operateType = 2)
    @DeleteMapping("/delete")
    @RequiresPermissions("support:SupMessageTemplate:DELETE")
    @ApiOperation(position = 8, value = "消息模板-删除" , notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合" , required = true) @RequestParam(name = "ids") String ids) {
        return R.ok("消息模板-删除成功" , service.removeByIds(Arrays.asList(ids.split(","))));

    }


    /**
     * 导出 消息模板
     */
    @AutoLog(value = "消息模板-导出")
    @GetMapping("/export")
    @RequiresPermissions("support:SupMessageTemplate:EXPORT")
    @ApiOperation(position = 9, value = "消息模板-导出" , notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
    public void export(SupMessageTemplate supMessageTemplate,
                       @ApiParam("选中导出的数据") @RequestParam(name = "selectedRowKeys" , required = false) String selectedRowKeys,
                       @ApiParam("自定义导出列") @RequestParam(name = "selectedColKeys" , required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<SupMessageTemplate> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(supMessageTemplate, req.getParameterMap()) : new QueryWrapper<SupMessageTemplate>().in("id" , selectedRowKeys.split(","));
        super.exportExl("消息模板" , selectedColKeys, queryWrapper);
    }

    /**
     * 导入 消息模板
     */
    @AutoLog(value = "消息模板-导入" , operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("support:SupMessageTemplate:IMPORT")
    @ApiOperation(position = 10, value = "消息模板-导入" , notes = "传入填好的模板文件即可")
    public R importXls(@ApiParam("EXCEL文件") MultipartFile file) {
        return R.ok("消息模板-导入成功" , service.saveBatch(EasyPoiUtil.importExcel(file, SupMessageTemplate.class)));
    }

    @AutoLog(value = "消息模板-确认重复信息")
    @GetMapping("/check")
    @RequiresPermissions("support:SupMessageTemplate:ADD")
    @ApiOperation(position = 11, value = "消息模板-确认重复信息" , notes = "传入查询属性名和属性值")
    public R check(
            @ApiParam("属性名") @RequestParam("key") String key,
            @ApiParam("属性值") @RequestParam("value") String value) {
        Boolean checkProp = checkProp(key, value);
        return R.ok(key + "：" + value + (checkProp ? "可用，无重名项" : "不可用，存在冲突"), checkProp);
    }

}
