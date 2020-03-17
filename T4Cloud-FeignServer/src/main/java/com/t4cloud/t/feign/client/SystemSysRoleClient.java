package com.t4cloud.t.feign.client;

import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.dto.SysRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * SupportClient 调用support微服务
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @date 2020/3/2 15:33
 */
@FeignClient(name = "T4Cloud-System", contextId = "SysRole")
public interface SystemSysRoleClient {

    @GetMapping(value = "/SysRole/list")
    R<List<SysRoleDTO>> list(@SpringQueryMap SysRoleDTO sysRole);

}
