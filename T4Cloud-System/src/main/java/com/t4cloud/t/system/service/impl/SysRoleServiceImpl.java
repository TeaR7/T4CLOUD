package com.t4cloud.t.system.service.impl;

import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.system.entity.SysRole;
import com.t4cloud.t.system.mapper.SysRoleMapper;
import com.t4cloud.t.system.service.ISysRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色表 服务实现类
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
public class SysRoleServiceImpl extends T4ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

}
