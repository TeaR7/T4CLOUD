package com.t4cloud.t.support.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.constant.CacheConstant;
import com.t4cloud.t.base.constant.ResultConstant;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.exception.T4CloudException;
import com.t4cloud.t.base.utils.JwtUtil;
import com.t4cloud.t.base.utils.RedisUtil;
import com.t4cloud.t.feign.client.SupportFileClient;
import com.t4cloud.t.support.entity.SupResource;
import com.t4cloud.t.support.service.ISupResourceService;
import io.minio.errors.MinioException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 * 资源管理表 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-02-23
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "资源", tags = "资源上传下载", position = 1)
@RequestMapping("/file")
public class FileController extends T4Controller<SupResource, ISupResourceService> implements SupportFileClient {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    /**
     * 资源上传 支持PUT和POST方法（正常应该是PUT，但是UNI-APP前段框架不支持PUT方法）
     */
    @Override
    @AutoLog(value = "资源上传", operateType = 1, logType = 5)
    @RequestMapping(value = "/upload", method = {RequestMethod.PUT, RequestMethod.POST})
    @ApiOperation(position = 1, value = "资源上传", notes = "传入文件和权限类型")
    public R<String> upload(
            @ApiParam("权限类型,1:公开，2：私有")
            @RequestParam(required = false, defaultValue = "2") Integer policy,

            @ApiParam("有效期（天），公开的情况下：默认为30天，私有的情况下：默认为永久。传递-1代表永久有效")
            @RequestParam(required = false, defaultValue = "0") Integer expire,

            @ApiParam("文件对象") MultipartFile file
    ) throws Exception {

        //准备资源对象
        SupResource resource = new SupResource();
        resource.setPolicy(policy);
        resource.setCount(0);

        //填充有效期
        if (expire > 0) {
            //说明有指定的时间
            resource.setExpireTime(DateUtil.offsetDay(DateUtil.date(), expire));
        } else if (expire == -1) {
            //永久（有效期100年）
            resource.setExpireTime(DateUtil.offset(DateUtil.date(), DateField.YEAR, 100));
        } else {
            //没有指定，采用默认策略
            if (policy == 2) {
                //私有的默认永久（有效期100年）
                resource.setExpireTime(DateUtil.offset(DateUtil.date(), DateField.YEAR, 100));
            } else {
                //公开的默认30天
                resource.setExpireTime(DateUtil.offsetDay(DateUtil.date(), 30));
            }
        }

        //处理资源名称
        String fileName;
        // 获取文件名
        String orgName = file.getOriginalFilename().replace("%", "");
        try {
            //生成新的文件名，防重名
            fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
            resource.setName(orgName);

            //获取后缀名
            String suffix = orgName.substring(orgName.lastIndexOf("."));
//        resource.setMimeType(service.getContentType(suffix));
            resource.setMimeType(MimeTypes.getDefaultMimeTypes().getMimeType(suffix).getName());
        } catch (Exception e) {
            /**
             * 上面是处理文件名，添加了时间戳并且处理了后缀名，识别文件类型
             * 如果遇到无法正确识别文件名（前端框架异常），可以考虑改写此处，不处理文件名和文件类型（放开下方注释即可，注释掉return）
             *
             * --------------------
             * @author TeaR
             * @date 2020/4/30 12:01
             */
            return R.error("文件名处理异常！请检查接口调用方式，或改写接口！");

            //fileName = orgName + "_" + System.currentTimeMillis();

        }

        //开启OSS上传
        service.uploadMinio(file, fileName, resource);

        //保存资源对象
        service.save(resource);

        return R.ok("资源上传成功", resource.getId());
    }


