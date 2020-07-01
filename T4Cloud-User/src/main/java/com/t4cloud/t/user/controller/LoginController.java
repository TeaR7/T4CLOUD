package com.t4cloud.t.user.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.nacos.common.util.Md5Utils;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.annotation.RSA;
import com.t4cloud.t.base.constant.CacheConstant;
import com.t4cloud.t.base.constant.LogConstant;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.LoginUser;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.utils.PwdUtil;
import com.t4cloud.t.base.utils.RedisUtil;
import com.t4cloud.t.base.utils.UserUtil;
import com.t4cloud.t.feign.client.SupportSupMessageClient;
import com.t4cloud.t.feign.client.SystemSysRoleClient;
import com.t4cloud.t.feign.client.SystemSysUserRoleClient;
import com.t4cloud.t.feign.client.WechatUserClient;
import com.t4cloud.t.feign.dto.*;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.service.ISysUserService;
import com.t4cloud.t.user.service.ISysUserThirdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.t4cloud.t.base.constant.CacheConstant.SYS_USERS_BIND_CODE;
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
@RequestMapping("/login")
@Api(value = "用户登录、退出、密码修改、图片验证码", tags = "登录相关接口", position = 1)
public class LoginController extends T4Controller<SysUser, ISysUserService> {


    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private WechatUserClient wechatUserClient;
    @Autowired
    private ISysUserThirdService sysUserThirdService;
    @Autowired
    private SystemSysRoleClient systemSysRoleClient;
    @Autowired
    private SystemSysUserRoleClient systemSysUserRoleClient;
    @Autowired
    private SupportSupMessageClient supportSupMessageClient;


    /**
     * 图片验证码获取
     */
    @AutoLog(value = "图片验证码获取", operateType = 4, logType = 2)
    @GetMapping("/checkCode")
    @ApiOperation(position = 1, value = "图片验证码获取", notes = "无需参数，获取图片验证码所需参数")
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

