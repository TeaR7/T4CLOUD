package com.t4cloud.t.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.t4cloud.t.common.annotation.AutoLog;
import com.t4cloud.t.common.controller.T4Controller;
import com.t4cloud.t.common.entity.LoginUser;
import com.t4cloud.t.common.entity.dto.R;
import com.t4cloud.t.common.exception.T4CloudException;
import com.t4cloud.t.common.utils.RedisUtil;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/sysUser")
@Api(tags = "用户相关")
public class SysUserController extends T4Controller<SysUser, ISysUserService> {

    @Autowired
    private RedisUtil redisUtil;

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
     * @return com.t4cloud.t.common.entity.dto.R
     * --------------------
     * @author TeaR
     * @date 2020/1/16 13:42
     */
    @AutoLog(value = "用户登录")
    @ApiOperation(value = "用户登录", notes = "用户登录接口，传入账号密码即可")
    @PostMapping("/login")
    public R login(@RequestBody SysUser user) {

        SysUser sysUser = service.getById("f5b910f2f895c1aca6662b38ea01aef6");

        LoginUser loginUser = new LoginUser();
        BeanUtil.copyProperties(sysUser, loginUser);

        //2. 校验用户名或密码是否正确
//        String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
//        String syspassword = sysUser.getPassword();

        service.generateToken(loginUser);

        return R.ok("登录成功", loginUser);
    }

}
