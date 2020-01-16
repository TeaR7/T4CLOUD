package com.t4cloud.t.user.service.impl;

import com.t4cloud.t.common.constant.CacheConstant;
import com.t4cloud.t.common.entity.LoginUser;
import com.t4cloud.t.common.service.impl.T4ServiceImpl;
import com.t4cloud.t.common.utils.JwtUtil;
import com.t4cloud.t.common.utils.RedisUtil;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.mapper.SysUserMapper;
import com.t4cloud.t.user.service.ISysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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


    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     *                 <p>
     * @return com.t4cloud.t.common.entity.LoginUser
     * --------------------
     * @author TeaR
     * @date 2020/1/16 11:01
     */
    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_USERS_CACHE, key = "#username")
    public LoginUser getUserByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        LoginUser loginUser = new LoginUser();
        SysUser sysUser = lambdaQuery().eq(SysUser::getUsername, username).one();
        if (sysUser == null) {
            return null;
        }
        BeanUtils.copyProperties(sysUser, loginUser);
        return loginUser;
    }

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
        String token = JwtUtil.sign(user.getUsername(), user.getPassword());
        // 设置token缓存有效时间
        redisUtil.set(CacheConstant.SYS_USERS_TOKEN + user.getUsername() + "-" + token, token, JwtUtil.EXPIRE_TIME * 2 / 1000);
        redisUtil.set(CacheConstant.SYS_USERS_CACHE + user.getUsername(), user, JwtUtil.EXPIRE_TIME * 2 / 1000);
        user.setToken(token);
        user.setPassword(null);
        return user;
    }

}
