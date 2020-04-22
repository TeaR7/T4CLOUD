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
import com.t4cloud.t.user.entity.SysUserThird;
import com.t4cloud.t.user.service.ISysUserThirdService;
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
 * 用户第三方登录数据 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-07
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "用户第三方登录数据" , tags = "用户第三方登录数据接口" , position = 10)
@RequestMapping("/SysUserThird")
public class SysUserThirdController extends T4Controller<SysUserThird, ISysUserThirdService> {

    /**
     * 详情
     */
    @AutoLog(value = "用户第三方登录数据-详情")
    @GetMapping("/detail")
    @RequiresPermissions("user:SysUserThird:VIEW")
    @ApiOperation(position = 1, value = "用户第三方登录数据-详情" , notes = "传入sysUserThird")
    public R<SysUserThird> detail(SysUserThird sysUserThird, HttpServletRequest req) {
        QueryWrapper<SysUserThird> sysUserThirdQueryWrapper = T4Query.initQuery(sysUserThird, req.getParameterMap());
        SysUserThird detail = service.getOne(sysUserThirdQueryWrapper);
        return R.ok("用户第三方登录数据-详情查询成功" , detail);
    }

    /**
     * 全部列表 用户第三方登录数据
     */
    @AutoLog(value = "用户第三方登录数据-全部列表")
    @GetMapping("/list")
    @RequiresPermissions("user:SysUserThird:VIEW")
    @ApiOperation(position = 2, value = "用户第三方登录数据-全部列表" , notes = "传入sysUserThird")
    public R<List<SysUserThird>> list(SysUserThird sysUserThird, HttpServletRequest req) {
        QueryWrapper<SysUserThird> sysUserThirdQueryWrapper = T4Query.initQuery(sysUserThird, req.getParameterMap());
        List<SysUserThird> list = service.list(sysUserThirdQueryWrapper);
        return R.ok("用户第三方登录数据-全部列表查询成功" , list);
    }

    /**
     * 分页查询 用户第三方登录数据
     */
    @AutoLog(value = "用户第三方登录数据-分页查询")
    @GetMapping("/page")
    @RequiresPermissions("user:SysUserThird:VIEW")
    @ApiOperation(position = 3, value = "用户第三方登录数据-分页查询" , notes = "传入sysUserThird")
    public R<IPage<SysUserThird>> page(SysUserThird sysUserThird,
                                       @ApiParam(name = "pageNo")
                                       @RequestParam(name = "pageNo" , required = false, defaultValue = "1") Integer pageNo,
                                       @ApiParam(name = "pageSize")
                                       @RequestParam(name = "pageSize" , required = false, defaultValue = "10") Integer pageSize,
                                       HttpServletRequest req) {
        QueryWrapper<SysUserThird> sysUserThirdQueryWrapper = T4Query.initQuery(sysUserThird, req.getParameterMap());
        IPage<SysUserThird> pages = service.page(new Page<>(pageNo, pageSize), sysUserThirdQueryWrapper);
        return R.ok("用户第三方登录数据-分页查询查询成功" , pages);
    }

    /**
     * 新增 用户第三方登录数据
     */
    @AutoLog(value = "用户第三方登录数据-新增" , operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("user:SysUserThird:ADD")
    @ApiOperation(position = 4, value = "用户第三方登录数据-新增" , notes = "传入sysUserThird")
    public R save(@Valid @RequestBody SysUserThird sysUserThird, BindingResult bindingResult) {
        return R.ok("用户第三方登录数据-新增成功" , service.save(sysUserThird));
    }

    /**
     * 修改 用户第三方登录数据
     */
    @AutoLog(value = "用户第三方登录数据-修改" , operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"user:SysUserThird:ADD" , "user:SysUserThird:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "用户第三方登录数据-修改" , notes = "传入sysUserThird")
    public R update(@RequestBody SysUserThird sysUserThird) {
        return R.ok("用户第三方登录数据-修改成功" , service.updateById(sysUserThird));
    }


    /**
     * 删除 用户第三方登录数据
     */
    @AutoLog(value = "用户第三方登录数据-删除" , operateType = 2)
    @DeleteMapping("/delete")
    @RequiresPermissions("user:SysUserThird:DELETE")
    @ApiOperation(position = 8, value = "用户第三方登录数据-删除" , notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合" , required = true) @RequestParam(name = "ids") String ids) {
        return R.ok("用户第三方登录数据-删除成功" , service.removeByIds(Arrays.asList(ids.split(","))));

    }


    /**
     * 导出 用户第三方登录数据
     */
    @AutoLog(value = "用户第三方登录数据-导出")
    @GetMapping("/export")
    @RequiresPermissions("user:SysUserThird:EXPORT")
    @ApiOperation(position = 9, value = "用户第三方登录数据-导出" , notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
    public void export(SysUserThird sysUserThird,
                       @ApiParam("选中导出的数据") @RequestParam(name = "selectedRowKeys" , required = false) String selectedRowKeys,
                       @ApiParam("自定义导出列") @RequestParam(name = "selectedColKeys" , required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<SysUserThird> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(sysUserThird, req.getParameterMap()) : new QueryWrapper<SysUserThird>().in("id" , selectedRowKeys.split(","));
        super.exportExl("用户第三方登录数据" , selectedColKeys, queryWrapper);
    }

    /**
     * 导入 用户第三方登录数据
     */
    @AutoLog(value = "用户第三方登录数据-导入" , operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("user:SysUserThird:IMPORT")
    @ApiOperation(position = 10, value = "用户第三方登录数据-导入" , notes = "传入填好的模板文件即可")
    public R importXls(@ApiParam("EXCEL文件") MultipartFile file) {
        return R.ok("用户第三方登录数据-导入成功" , service.saveBatch(EasyPoiUtil.importExcel(file, SysUserThird.class)));
    }

}
