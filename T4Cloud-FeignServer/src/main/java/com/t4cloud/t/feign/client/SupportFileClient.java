package com.t4cloud.t.feign.client;

import com.t4cloud.t.base.entity.dto.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * SupportClient 调用support微服务
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @date 2020/3/2 15:33
 */
@FeignClient(name = "T4Cloud-Support" , contextId = "file")
public interface SupportFileClient {

    /**
     * 调用上传文件服务
     *
     * @param policy 权限类型,1:公开，2：私有
     * @param expire 有效期（天），公开的情况下：默认为30天，私有的情况下：默认为永久。传递-1代表永久有效
     * @param file   文件对象
     *               <p>
     * @return void
     * --------------------
     * @author TeaR
     * @date 2020/3/2 15:37
     */
    @PutMapping(value = "/file/upload" , consumes = "multipart/form-data")
    R<String> upload(
            @RequestParam(name = "policy" , required = false, defaultValue = "2") Integer policy,
            @RequestParam(name = "expire" , required = false, defaultValue = "0") Integer expire,
            MultipartFile file) throws Exception;

}
