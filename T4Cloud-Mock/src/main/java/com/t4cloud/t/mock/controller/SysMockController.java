package com.t4cloud.t.mock.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.mock.entity.SysMock;
import com.t4cloud.t.mock.service.ISysMockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * Mock数据 控制器
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-14
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "Mock数据", tags = "Mock数据接口")
@RequestMapping("/SysMock")
public class SysMockController extends T4Controller<SysMock, ISysMockService> {

    /**
     * 详情
     */
    @AutoLog(value = "Mock数据-详情", operateType = 4)
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @RequiresPermissions(value = {"mock:SysMock:VIEW"})
    @ApiOperation(value = "Mock数据-详情", notes = "传入sysMock")
    public R<SysMock> detail(SysMock sysMock, HttpServletRequest req) {
        QueryWrapper<SysMock> sysMockQueryWapper = T4Query.initQuery(sysMock, req.getParameterMap());
        SysMock detail = service.getOne(sysMockQueryWapper);
        return R.ok("Mock数据-详情查询成功", detail);
    }

    /**
     * 全部列表 Mock数据
     */
    @AutoLog(value = "Mock数据-全部列表", operateType = 4)
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @RequiresPermissions(value = {"mock:SysMock:VIEW"})
    @ApiOperation(value = "Mock数据-全部列表", notes = "传入sysMock")
    public R<List<SysMock>> list(SysMock sysMock, HttpServletRequest req) {
        QueryWrapper<SysMock> sysMockQueryWapper = T4Query.initQuery(sysMock, req.getParameterMap());
        List<SysMock> list = service.list(sysMockQueryWapper);
        return R.ok("Mock数据-全部列表查询成功", list);
    }

    /**
     * 分页查询 Mock数据
     */
    @AutoLog(value = "Mock数据-分页查询", operateType = 4)
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @RequiresPermissions(value = {"mock:SysMock:VIEW"})
    @ApiOperation(value = "Mock数据-分页查询", notes = "传入sysMock")
    public R<IPage<SysMock>> page(SysMock sysMock,
                                  @ApiParam(name = "pageNo", required = false)
                                  @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                  @ApiParam(name = "pageSize", required = false)
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  HttpServletRequest req) {
        QueryWrapper<SysMock> sysMockQueryWapper = T4Query.initQuery(sysMock, req.getParameterMap());
        IPage<SysMock> pages = service.page(new Page<>(pageNo, pageSize), sysMockQueryWapper);
        return R.ok("Mock数据-分页查询查询成功", pages);
    }

    /**
     * 新增 Mock数据
     */
    @AutoLog(value = "Mock数据-新增", operateType = 1)
    @PutMapping("/save")
    @ApiOperationSupport(order = 4)
    @RequiresPermissions(value = {"mock:SysMock:ADD"})
    @ApiOperation(value = "Mock数据-新增", notes = "传入sysMock")
    public R save(@Valid @RequestBody SysMock sysMock) {
        return R.ok("Mock数据-新增成功", service.save(sysMock));
    }

    /**
     * 修改 Mock数据
     */
    @AutoLog(value = "Mock数据-修改", operateType = 3)
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @RequiresPermissions(value = {"mock:SysMock:ADD", "mock:SysMock:EDIT"}, logical = Logical.OR)
    @ApiOperation(value = "Mock数据-修改", notes = "传入sysMock")
    public R update(@Valid @RequestBody SysMock sysMock) {
        return R.ok("Mock数据-修改成功", service.updateById(sysMock));
    }


    /**
     * 删除 Mock数据
     */
    @AutoLog(value = "Mock数据-删除", operateType = 2)
    @DeleteMapping("/delete")
    @ApiOperationSupport(order = 8)
    @RequiresRoles("ADMIN")
    @RequiresPermissions(value = {"mock:SysMock:DELETE"})
    @ApiOperation(value = "Mock数据-删除", notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.ok("Mock数据-删除成功", service.removeByIds(Arrays.asList(ids.split(","))));
    }


}
