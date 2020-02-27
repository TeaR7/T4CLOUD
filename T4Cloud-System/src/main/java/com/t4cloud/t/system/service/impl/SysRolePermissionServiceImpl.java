package com.t4cloud.t.system.service.impl;

import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.system.entity.SysRolePermission;
import com.t4cloud.t.system.mapper.SysRolePermissionMapper;
import com.t4cloud.t.system.service.ISysRolePermissionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色权限表 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-21 
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysRolePermissionServiceImpl extends T4ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

}
