package com.t4cloud.t.system.service;


import com.t4cloud.t.base.service.T4Service;
import com.t4cloud.t.system.entity.SysPermission;

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


    /**
     * 将权限列表转换为树状结构
     *
     * @param list    树状结构一级节点
     * @param allData 原权限列表
     *                <p>
     * @return void
     * --------------------
     * @author TeaR
     * @date 2020/2/13 14:33
     */
    void tree(List<SysPermission> list, List<SysPermission> allData);

    /**
     * 获取权限的子集
     *
     * @param ids    树状结构一级节点 id 列表
     *                <p>
     * @return void
     * --------------------
     * @author TeaR
     * @date 2020/2/13 14:33
     */
    void findChildren(List<String> ids);

}
