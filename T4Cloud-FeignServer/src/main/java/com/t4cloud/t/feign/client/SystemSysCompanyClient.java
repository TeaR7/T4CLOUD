package com.t4cloud.t.feign.client;

import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.dto.SysCompanyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公司表 Feign Client
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @since 2020-04-15
 */
@FeignClient(name = "T4Cloud-System" , contextId = "sysCompany" , path = "/SysCompany")
public interface SystemSysCompanyClient {


    /**
     * 全部列表 公司表
     */
    @GetMapping(value = "/list")
    R<List<SysCompanyDTO>> list(@SpringQueryMap SysCompanyDTO sysCompanyDTO);

    /**
     * 新增 公司表
     */
    @PutMapping(value = "/save")
    R<?> save(@RequestBody SysCompanyDTO sysCompany);

    /**
     * 修改 公司表
     */
    @PostMapping(value = "/update")
    R<?> update(@RequestBody SysCompanyDTO sysCompany);

    /**
     * 删除 公司表
     */
    @DeleteMapping(value = "/delete")
    R<List<SysCompanyDTO>> delete(@RequestParam("ids") String ids);

}