    /**
     * 账号密码登录
     */
    @AutoLog(value = "账号密码登录", operateType = 1, logType = 2)
    @PostMapping("/loginByPwd")
    @ApiOperation(position = 2, value = "账号密码登录", notes = "根据账号密码及验证码登录")
    public R<LoginUser> loginByPwd(@Valid @RSA @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {


        //开始验证图片验证码
        if (!service.checkCode(SYS_USERS_CHECK_CODE, loginDTO.getCode(), loginDTO.getCodeKey())) {
            return R.error("验证码错误");
        }

        if (StringUtils.isEmpty(loginDTO.getUsername()) || StringUtils.isEmpty(loginDTO.getPassword())) {
            return R.error("账号密码不允许为空");
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
     * 第三方动态码登录
     */
    @AutoLog(value = "第三方动态码登录", operateType = 1, logType = 2)
    @PostMapping("/loginByCode/{type}")
    @ApiOperation(position = 2, value = "第三方动态码登录", notes = "根据账号密码及验证码登录")
    public R<LoginUser> loginByCode(
            @ApiParam("第三方类型，微信公众号：WE_OPEN，钉钉（）应用内H5：DING_H5,其他暂不支持") @PathVariable("type") String type,
            @ApiParam(required = true, name = "code", value = "动态码")
            @RequestParam(value = "code", required = true) String code,
            @ApiParam(required = false, name = "role", value = "用户角色,如果不传，会默认使用APP-USER角色CODE")
            @RequestParam(value = "role", required = false) String role
    ) {

        //登录的用户对象
        SysUser user;

        //添加登录权限,前台注册用户添加默认权限，用来区分前后台用户
        //查询角色
        if (StrUtil.isBlank(role)) {
            role = "APP-USER";
        }
        R<SysRoleDTO> sysRoleDTOR = systemSysRoleClient.appUserRole(role);
        if (!sysRoleDTOR.isSuccess()) {
            return R.error("获取角色失败，请稍后再试");
        }

        SysRoleDTO sysRoleDTO = sysRoleDTOR.getResult();

        switch (type) {
            case "WE_OPEN":
                R<SysUserThirdDTO> weOpenUserOauthResult = wechatUserClient.userOauth(code);
                if (!weOpenUserOauthResult.isSuccess()) {
                    return R.error("微信JSAPI登录失败,校验不成功:" + weOpenUserOauthResult.getMessage());
                }
                SysUserThirdDTO weOpenUserOauth = weOpenUserOauthResult.getResult();
                //根据第三方数据获取用户信息
                user = service.getByThirdInfo(weOpenUserOauth, sysRoleDTO);
                break;
            case "DING_H5":
                R<SysUserThirdDTO> dingH5UserOauthResult = wechatUserClient.userOauth(code);
                if (!dingH5UserOauthResult.isSuccess()) {
                    return R.error("钉钉H5登录失败,校验不成功:" + dingH5UserOauthResult.getMessage());
                }
                SysUserThirdDTO dingH5UserOauth = dingH5UserOauthResult.getResult();
                //根据第三方数据获取用户信息
                user = service.getByThirdInfo(dingH5UserOauth, sysRoleDTO);
                break;
            default:
                return R.error("暂不支持的登录方式");
        }

        if (user == null) {
            return R.error("user对象获取失败，登录失败");
        }

        //生成登录信息
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(user, loginUser);

        service.generateToken(loginUser);

        return R.ok("登录成功", loginUser);
    }

    /**
     * 退出登录
     */
    @AutoLog(value = "退出登录", operateType = 2, logType = 2)
    @DeleteMapping("/logout")
    @ApiOperation(position = 3, value = "退出登录", notes = "已登录用户退出登录，注销生效的TOKEN")
    public R logout() {

        //获取当前登录的用户的用户ID
        String userId = UserUtil.getCurrentUser().getId();
        //删除用户登录Token缓存
        redisUtil.del(CacheConstant.SYS_USERS_TOKEN + userId + "-" + getToken());

        SecurityUtils.getSubject().logout();
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
    @ApiOperation(position = 4, value = "修改密码", notes = "已登录用户修改密码")
    public R changePwd(@RequestBody LoginDTO loginDTO) {
        //开始验证图片验证码
        if (!service.checkCode(SYS_USERS_CHECK_CODE, loginDTO.getCode(), loginDTO.getCodeKey())) {
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
    @ApiOperation(position = 5, value = "获取用户信息", notes = "已登录的用户信息")
    public R<LoginUser> userInfo() {
        return R.ok("获取用户信息成功！", UserUtil.getCurrentUser());
    }

    /**
     * 动态验证码码获取
     */
    @AutoLog(value = "动态验证码码获取", operateType = 1, logType = 2)
    @GetMapping("/captcha")
    @ApiOperation(position = 1, value = "动态验证码码获取", notes = "需要邮箱或手机号，动态验证码码获取")
    public R<JSONObject> getCaptcha(
            @ApiParam(name = "target", value = "手机号或者邮件")
            @RequestParam String target
    ) {

        //验证是否已经发送过验证码
        Object object = redisUtil.get(SYS_USERS_BIND_CODE + target + "_limit");
        if (object != null) {
            return R.error("不允许频繁请求,请稍后再试！");
        }

        //生成验证码
        String captcha = RandomUtil.randomNumbers(6);

        //判断手机还是邮箱
        SupMessageDTO supMessageDTO = new SupMessageDTO();
        supMessageDTO.setMessageTemplateCode(target.contains("@") ? "EMAIL-CAPTCHA" : "PHONE-CAPTCHA");
        supMessageDTO.setTarget(target);
        //设置参数
        JSONObject param = new JSONObject();
        param.put("username", target);
        param.put("code", captcha);
        supMessageDTO.setParam(param.toString());

        R save = supportSupMessageClient.saveByTemplate(supMessageDTO);

        if (!save.isSuccess()) {
            return R.ok("验证码发送失败！" + save.getMessage());
        }

        //写入缓存中
        redisUtil.set(SYS_USERS_BIND_CODE + target, captcha, 60 * 30);
        redisUtil.set(SYS_USERS_BIND_CODE + target + "_limit", captcha, 60);

        return R.ok("验证码发送成功！");
    }

    /**
     * 手机/邮箱绑定
     */
    @AutoLog(value = "手机/邮箱绑定", operateType = 3, logType = 2)
    @PostMapping("/bindThird")
    @ApiOperation(position = 3, value = "手机/邮箱绑定", notes = "绑定第三方信息")
    public R<String> bindThird(@RequestBody LoginDTO loginDTO) {

        //开始验证图片验证码
        if (!service.checkCode(SYS_USERS_BIND_CODE, loginDTO.getCode(), loginDTO.getCodeKey())) {
            return R.error("验证码错误");
        }

        //获取当前用户
        LoginUser user = UserUtil.getCurrentUser();
        SysUser sysUser = service.getById(user.getId());

        //绑定手机/邮箱
        if (loginDTO.getCodeKey().contains("@")) {
            sysUser.setEmail(loginDTO.getCodeKey());
        } else {
            sysUser.setPhone(loginDTO.getCodeKey());
        }

        service.updateById(sysUser);

        //更新用户缓存
        service.freshUserCache(user.getId());

        return R.ok((loginDTO.getCodeKey().contains("@") ? "邮箱" : "手机") + "绑定成功！");
    }

    /**
     * 用户注册
     */
    @AutoLog(value = "用户注册", operateType = 1, logType = LogConstant.LOG_TYPE_LOGIN)
    @PostMapping("/register")
    @ApiOperation(position = 3, value = "用户注册", notes = "用户注册，账号密码即可")
    public R<LoginUser> register(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {

        //查询是否有重名用户
        SysUser sysUser = service.lambdaQuery().eq(SysUser::getUsername, loginDTO.getUsername()).one();
        if (sysUser != null) {
            return R.error("该用户已存在");
        }

        //查询角色
        String roleCode = loginDTO.getRole();
        if (StrUtil.isBlank(roleCode)) {
            roleCode = "APP-USER";
        }
        R<SysRoleDTO> sysRoleDTOR = systemSysRoleClient.appUserRole(roleCode);
        if (!sysRoleDTOR.isSuccess()) {
            return R.error("获取角色失败，请稍后再试");
        }

        SysRoleDTO sysRoleDTO = sysRoleDTOR.getResult();


        //然后插入用户表
        SysUser user = new SysUser();
        //配置用户信息
        user.setRealname(loginDTO.getUsername());
        user.setUsername(loginDTO.getUsername());
        //配置密码，但是微信登录没有密码
        String salt = RandomUtil.randomString(8);
        user.setSalt(salt);
        //填充随机密码
        String password = PwdUtil.encrypt(user.getUsername(), loginDTO.getPassword(), user.getSalt());
        user.setPassword(password);
        user.setStatus(1);
        service.save(user);

        //增加用户权限
        SysUserRoleDTO userRoleDTO = new SysUserRoleDTO();
        userRoleDTO.setUserId(user.getId());
        userRoleDTO.setRoleIds(sysRoleDTO.getId());
        systemSysUserRoleClient.saveByUser(userRoleDTO);

        //生成登录信息
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(user, loginUser);

        service.generateToken(loginUser);

        return R.ok("注册成功", loginUser);
    }

}
