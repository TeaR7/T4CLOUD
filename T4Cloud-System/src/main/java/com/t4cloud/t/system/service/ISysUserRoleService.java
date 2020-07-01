package com.t4cloud.t.system.service;


import com.t4cloud.t.base.service.T4Service;
import com.t4cloud.t.system.entity.SysRole;
import com.t4cloud.t.system.entity.SysUserRole;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户角色表 服务类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-02-27
 */
public interface ISysUserRoleService extends T4Service<SysUserRole> {

    /**
     * 获取某用户的所有角色
     *
     * @param userId 用户ID
     *               <p>
     * @return java.util.List<com.t4cloud.t.system.entity.SysRole>
     * --------------------
     * @author TeaR
     * @date 2020/2/27 19:35
     */
    List<SysRole> getUserRoleList(String userId);

    /**
     * 用户保存角色信息
     *
     * @param sysUserRole 用户角色信息
     *                    <p>
     * @return boolean
     * --------------------
     * @author TeaR
     * @date 2020/6/8 15:45
     */
    boolean saveByUser(SysUserRole sysUserRole);

    /**
     * 清空用户权限
     *
     * @param ids 用户ID（多个用，拼接）
     * <p>
     * @return boolean
     * --------------------
     * @author TeaR
     * @date 2020/6/8 16:02
     */
    boolean deleteByUser(@RequestParam String ids);

}
