package com.t4cloud.t.system.mq;

import com.t4cloud.t.base.entity.T4Log;
import com.t4cloud.t.service.service.IT4CommonService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.t4cloud.t.base.constant.MqConstant.TOPIC_LOG;
import static com.t4cloud.t.base.constant.MqConstant.T_CONSUMER_GROUP;

/**
 * MQ日志处理器
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @date 2020/2/17 14:52
 */
@Service
@RocketMQMessageListener(consumerGroup = T_CONSUMER_GROUP,topic = TOPIC_LOG)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogListener implements RocketMQListener<T4Log> {

    private final IT4CommonService service;

    @Override
    public void onMessage(T4Log t4Log) {
        t4Log.setCreateBy("MQ入库");
        service.saveLog(t4Log);
    }

}
