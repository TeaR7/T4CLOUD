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
import com.t4cloud.t.support.entity.SysLog;
import com.t4cloud.t.support.service.ISysLogService;
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
 * 系统日志表 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-27
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "系统日志表" , tags = "系统日志表接口" , position = 5)
@RequestMapping("/SysLog")
public class SysLogController extends T4Controller<SysLog, ISysLogService> {

    /**
     * 详情
     */
    @AutoLog(value = "系统日志表-详情")
    @GetMapping("/detail")
    @RequiresPermissions("support:SysLog:VIEW")
    @ApiOperation(position = 1, value = "系统日志表-详情" , notes = "传入sysLog")
    public R<SysLog> detail(SysLog sysLog, HttpServletRequest req) {
        QueryWrapper<SysLog> sysLogQueryWrapper = T4Query.initQuery(sysLog, req.getParameterMap());
        SysLog detail = service.getOne(sysLogQueryWrapper);
        return R.ok("系统日志表-详情查询成功" , detail);
    }

    /**
     * 全部列表 系统日志表
     */
    @AutoLog(value = "系统日志表-全部列表")
    @GetMapping("/list")
    @RequiresPermissions("support:SysLog:VIEW")
    @ApiOperation(position = 2, value = "系统日志表-全部列表" , notes = "传入sysLog")
    public R<List<SysLog>> list(SysLog sysLog, HttpServletRequest req) {
        QueryWrapper<SysLog> sysLogQueryWrapper = T4Query.initQuery(sysLog, req.getParameterMap());
        List<SysLog> list = service.list(sysLogQueryWrapper);
        return R.ok("系统日志表-全部列表查询成功" , list);
    }

    /**
     * 分页查询 系统日志表
     */
    @AutoLog(value = "系统日志表-分页查询")
    @GetMapping("/page")
    @RequiresPermissions("support:SysLog:VIEW")
    @ApiOperation(position = 3, value = "系统日志表-分页查询" , notes = "传入sysLog")
    public R<IPage<SysLog>> page(SysLog sysLog,
                                 @ApiParam(name = "pageNo")
                                 @RequestParam(name = "pageNo" , required = false, defaultValue = "1") Integer pageNo,
                                 @ApiParam(name = "pageSize")
                                 @RequestParam(name = "pageSize" , required = false, defaultValue = "10") Integer pageSize,
                                 HttpServletRequest req) {
        QueryWrapper<SysLog> sysLogQueryWrapper = T4Query.initQuery(sysLog, req.getParameterMap());
        IPage<SysLog> pages = service.page(new Page<>(pageNo, pageSize), sysLogQueryWrapper);
        return R.ok("系统日志表-分页查询查询成功" , pages);
    }

    /**
     * 新增 系统日志表
     */
    @AutoLog(value = "系统日志表-新增" , operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("support:SysLog:ADD")
    @ApiOperation(position = 4, value = "系统日志表-新增" , notes = "传入sysLog")
    public R save(@Valid @RequestBody SysLog sysLog, BindingResult bindingResult) {
        return R.ok("系统日志表-新增成功" , service.save(sysLog));
    }

    /**
     * 修改 系统日志表
     */
    @AutoLog(value = "系统日志表-修改" , operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"support:SysLog:ADD" , "support:SysLog:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "系统日志表-修改" , notes = "传入sysLog")
    public R update(@RequestBody SysLog sysLog) {
        return R.ok("系统日志表-修改成功" , service.updateById(sysLog));
    }


    /**
     * 删除 系统日志表
     */
    @AutoLog(value = "系统日志表-删除" , operateType = 2)
    @DeleteMapping("/delete")
    @RequiresPermissions("support:SysLog:DELETE")
    @ApiOperation(position = 8, value = "系统日志表-删除" , notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合" , required = true) @RequestParam(name = "ids") String ids) {
        return R.ok("系统日志表-删除成功" , service.removeByIds(Arrays.asList(ids.split(","))));

    }


    /**
     * 导出 系统日志表
     */
    @AutoLog(value = "系统日志表-导出")
    @GetMapping("/export")
    @RequiresPermissions("support:SysLog:EXPORT")
    @ApiOperation(position = 9, value = "系统日志表-导出" , notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
    public void export(SysLog sysLog,
                       @ApiParam("选中导出的数据") @RequestParam(name = "selectedRowKeys" , required = false) String selectedRowKeys,
                       @ApiParam("自定义导出列") @RequestParam(name = "selectedColKeys" , required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<SysLog> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(sysLog, req.getParameterMap()) : new QueryWrapper<SysLog>().in("id" , selectedRowKeys.split(","));
        super.exportExl("系统日志表" , selectedColKeys, queryWrapper);
    }

    /**
     * 导入 系统日志表
     */
    @AutoLog(value = "系统日志表-导入" , operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("support:SysLog:IMPORT")
    @ApiOperation(position = 10, value = "系统日志表-导入" , notes = "传入填好的模板文件即可")
    public R importXls(@ApiParam("EXCEL文件") MultipartFile file) {
        return R.ok("系统日志表-导入成功" , service.saveBatch(EasyPoiUtil.importExcel(file, SysLog.class)));
    }

}
