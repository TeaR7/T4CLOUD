package com.t4cloud.t.feign.client;

import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.dto.SupMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息列表 Feign Client
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @since 2020-03-31
 */
@FeignClient(name = "T4Cloud-Support" , contextId = "supMessage" , path = "/SupMessage")
public interface SupportSupMessageClient {


    /**
     * 全部列表 消息列表
     */
    @GetMapping(value = "/list")
    R<List<SupMessageDTO>> list(@SpringQueryMap SupMessageDTO supMessageDTO);

    /**
     * 新增 消息列表
     */
    @PutMapping(value = "/save")
    R<?> save(@RequestBody SupMessageDTO supMessage);

    /**
     * 新增 消息列表
     */
    @PutMapping(value = "/saveByTemplate")
    R<String> saveByTemplate(@RequestBody SupMessageDTO supMessage);


    /**
     * 修改 消息列表
     */
    @PostMapping(value = "/update")
    R<?> update(@RequestBody SupMessageDTO supMessage);

    /**
     * 删除 消息列表
     */
    @DeleteMapping(value = "/delete")
    R<List<SupMessageDTO>> delete(@RequestParam("ids") String ids);

}
