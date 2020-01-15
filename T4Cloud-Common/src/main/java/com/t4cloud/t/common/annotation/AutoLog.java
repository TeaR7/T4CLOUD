package com.t4cloud.t.common.annotation;


import java.lang.annotation.*;

/**
 * 自动完成日志记录
 * <p>
 * --------------------
 *
 * @author TeaR
 * @date 2020/1/15 17:26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {

    /**
     * 日志内容
     */
    String value();

    /**
     * 日志类型
     * 1.管理员操作
     * 2.登录日志
     * 3.用户操作
     * 4.定时任务
     * 5.其他日志
     */
    int logType() default 5;

    /**
     * 操作日志类型
     * 1.增
     * 2.删
     * 3.改
     * 4.查
     */
    int operateType() default 4;
}
