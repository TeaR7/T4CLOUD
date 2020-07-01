package com.t4cloud.t.feign.client;

import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.dto.SysUserThirdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 微信用户录数据 Feign Client
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @since 2020-04-07
 */
@FeignClient(name = "T4Cloud-Third", contextId = "WeUser", path = "/WeUser")
public interface WechatUserClient {


    /**
     * 微信登录鉴权
     */
    @PostMapping(value = "/oauth")
    R<SysUserThirdDTO> userOauth(@RequestParam(value = "code") String code);

}
