package com.t4cloud.t.system.service;


import com.t4cloud.t.system.entity.SysPermission;
import com.t4cloud.t.base.service.T4Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 菜单权限表 服务类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-13 
 */
public interface ISysPermissionService extends T4Service<SysPermission> {

    /**
     * 查询用户有的所有权限
     *
     * @param username 用户名
     * <p>
     * @return java.util.List<com.t4cloud.t.system.entity.SysPermission>
     * --------------------
     * @author TeaR
     * @date 2020/2/13 13:18
     */
    List<SysPermission> queryByUsername(String username);

}
