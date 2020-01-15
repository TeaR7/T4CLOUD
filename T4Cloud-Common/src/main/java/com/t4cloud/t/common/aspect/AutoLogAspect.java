package com.t4cloud.t.common.aspect;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.t4cloud.t.common.annotation.AutoLog;
import com.t4cloud.t.common.entity.dto.T4Log;
import com.t4cloud.t.common.utils.IPUtils;
import com.t4cloud.t.common.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 *
 * @Author scott
 * @email jeecgos@163.com
 * @Date 2018年1月14日
 */
@Slf4j
@Aspect
@Component
public class AutoLogAspect {

    @Pointcut("@annotation(com.t4cloud.t.common.annotation.AutoLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result;
        try {
            //执行方法
            result = point.proceed();

            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;

            //保存日志
            saveSysLog(point, result, time);

        } catch (Exception e) {
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveSysLog(point, e, time);

            throw e;
        }


        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, Object result, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        T4Log sysLog = new T4Log();
        AutoLog syslog = method.getAnnotation(AutoLog.class);
        if (syslog != null) {
            //注解上的描述,操作日志内容
            sysLog.setLogContent(syslog.value());
            sysLog.setLogType(syslog.logType());
            sysLog.setOperateType(syslog.operateType());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSONObject.toJSONString(args);
            sysLog.setRequestParam(params);
        } catch (Exception e) {

        }

        //获取request
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        sysLog.setRequestUrl(request.getRequestURL().toString());
        sysLog.setRequestType(request.getMethod());

//		//获取登录用户信息
//		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//		if(sysUser!=null){
//			sysLog.setUserid(sysUser.getUsername());
//			sysLog.setUsername(sysUser.getRealname());
//
//		}
        //耗时
        sysLog.setCostTime(time);
        sysLog.setCreateTime(new Date());
        sysLog.setResult(JSONUtil.toJsonStr(result));

        //保存系统日志
//		sysLogService.save(sysLog);

        /** TODO 修改成投递MQ
         *
         * -by TeaR  -2020/1/15-17:45
         */

        log.info("========================= START " + sysLog.getLogContent() + " START =========================");
        log.info("Method \t: \t" + sysLog.getMethod());
        log.info("URL   \t:  \t" + sysLog.getRequestType() + ":" + sysLog.getRequestUrl());
        log.info("Param \t:  \t" + sysLog.getRequestParam());
        log.info("User  \t:  \t" + sysLog.getUsername() + "," + sysLog.getId());
        log.info("Result \t:  \t" + sysLog.getResult());
        log.info("========================= END" + sysLog.getLogContent() + " END =========================");

    }

}
