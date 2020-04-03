package com.t4cloud.t.support.service;


import com.t4cloud.t.base.service.T4Service;
import com.t4cloud.t.support.entity.SupResource;
import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 资源管理表 服务类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-02-23
 */
public interface ISupResourceService extends T4Service<SupResource> {

    /**
     * 根据文件后缀名识别contentType
     *
     * @param filenameExtension <p>
     * @return java.lang.String
     * --------------------
     * @author TeaR
     * @date 2020/1/3 21:57
     */
    String getContentType(String filenameExtension);

    /**
     * 上传文件到minio
     *
     * @param mf       文件对象
     * @param fileName 文件名
     * @param resource 资源对象
     *                 <p>
     * @return void
     * --------------------
     * @author TeaR
     * @date 2020/2/23 22:06
     */
    void uploadMinio(MultipartFile mf, String fileName, SupResource resource) throws MinioException;

    /**
     * 从minio下载文件
     *
     * @param resource 资源对象
     *                 <p>
     * @return void
     * --------------------
     * @author TeaR
     * @date 2020/2/23 22:06
     */
    InputStream downloadMinio(SupResource resource) throws MinioException;
}
