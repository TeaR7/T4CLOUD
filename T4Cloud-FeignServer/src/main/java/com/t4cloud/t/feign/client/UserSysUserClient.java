package com.t4cloud.t.feign.client;

import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.dto.SysUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户表 Feign Client
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @since 2020-03-17
 */
@FeignClient(name = "T4Cloud-User" , contextId = "sysUser" , path = "/SysUser")
public interface UserSysUserClient {

    /**
     * 全部列表 用户表
     */
    @GetMapping(value = "/detail")
    R<SysUserDTO> detail(@SpringQueryMap SysUserDTO sysUserDTO);

    /**
     * 全部列表 用户表
     */
    @GetMapping(value = "/list")
    R<List<SysUserDTO>> list(@SpringQueryMap SysUserDTO sysUserDTO);

    /**
     * 新增 用户表
     */
    @PutMapping(value = "/save")
    R<?> save(@RequestBody SysUserDTO sysUser);

    /**
     * 修改 用户表
     */
    @PostMapping(value = "/update")
    R<?> update(@RequestBody SysUserDTO sysUser);

    /**
     * 删除 用户表
     */
    @DeleteMapping(value = "/delete")
    R<List<SysUserDTO>> delete(@RequestParam("ids") String ids);

}