    /**
     * 详情
     */
    @AutoLog(value = "资源预览", logType = 5)
    @GetMapping("/view/{path}")
    @ApiOperation(position = 2, value = "资源预览", notes = "直接拼接相对文件路径即可，此接口header中的token如果有的话需要传递过来，如果访问的是私有资源的元，需要校验token的")
    public void view(
            @ApiParam("上传资源所得的资源路径")
            @PathVariable String path,
            HttpServletResponse response) throws Exception {

        //获取文件
        SupResource resource = service.getById(path);
        if (resource == null) {
            throw new T4CloudException("资源不存在！");
        }

        //此处不校验资源是否过期，目前设计资源过期标志只是意味着删除的权重较高

        //验证权限
        String token = getToken();
        String username = JwtUtil.getUsername(token);
        if (resource.getPolicy() == 2) {
            //站内私有的，需要有效的token

            //token简单校验（此方法并不完全可靠）
            String cacheToken = (String) redisUtil.get(CacheConstant.SYS_USERS_TOKEN + username + "-" + token);
            if (token == null || cacheToken == null || !token.equalsIgnoreCase(cacheToken)) {
                throw new AuthorizationException("token已失效，请重新登录");
            }
        }

        //获取文件流
        InputStream file = service.downloadMinio(resource);

        try {
            //对外输出
            response.setContentType(resource.getMimeType() + ";charset=utf-8");
            response.addHeader(ResultConstant.ACCESS_CONTROL_EXPOSE_HEADERS, ResultConstant.FILENAME);
            response.addHeader(ResultConstant.FILENAME, URLUtil.encode(resource.getName(), "UTF-8"));
            OutputStream outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = file.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();

            //投递MQ，记录资源访问更新信息（大量消息堆积，未被消费，暂时不知道原因）
//            resource.setUpdateBy(username);
//            rocketMQTemplate.convertAndSend(TOPIC_RESOURCE, resource);

            //本地直接记录
            resource.setCount(resource.getCount() + 1).setUpdateBy(username);
            service.updateById(resource);

        } catch (Exception e) {
            throw new MinioException("预览失败." + e.getMessage());
        }

    }

    /**
     * 资源下载
     */
    @AutoLog(value = "资源下载", logType = 5)
    @GetMapping("/download/{path}")
    @ApiOperation(position = 3, value = "资源下载", notes = "直接拼接相对文件路径即可，此接口header中的token如果有的话需要传递过来，如果访问的是私有资源的元，需要校验token的")
    public void download(
            @ApiParam("上传资源所得的资源路径")
            @PathVariable String path,
            HttpServletResponse response) throws Exception {

        //获取文件
        SupResource resource = service.getById(path);
        if (resource == null) {
            throw new T4CloudException("资源不存在！");
        }

        //此处不校验资源是否过期，目前设计资源过期标志只是意味着删除的权重较高

        //验证权限
        String token = getToken();
        String username = JwtUtil.getUsername(token);
        if (resource.getPolicy() == 2) {
            //站内私有的，需要有效的token

            //token简单校验（此方法并不完全可靠）
            String cacheToken = (String) redisUtil.get(CacheConstant.SYS_USERS_TOKEN + username + "-" + token);
            if (token == null || cacheToken == null || !token.equalsIgnoreCase(cacheToken)) {
                throw new AuthorizationException("token已失效，请重新登录");
            }
        }

        //获取文件流
        InputStream file = service.downloadMinio(resource);

        try {
            // 设置强制下载不打开  
            response.setContentType("application/force-download");
            response.addHeader(ResultConstant.ACCESS_CONTROL_EXPOSE_HEADERS, ResultConstant.FILENAME);
            response.addHeader(ResultConstant.FILENAME, URLUtil.encode(resource.getName(), "UTF-8"));
            response.addHeader("Content-Disposition", "attachment;fileName=" + new String(resource.getName().getBytes("UTF-8"), "iso-8859-1"));
            //对外输出流
            OutputStream outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = file.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();

            //投递MQ，记录资源访问更新信息（大量消息堆积，未被消费，暂时不知道原因）
//            resource.setUpdateBy(username);
//            rocketMQTemplate.convertAndSend(TOPIC_RESOURCE, resource);

            //本地直接记录
            resource.setCount(resource.getCount() + 1).setUpdateBy(username);
            service.updateById(resource);

        } catch (Exception e) {
            throw new MinioException("资源下载失败." + e.getMessage());
        }

    }


