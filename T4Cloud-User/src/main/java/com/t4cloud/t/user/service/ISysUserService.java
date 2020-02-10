package com.t4cloud.t.user.service;

import com.t4cloud.t.base.entity.LoginUser;
import com.t4cloud.t.base.service.T4Service;
import com.t4cloud.t.user.entity.SysUser;

/**
 * 用户实体 服务类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @date 2020/1/15 23:33
 */
public interface ISysUserService extends T4Service<SysUser> {

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
    LoginUser getUserByUsername(String username);

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
    LoginUser generateToken(LoginUser user);

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
    boolean checkCode(String code, String key);

}
