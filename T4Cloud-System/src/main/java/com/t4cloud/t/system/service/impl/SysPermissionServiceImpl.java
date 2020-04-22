package com.t4cloud.t.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.t4cloud.t.base.annotation.cache.PermissionCacheable;
import com.t4cloud.t.base.entity.BaseEntity;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.system.entity.SysPermission;
import com.t4cloud.t.system.mapper.SysPermissionMapper;
import com.t4cloud.t.system.service.ISysPermissionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-13
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysPermissionServiceImpl extends T4ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    /**
     * 查询用户有的所有权限
     *
     * @param userId 用户ID
     *               <p>
     * @return java.util.List<com.t4cloud.t.system.entity.SysPermission>
     * --------------------
     * @author TeaR
     * @date 2020/2/13 13:18
     */
    @Override
    @PermissionCacheable
    public List<SysPermission> queryByUserId(String userId) {
        return baseMapper.queryByUserId(userId);
    }


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
    @Override
    public void tree(List<SysPermission> list, List<SysPermission> allData) {
        for (SysPermission sysPermission : list) {
            List<SysPermission> permissions = allData.stream().filter(item -> sysPermission.getId().equalsIgnoreCase(item.getParentId())).collect(Collectors.toList());
            if (permissions == null || permissions.size() == 0) {
                continue;
            }
            //排序
            permissions = permissions.stream().sorted(Comparator.comparing(SysPermission::getSortNo)).collect(Collectors.toList());
            tree(permissions, allData);
            sysPermission.setChildren(permissions);
        }
    }

    /**
     * 获取权限的子集
     *
     * @param ids 树状结构一级节点 id 列表
     *            <p>
     * @return void
     * --------------------
     * @author TeaR
     * @date 2020/2/13 14:33
     */
    @Override
    public void findChildren(List<String> ids, List<String> all) {
        //查询所有子节点
        List<String> permissions = lambdaQuery().in(SysPermission::getParentId, ids).list().stream().map(BaseEntity::getId).collect(Collectors.toList());
        //无子节点，结束
        if (CollectionUtil.isEmpty(permissions)) {
            return;
        }
        //存在子节点，继续往下寻找
        findChildren(permissions, all);
        //添加入原列表中
        all.addAll(permissions);
    }
}
