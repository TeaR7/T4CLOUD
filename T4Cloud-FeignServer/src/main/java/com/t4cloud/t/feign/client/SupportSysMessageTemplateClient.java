package com.t4cloud.t.feign.client;

import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.dto.SysMessageTemplateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息模板 Feign Client
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @since 2020-03-16 
 */
@FeignClient(name = "T4Cloud-Support", contextId = "sysMessageTemplate")
public interface SupportSysMessageTemplateClient {


    /**
     * 全部列表 消息模板
     */
    @GetMapping(value = "/SysMessageTemplate/list")
    R<List<SysMessageTemplateDTO>> list(@SpringQueryMap SysMessageTemplateDTO sysMessageTemplateDTO);

    /**
     * 新增 消息模板
     */
    @PutMapping(value = "/SysMessageTemplate/save")
    R<?> save(@RequestBody SysMessageTemplateDTO sysMessageTemplate);

    /**
     * 修改 消息模板
     */
    @PostMapping(value = "/SysMessageTemplate/update")
    R<?> update(@RequestBody SysMessageTemplateDTO sysMessageTemplate);

    /**
     * 删除 消息模板
     */
    @DeleteMapping(value = "/SysMessageTemplate/delete")
    R<List<SysMessageTemplateDTO>> delete(@RequestParam("ids") String ids);

}
