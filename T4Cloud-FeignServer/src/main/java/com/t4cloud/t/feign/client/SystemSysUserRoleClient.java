package com.t4cloud.t.feign.client;

import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.annotation.cache.RoleCacheEvict;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.dto.SysRoleDTO;
import com.t4cloud.t.feign.dto.SysUserRoleDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户角色表 Feign Client
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @since 2020-03-17 
 */
@FeignClient(name = "T4Cloud-System", contextId = "sysUserRole",path = "SysUserRole")
public interface SystemSysUserRoleClient {


    /**
     * 全部列表 用户角色表
     */
    @GetMapping(value = "/getRoleList/{username}")
    R<List<SysRoleDTO>> getRoleList(@PathVariable("username") String username);

    /**
     * 新增 用户角色表
     */
    @PutMapping(value = "/saveByUser")
    R<?> saveByUser(@RequestBody SysUserRoleDTO sysUserRole);

    /**
     * 保存 用户保存角色
     */
    @DeleteMapping("/deleteByUser")
    R<?> deleteByUser(@RequestParam("ids") String ids);

}
