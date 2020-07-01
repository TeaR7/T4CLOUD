package com.t4cloud.t.user.service;

import com.t4cloud.t.base.entity.LoginUser;
import com.t4cloud.t.base.service.T4Service;
import com.t4cloud.t.feign.dto.SysRoleDTO;
import com.t4cloud.t.feign.dto.SysUserThirdDTO;
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
     * @param prefix 缓存命名空间
     * @param code   用户提交的验证码
     * @param key    验证码对应的KEY
     *               <p>
     * @return boolean
     * --------------------
     * @author TeaR
     * @date 2020/2/10 17:33
     */
    boolean checkCode(String prefix, String code, String key);

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
    void freshUserCache(String userId);

    /**
     * 通过第三方用户信息新增或获取用户信息
     *
     * @param userOauth  第三方信息
     * @param sysRoleDTO 用户角色（新增用户的时候生效）
     *                   <p>
     * @return com.t4cloud.t.user.entity.SysUser
     * --------------------
     * @author TeaR
     * @date 2020/6/28 16:17
     */
    SysUser getByThirdInfo(SysUserThirdDTO userOauth, SysRoleDTO sysRoleDTO);

}
