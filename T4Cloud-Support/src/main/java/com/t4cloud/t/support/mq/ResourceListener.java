package com.t4cloud.t.support.mq;

import cn.hutool.json.JSONUtil;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.support.entity.SupResource;
import com.t4cloud.t.support.service.ISupResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.t4cloud.t.base.constant.MqConstant.TOPIC_RESOURCE;
import static com.t4cloud.t.base.constant.MqConstant.T_CONSUMER_GROUP;

/**
 * 资源访问记录处理器
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @date 2020/2/17 14:52
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = T_CONSUMER_GROUP, topic = TOPIC_RESOURCE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResourceListener implements RocketMQListener<SupResource> {

    private final ISupResourceService service;

    @Override
    @AutoLog(value = "资源访问MQ处理" , logType = 5, operateType = 3)
    public void onMessage(SupResource resource) {
        //获取最新的数据
        SupResource supResource = service.getById(resource.getId());
        if (supResource == null) {
            log.error("接受到的资源对象异常！" , resource);
            log.info("接受到的资源对象异常INFO！" , JSONUtil.toJsonStr(resource));
        }
        supResource.setCount(supResource.getCount() + 1)
                .setUpdateBy(resource.getUpdateBy())
                .setUpdateTime(resource.getUpdateTime());
        service.updateById(resource);
    }

}
