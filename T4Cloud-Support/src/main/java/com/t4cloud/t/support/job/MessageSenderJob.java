package com.t4cloud.t.support.job;

import cn.hutool.json.JSONObject;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.support.service.ISupMessageService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * XxlJob开发示例（Bean模式）
 * <p>
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageSenderJob {

    private final ISupMessageService supMessageService;

    /**
     * 定时推送消息
     */
    @XxlJob("messageSender")
    @AutoLog(value = "定时推送消息" , logType = 4, operateType = 3)
    public ReturnT messageSender(String param) {
        log.debug("Test messageSender, Hello World.");
        //读取所有待发送的邮件
        int mailCount = supMessageService.mailSender(5);
        //读取所有待发送短信
        int smsCount = supMessageService.smsSender(5);

        //结果集
        JSONObject data = new JSONObject();
        data.put("mailCount" , mailCount);
        data.put("smsCount" , smsCount);

        return ReturnT.SUCCESS.setMsg("定时推送消息成功").setContent(data);
    }

}
