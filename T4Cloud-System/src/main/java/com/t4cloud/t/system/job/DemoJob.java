package com.t4cloud.t.system.job;

import cn.hutool.json.JSONUtil;
import com.t4cloud.t.system.entity.SysPermission;
import com.t4cloud.t.system.service.ISysPermissionService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Slf4j
@Component
public class DemoJob {

    @Autowired
    private ISysPermissionService sysPermissionService;

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJob")
    public ReturnT demoJobHandler(String param) throws Exception {
        log.info("Test job, Hello World.");

        log.info("param：" + param);

        //测试service注入
        List<SysPermission> teaR = sysPermissionService.queryByUsername(param);
        log.info(JSONUtil.toJsonStr(teaR));

        return ReturnT.SUCCESS;
    }

}
