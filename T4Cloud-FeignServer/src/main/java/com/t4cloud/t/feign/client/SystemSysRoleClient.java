package com.t4cloud.t.feign.client;

import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.dto.SysRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * SupportClient 调用support微服务
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @date 2020/3/2 15:33
 */
@FeignClient(name = "T4Cloud-System", contextId = "sysRole", path = "/SysRole")
public interface SystemSysRoleClient {

    @GetMapping(value = "/list")
    R<List<SysRoleDTO>> list(@SpringQueryMap SysRoleDTO sysRole);

    /**
     * 获取APP端用户权限，传入角色名即可，没有该角色则会创建一个
     *
     * @param roleCode 角色编码
     *                 <p>
     * @return com.t4cloud.t.base.entity.dto.R<com.t4cloud.t.feign.dto.SysRoleDTO>
     * --------------------
     * @author TeaR
     * @date 2020/4/28 15:02
     */
    @GetMapping(value = "/appUserRole")
    R<SysRoleDTO> appUserRole(@RequestParam("roleCode") String roleCode);

}
