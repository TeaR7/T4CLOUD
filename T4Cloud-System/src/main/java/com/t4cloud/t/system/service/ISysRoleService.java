package com.t4cloud.t.system.service;


import com.t4cloud.t.base.service.T4Service;
import com.t4cloud.t.system.entity.SysRole;

/**
 * 角色表 服务类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-21
 */
public interface ISysRoleService extends T4Service<SysRole> {

    /**
     * 获取APP-USER的用户角色
     *
     * @param roleCode 角色编码
     *                 <p>
     * @return com.t4cloud.t.system.entity.SysRole
     * --------------------
     * @author TeaR
     * @date 2020/6/8 16:14
     */
    SysRole appUserRole(String roleCode);
}
