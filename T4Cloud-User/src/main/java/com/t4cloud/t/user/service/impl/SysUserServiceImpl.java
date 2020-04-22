package com.t4cloud.t.user.service.impl;

import com.t4cloud.t.base.constant.CacheConstant;
import com.t4cloud.t.base.entity.LoginUser;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.base.utils.JwtUtil;
import com.t4cloud.t.base.utils.RedisUtil;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.mapper.SysUserMapper;
import com.t4cloud.t.user.service.ISysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @date 2020/1/16 13:39
 */
@Service
@Slf4j
@AllArgsConstructor
public class SysUserServiceImpl extends T4ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private Environment environment;

    /**
     * 用户校验成功后生成token
     *
     * @param user 已登录用户对象
     *             <p>
     * @return com.t4cloud.t.common.entity.LoginUser
     * --------------------
     * @author TeaR
     * @date 2020/1/16 15:11
     */
    @Override
    public LoginUser generateToken(LoginUser user) {
        String token = JwtUtil.sign(user.getId(), user.getUsername(), user.getPassword());
        // 设置token缓存有效时间
        redisUtil.set(CacheConstant.SYS_USERS_TOKEN + user.getId() + "-" + token, token, JwtUtil.EXPIRE_TIME * 2 / 1000);
        redisUtil.set(CacheConstant.SYS_USERS_CACHE + user.getId(), user, JwtUtil.EXPIRE_TIME * 2 / 1000);
        user.setToken(token);

        //脱敏处理
        user.setPassword(null);
        user.setId(null);
        user.setSalt(null);

        return user;
    }

    /**
     * 检验基础的图片验证码
     *
     * @param code 用户提交的验证码
     * @param key  验证码对应的KEY
     *             <p>
     * @return boolean
     * --------------------
     * @author TeaR
     * @date 2020/2/10 17:33
     */
    @Override
    public boolean checkCode(String prefix, String code, String key) {

        //获取是否验证验证码
        Boolean check = Boolean.parseBoolean(environment.getProperty("t4cloud.check-code"));

        if (check) {
            //开始验证验证码
            String checkCode = (String) redisUtil.get(prefix + key);

            if (StringUtils.isEmpty(checkCode)) {
                return false;
            }

            if (!checkCode.equalsIgnoreCase(code)) {
                return false;
            }

            //验证通过，失效该验证码
            redisUtil.del(prefix + key);
        }

        return true;
    }

    /**
     * 刷新用户缓存
     *
     * @param userId 用户ID
     *               <p>
     * @return void
     * --------------------
     * @author TeaR
     * @date 2020/4/22 14:53
     */
    @Override
    public void freshUserCache(String userId) {
        //更新用户缓存
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(getById(userId), loginUser);
        redisUtil.set(CacheConstant.SYS_USERS_CACHE + userId, loginUser, redisUtil.getExpire(CacheConstant.SYS_USERS_CACHE + userId));
    }


}
