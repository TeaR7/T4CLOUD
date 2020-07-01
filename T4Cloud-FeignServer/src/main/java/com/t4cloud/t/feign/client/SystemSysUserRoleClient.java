package com.t4cloud.t.feign.client;

import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.dto.SysRoleDTO;
import com.t4cloud.t.feign.dto.SysUserRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
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
@FeignClient(name = "T4Cloud-System", contextId = "sysUserRole", path = "SysUserRole")
public interface SystemSysUserRoleClient {


    /**
     * 全部列表 用户角色表
     */
    @GetMapping(value = "/getRoleList/{userId}")
    R<List<SysRoleDTO>> getRoleList(@PathVariable("userId") String userId);

    /**
     * 根据角色ID获取所有用户Id
     */
    @GetMapping("/getUserIdList/{roleId}")
    R<List<String>> getUserIdList(@PathVariable("roleId") String roleId);

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

    /**
     * 新增 用户注册增加权限
     */
    @PutMapping(value = "/regUserRole")
    R<?> regUserRole(@RequestBody SysUserRoleDTO userRoleDTO);
}
