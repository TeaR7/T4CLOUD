package com.t4cloud.t.feign.client;

import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.dto.SysUserThirdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 钉钉用户登录数据 Feign Client
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @since 2020-06-28
 */
@FeignClient(name = "T4Cloud-Third", contextId = "DingUser", path = "/DingUser")
public interface DingUserClient {


    /**
     * 钉钉内H5登录鉴权
     */
    @PostMapping(value = "/oauth")
    R<SysUserThirdDTO> userOauth(@RequestParam(value = "code") String code);

}
