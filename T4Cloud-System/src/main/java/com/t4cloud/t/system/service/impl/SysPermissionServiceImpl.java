package com.t4cloud.t.system.service.impl;

import com.t4cloud.t.base.constant.CacheConstant;
import com.t4cloud.t.system.entity.SysPermission;
import com.t4cloud.t.system.mapper.SysPermissionMapper;
import com.t4cloud.t.system.service.ISysPermissionService;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;

import java.util.List;

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
     * @param username 用户名
     *                 <p>
     * @return java.util.List<com.t4cloud.t.system.entity.SysPermission>
     * --------------------
     * @author TeaR
     * @date 2020/2/13 13:18
     */
    @Override
//    @Cacheable(value = CacheConstant.SYS_USER_PERMISSIONS)
    public List<SysPermission> queryByUsername(String username) {
        return baseMapper.queryByUsername(username);
    }
}
