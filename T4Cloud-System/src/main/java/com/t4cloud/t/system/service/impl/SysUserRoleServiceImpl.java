package com.t4cloud.t.system.service.impl;

import com.t4cloud.t.base.annotation.cache.RoleCacheable;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.system.entity.SysRole;
import com.t4cloud.t.system.entity.SysUserRole;
import com.t4cloud.t.system.mapper.SysRoleMapper;
import com.t4cloud.t.system.mapper.SysUserRoleMapper;
import com.t4cloud.t.system.service.ISysUserRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色表 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-02-27
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserRoleServiceImpl extends T4ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private final SysRoleMapper sysRoleMapper;

    /**
     * 获取某用户的所有角色
     *
     * @param username 用户名
     *                 <p>
     * @return java.util.List<com.t4cloud.t.system.entity.SysRole>
     * --------------------
     * @author TeaR
     * @date 2020/2/27 19:35
     */
    @Override
    @RoleCacheable
    public List<SysRole> getUserRoleList(String username) {
        return sysRoleMapper.queryByUsername(username);
    }

}
