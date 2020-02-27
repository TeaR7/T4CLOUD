package com.t4cloud.t.support.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.t4cloud.t.base.entity.LoginUser;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.base.utils.UserUtil;
import com.t4cloud.t.support.entity.SysResource;
import com.t4cloud.t.support.mapper.SysResourceMapper;
import com.t4cloud.t.support.service.ISysResourceService;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 资源管理表 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-02-23
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class SysResourceServiceImpl extends T4ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {

    @Autowired
    private MinioClient minioClient;

    @Value("${t4cloud.minio.endpoint}")
    private String endpoint;
    @Value("${t4cloud.minio.bucket}")
    private String bucket;

    /**
     * 根据文件后缀名识别contentType
     *
     * @param filenameExtension <p>
     * @return java.lang.String
     * --------------------
     * @author TeaR
     * @date 2020/1/3 21:57
     */
    @Override
    public String getContentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase(".BMP")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase(".GIF")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase(".MP4")) {
            return "video/mp4";
        }
        if (filenameExtension.equalsIgnoreCase(".JPEG")
                || filenameExtension.equalsIgnoreCase(".JPG")
                || filenameExtension.equalsIgnoreCase(".PNG")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase(".HTML")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase(".TXT")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase(".VSD")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase(".PPTX")
                || filenameExtension.equalsIgnoreCase(".PPT")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase(".DOCX")
                || filenameExtension.equalsIgnoreCase(".DOC")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase(".XML")) {
            return "text/xml";
        }
        if (filenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        return null;
    }


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
    @Override
    public void uploadMinio(MultipartFile mf, String fileName, SysResource resource) throws MinioException {
        //构建上传路径
        LoginUser user = UserUtil.getCurrentUser();
        String path = "/" + user.getUsername() + "/" + DateUtil.year(DateUtil.date()) + "/" + fileName;
        if (resource.getPolicy() == 1) {
            path = "/open" + path;
        }
        try {
            // 检查存储桶是否已经存在，如果不存在则创建该桶
            if (!minioClient.bucketExists(bucket)) {
                minioClient.makeBucket(bucket);
            }
            // 使用putObject上传一个文件到存储桶中。
            minioClient.putObject(bucket, path, mf.getInputStream(), "application/octet-stream");
        } catch (Exception e) {
            e.printStackTrace();
            throw new MinioException("文件上传失败");
        }
        //相对路径
        resource.setPath(bucket + path);
        //绝对路径
        resource.setUrl(endpoint + bucket + path);
    }

    /**
     * 从minio下载文件
     *
     * @param resource 资源对象
     *             <p>
     * @return void
     * --------------------
     * @author TeaR
     * @date 2020/2/23 22:06
     */
    @Override
    public InputStream downloadMinio(SysResource resource) throws MinioException {
        try {
            //拆分path中的bucket和filename
            return minioClient.getObject(resource.getBucket(), resource.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MinioException("文件获取失败");
        }
    }

}
