package com.t4cloud.t.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.t4cloud.t.common.annotation.AutoLog;
import com.t4cloud.t.common.controller.T4Controller;
import com.t4cloud.t.common.entity.dto.R;
import com.t4cloud.t.common.query.T4Query;
import com.t4cloud.t.user.entity.User;
import com.t4cloud.t.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 用户表 控制器
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user/user")
@Api(value = "用户表", tags = "用户表接口")
public class UserController extends T4Controller<User, IUserService> {

    /**
     * 详情
     */
    @AutoLog(value = "用户表-详情", operateType = 4)
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入user")
    public R<User> detail(User user, HttpServletRequest req) {
        QueryWrapper<User> userQueryWapper = T4Query.initQuery(user, req.getParameterMap());
        User detail = service.getOne(userQueryWapper);
        return R.ok("用户表-详情查询成功", detail);
    }

    /**
     * 全部列表 用户表
     */
    @AutoLog(value = "用户表-全部列表", operateType = 4)
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "全部列表", notes = "传入user")
    public R<List<User>> list(User user, HttpServletRequest req) {
        QueryWrapper<User> userQueryWapper = T4Query.initQuery(user, req.getParameterMap());
        List<User> list = service.list(userQueryWapper);
        return R.ok("用户表-全部列表查询成功", list);
    }

    /**
     * 分页查询 用户表
     */
    @AutoLog(value = "用户表-分页查询", operateType = 4)
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页查询", notes = "传入user")
    public R<IPage<User>> page(User user,
                               @ApiParam(name = "pageNo", required = false)
                               @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                               @ApiParam(name = "pageSize", required = false)
                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                               HttpServletRequest req) {
        QueryWrapper<User> userQueryWapper = T4Query.initQuery(user, req.getParameterMap());
        IPage<User> pages = service.page(new Page<>(pageNo, pageSize), userQueryWapper);
        return R.ok("用户表-分页查询查询成功", pages);
    }

    /**
     * 新增 用户表
     */
    @AutoLog(value = "用户表-新增", operateType = 1)
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入user")
    public R save(@Valid @RequestBody User user) {
        return R.ok("用户表-新增成功", service.save(user));
    }

    /**
     * 修改 用户表
     */
    @AutoLog(value = "用户表-修改", operateType = 3)
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入user")
    public R update(@Valid @RequestBody User user) {
        return R.ok("用户表-修改成功", service.updateById(user));
    }


    /**
     * 删除 用户表
     */
    @AutoLog(value = "用户表-删除", operateType = 2)
    @PostMapping("/delete")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "删除", notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.ok("用户表-删除成功", service.removeByIds(Arrays.asList(ids.split(","))));
    }


}
