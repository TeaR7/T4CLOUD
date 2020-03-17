package com.t4cloud.t.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t4cloud.t.system.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表 Mapper 接口
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-21 
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户查询用户权限
     */
    List<SysRole> queryByUsername(@Param("username") String username);

}
