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
    @Override
    public SysRole appUserRole(String roleCode) {
        //查询系统角色
        SysRole sysRole = lambdaQuery().eq(SysRole::getRoleCode, roleCode).one();
        if (sysRole == null) {
            //如果不存在则新增
            sysRole = new SysRole()
                    .setRoleCode(roleCode)
                    .setRoleName("未归类APP用户")
                    .setDescription("不存在指定角色，系统自动添加");
            save(sysRole);
        }
        return sysRole;
    }
}