    /**
     * 视频预览
     */
    @AutoLog(value = "视频预览", logType = 5)
    @GetMapping("/video/{path}")
    @ApiOperation(position = 3, value = "视频预览", notes = "直接拼接相对文件路径即可，此接口header中的token如果有的话需要传递过来，如果访问的是私有资源的元，需要校验token的")
    public void video(
            @ApiParam("上传资源所得的资源路径")
            @PathVariable String path,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        //获取文件
        SupResource resource = service.getById(path);
        if (resource == null) {
            throw new T4CloudException("资源不存在！");
        }

        //此处不校验资源是否过期，目前设计资源过期标志只是意味着删除的权重较高

        //验证权限
        String token = getToken();
        String username = JwtUtil.getUsername(token);
        if (resource.getPolicy() == 2) {
            //站内私有的，需要有效的token

            //token简单校验（此方法并不完全可靠）
            String cacheToken = (String) redisUtil.get(CacheConstant.SYS_USERS_TOKEN + username + "-" + token);
            if (token == null || cacheToken == null || !token.equalsIgnoreCase(cacheToken)) {
                throw new AuthorizationException("token已失效，请重新登录");
            }
        }

        //获取文件流
        InputStream file = service.downloadMinio(resource);

        /** TODO 此处没做完
         *
         * -by TeaR  -2020/3/12-23:07
         */

        File tempFile = FileUtil.touch("/tmp/" + resource.getBucket() + "/" + resource.getPath());

        RandomAccessFile randomFile = new RandomAccessFile(tempFile, "r");//只读模式
        long contentLength = randomFile.length();
        String range = request.getHeader("Range");
        int start = 0, end = 0;
        if (range != null && range.startsWith("bytes=")) {
            String[] values = range.split("=")[1].split("-");
            start = Integer.parseInt(values[0]);
            if (values.length > 1) {
                end = Integer.parseInt(values[1]);
            }
        }
        int requestSize = 0;
        if (end != 0 && end > start) {
            requestSize = end - start + 1;
        } else {
            requestSize = Integer.MAX_VALUE;
        }

        response.setContentType("video/mp4");
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("ETag", resource.getPath());
        response.setHeader("Last-Modified", new Date().toString());
        //第一次请求只返回content length来让客户端请求多次实际数据
        if (range == null) {
            response.setHeader("Content-length", contentLength + "");
        } else {
            //以后的多次以断点续传的方式来返回视频数据
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);//206
            long requestStart = 0, requestEnd = 0;
            String[] ranges = range.split("=");
            if (ranges.length > 1) {
                String[] rangeDatas = ranges[1].split("-");
                requestStart = Integer.parseInt(rangeDatas[0]);
                if (rangeDatas.length > 1) {
                    requestEnd = Integer.parseInt(rangeDatas[1]);
                }
            }
            long length = 0;
            if (requestEnd > 0) {
                length = requestEnd - requestStart + 1;
                response.setHeader("Content-length", "" + length);
                response.setHeader("Content-Range", "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
            } else {
                length = contentLength - requestStart;
                response.setHeader("Content-length", "" + length);
                response.setHeader("Content-Range", "bytes " + requestStart + "-" + (contentLength - 1) + "/" + contentLength);
            }
        }
        ServletOutputStream out = response.getOutputStream();
        int needSize = requestSize;
        randomFile.seek(start);
        while (needSize > 0) {
            byte[] buffer = new byte[4096];
            int len = randomFile.read(buffer);
            if (needSize < buffer.length) {
                out.write(buffer, 0, needSize);
            } else {
                out.write(buffer, 0, len);
                if (len < buffer.length) {
                    break;
                }
            }
            needSize -= buffer.length;
        }
        randomFile.close();
        out.close();

    }

}
