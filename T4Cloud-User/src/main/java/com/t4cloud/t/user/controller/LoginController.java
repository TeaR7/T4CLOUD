package com.t4cloud.t.user.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.nacos.common.util.Md5Utils;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.constant.CacheConstant;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.LoginUser;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.utils.PwdUtil;
import com.t4cloud.t.base.utils.RedisUtil;
import com.t4cloud.t.base.utils.UserUtil;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.entity.dto.LoginDTO;
import com.t4cloud.t.user.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.t4cloud.t.base.constant.CacheConstant.SYS_USERS_CHECK_CODE;

/**
 * 登录相关功能 控制器
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-10
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user/login")
@Api(value = "用户登录、退出、密码修改、图片验证码", tags = "登录相关接口")
public class LoginController extends T4Controller<SysUser, ISysUserService> {


    @Autowired
    private RedisUtil redisUtil;

    /**
     * 账号密码登录
     */
    @AutoLog(value = "账号密码登录", operateType = 4, logType = 2)
    @PostMapping("/loginByPwd")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "账号密码登录", notes = "根据账号密码及验证码登录")
    public R<LoginUser> loginByPwd(@RequestBody LoginDTO loginDTO) {

        //开始验证图片验证码
        if (!service.checkCode(loginDTO.getCode(), loginDTO.getCodeKey())) {
            return R.error("验证码错误");
        }

        //获取账号密码,查看是否有效账户
        SysUser sysUser = service.lambdaQuery()
                .eq(SysUser::getUsername, loginDTO.getUsername())
                .one();
        if (sysUser == null) {
            return R.error("用户不存在");
        }

        // TODO 这里有个小隐患，就是在不知道密码的情况下，也能被测试出账号是否异常。当前看起来没有问题，只是无需密码即可知账号状态有点不应该 -by TeaR  -2020/2/10-16:10
        if (sysUser.getStatus() == 0) {
            return R.error("用户已被冻结，禁止登录");
        }

        //验证密码
        String password = PwdUtil.encrypt(loginDTO.getUsername(), loginDTO.getPassword(), sysUser.getSalt());
        if (!sysUser.getPassword().equals(password)) {
            return R.error("用户名或密码错误，请重试");
        }

        //生成登录信息
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);

        service.generateToken(loginUser);

        return R.ok("登录成功", loginUser);
    }

    /**
     * 退出登录
     */
    @AutoLog(value = "退出登录", operateType = 2, logType = 2)
    @DeleteMapping("/logout")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "退出登录", notes = "已登录用户退出登录，注销生效的TOKEN")
    public R logout() {

        //获取当前登录的用户的用户名
        String username = UserUtil.getCurrentUser().getUsername();
        //删除用户登录Token缓存
        redisUtil.del(CacheConstant.SYS_USERS_TOKEN + username + "-" + getToken());

        /** TODO 退出的时候是否需要删除掉其他用户相关信息？
         *
         * 例如用户本身的缓存，用户权限的缓存。但是删除这些缓存是否会导致其他的客户端登录失效或登录异常？
         *
         * -by TeaR  -2020/2/10-16:55
         */
        return R.ok("退出登录成功！");
    }

    /**
     * 修改密码
     */
    @AutoLog(value = "修改密码", operateType = 3, logType = 2)
    @PostMapping("/changePwd")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "修改密码", notes = "已登录用户修改密码")
    public R changePwd(@RequestBody LoginDTO loginDTO) {
        //开始验证图片验证码
        if (!service.checkCode(loginDTO.getCode(), loginDTO.getCodeKey())) {
            return R.error("验证码错误");
        }
        //开始变更密码
        //查询当前用户
        LoginUser currentUser = UserUtil.getCurrentUser();
        SysUser sysUser = service.getById(currentUser.getId());
        //生辰新密码
        String password = PwdUtil.encrypt(sysUser.getUsername(), loginDTO.getPassword(), sysUser.getSalt());
        sysUser.setPassword(password);
        //变更数据库
        service.updateById(sysUser);

        return R.ok("密码修改成功！");
    }

    /**
     * 修改密码
     */
    @AutoLog(value = "获取用户信息", operateType = 4, logType = 2)
    @GetMapping("/userInfo")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "获取用户信息", notes = "已登录的用户信息")
    public R<LoginUser> userInfo(@RequestBody LoginDTO loginDTO) {
        return R.ok("获取用户信息成功！", UserUtil.getCurrentUser());
    }


    /**
     * 图片验证码获取
     */
    @AutoLog(value = "图片验证码获取", operateType = 4, logType = 2)
    @GetMapping("/checkCode")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "图片验证码获取", notes = "无需参数，获取图片验证码所需参数")
    public R<JSONObject> getCheckCode() {
        JSONObject data = new JSONObject();
        try {
            String code = RandomUtil.randomString("qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890", 4);
            String key = Md5Utils.getMD5(code + System.currentTimeMillis(), "utf-8");
            redisUtil.set(SYS_USERS_CHECK_CODE + key, code, 60);
            data.put("key", key);
            data.put("code", code);
            return R.ok("验证码获取成功", data);
        } catch (Exception e) {
            e.printStackTrace();
            return R.ok("验证码获取失败，请联系管理员");
        }
    }

}
