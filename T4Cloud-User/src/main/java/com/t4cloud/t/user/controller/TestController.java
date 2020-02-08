package com.t4cloud.t.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.t4cloud.t.common.annotation.AutoLog;
import com.t4cloud.t.common.controller.T4Controller;
import com.t4cloud.t.common.entity.LoginUser;
import com.t4cloud.t.common.entity.dto.R;
import com.t4cloud.t.common.exception.T4CloudException;
import com.t4cloud.t.common.query.T4Query;
import com.t4cloud.t.common.utils.RedisUtil;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * TestController
 * <p>
 * 测试用
 * <p>
 * ---------------------
 *
 * @author Terry
 * @date 2020/1/15 12:39
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试接口")
public class TestController extends T4Controller<SysUser, ISysUserService> {

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 详情
     */
    @AutoLog(value = "用户表-详情", operateType = 4)
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入user")
    public R<SysUser> detail(SysUser user, HttpServletRequest req) {
        QueryWrapper<SysUser> sysUserQueryWapper = T4Query.initQuery(user, req.getParameterMap());
        SysUser detail = service.getOne(sysUserQueryWapper);
        return R.ok("用户表-详情查询成功", detail);
    }

    /**
     * 分页查询 用户表
     */
    @AutoLog(value = "用户表-分页查询", operateType = 4)
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页查询", notes = "传入user")
    public R<IPage<SysUser>> page(SysUser user,
                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                  HttpServletRequest req) {
        QueryWrapper<SysUser> sysUserQueryWapper = T4Query.initQuery(user, req.getParameterMap());
        IPage<SysUser> pages = service.page(new Page<>(pageNo, pageSize), sysUserQueryWapper);
        return R.ok("用户表-分页查询查询成功", pages);
    }


    /**
     * 测试接口
     * <p>
     * --------------------
     *
     * @author TeaR
     * @date 2020/1/15 18:12
     */
    @AutoLog(value = "测试")
    @ApiOperation(value = "测试方法", notes = "具体用来测试是否可以成功返回")
    @GetMapping("/403/{type}")
    public R test(@PathVariable String type) {

        redisUtil.set("test", type);

        if (type.equalsIgnoreCase("1")) {
            throw new T4CloudException("测试异常d");
        }

        SysUser sysUser = service.getById("f5b910f2f895c1aca6662b38ea01aef6");

        redisUtil.get("test");
        redisUtil.set("test:" + type, sysUser);

        return R.ok("测试成功" + type, sysUser);
    }

    /**
     * 测试接口
     * <p>
     * --------------------
     *
     * @author TeaR
     * @date 2020/1/15 18:12
     */
    @AutoLog(value = "测试登录")
    @ApiOperation(value = "测试登录方法", notes = "此方法需要登录才可以")
    @GetMapping("/testLogin/{type}")
    public R testLogin(@PathVariable String type) {

        redisUtil.set("test", type);

        if (type.equalsIgnoreCase("1")) {
            throw new T4CloudException("测试异常d");
        }

        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();


        redisUtil.get("test");
        redisUtil.set("test:" + type, user);

        return R.ok("测试成功" + type, user);
    }


    /**
     * 用户登录接口
     *
     * @param user 用户对象，传入账号密码即可
     *             <p>
     * @return com.t4cloud.t.common.entity.dto.Result
     * --------------------
     * @author TeaR
     * @date 2020/1/16 13:42
     */
    @AutoLog(value = "用户登录")
    @ApiOperation(value = "用户登录", notes = "用户登录接口，传入账号密码即可")
    @PostMapping("/login")
    public R login(@RequestBody SysUser user) {

        SysUser sysUser = service.getById("1");

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);

        //2. 校验用户名或密码是否正确
//        String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
//        String syspassword = sysUser.getPassword();

        service.generateToken(loginUser);

        return R.ok("登录成功", loginUser);
    }

}
