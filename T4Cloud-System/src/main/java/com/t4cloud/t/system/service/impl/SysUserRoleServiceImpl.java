package com.t4cloud.t.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.t4cloud.t.base.annotation.cache.RoleCacheEvict;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
     * @param userId 用户ID
     *               <p>
     * @return java.util.List<com.t4cloud.t.system.entity.SysRole>
     * --------------------
     * @author TeaR
     * @date 2020/2/27 19:35
     */
    @Override
    @RoleCacheable
    public List<SysRole> getUserRoleList(String userId) {
        return sysRoleMapper.queryByUserId(userId);
    }

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
    @Override
    @RoleCacheEvict // TODO 这个注解有个问题，我偷懒了，任何一个用户编辑他的角色的时候，不仅会清空他自己的redis角色和权限信息，是清空所有人的.以后可以优化一下此处，暂时问题不大
    public boolean saveByUser(SysUserRole sysUserRole) {
        //查询之前的所有角色
        List<SysUserRole> sysUserRoleList = lambdaQuery().eq(SysUserRole::getUserId, sysUserRole.getUserId()).list();
        List<String> oldRoleIds = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

        //新的权限
        List<String> newRoleIds = StrUtil.isBlank(sysUserRole.getRoleIds()) ? new ArrayList<>() : Arrays.asList(sysUserRole.getRoleIds().split(","));

        //处理新增的
        List<String> addRoleIds = newRoleIds.stream().filter(item -> StrUtil.isNotEmpty(item) && !oldRoleIds.contains(item)).collect(Collectors.toList());
        List<SysUserRole> addRoleList = new ArrayList<>();
        for (String roleId : addRoleIds) {
            SysUserRole userRole = new SysUserRole().setRoleId(roleId).setUserId(sysUserRole.getUserId());
            addRoleList.add(userRole);
        }
        saveBatch(addRoleList);
        //处理删除的
        List<String> deleteRoleIds = oldRoleIds.stream().filter(item -> StrUtil.isNotEmpty(item) && !newRoleIds.contains(item)).collect(Collectors.toList());
        removeByIds(sysUserRoleList.stream().filter(item -> deleteRoleIds.contains(item.getRoleId())).map(SysUserRole::getId).collect(Collectors.toList()));

        return true;
    }

    /**
     * 清空用户权限
     *
     * @param ids 用户ID（多个用，拼接）
     *            <p>
     * @return boolean
     * --------------------
     * @author TeaR
     * @date 2020/6/8 16:02
     */
    @Override
    @RoleCacheEvict // TODO 这个注解有个问题，我偷懒了，任何一个用户编辑他的角色的时候，不仅会清空他自己的redis角色和权限信息，是清空所有人的.以后可以优化一下此处，暂时问题不大
    public boolean deleteByUser(String ids) {

        //查询之前的所有角色
        List<SysUserRole> sysUserRoleList = lambdaQuery().in(SysUserRole::getUserId, Arrays.asList(ids.split(StrUtil.COMMA))).list();
        removeByIds(sysUserRoleList.stream().map(SysUserRole::getId).collect(Collectors.toList()));

        return true;
    }

}
