package com.t4cloud.t.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t4cloud.t.system.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表 Mapper 接口
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-13
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据用户查询用户权限
     */
    List<SysPermission> queryByUserId(@Param("userId") String userId);

}
