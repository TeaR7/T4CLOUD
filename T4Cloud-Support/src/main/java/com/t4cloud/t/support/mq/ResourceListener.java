package com.t4cloud.t.support.mq;

import com.t4cloud.t.support.entity.SysResource;
import com.t4cloud.t.support.service.ISysResourceService;
import lombok.RequiredArgsConstructor;
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
@Service
@RocketMQMessageListener(consumerGroup = T_CONSUMER_GROUP, topic = TOPIC_RESOURCE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResourceListener implements RocketMQListener<SysResource> {

    private final ISysResourceService service;

    @Override
    public void onMessage(SysResource resource) {
        //获取最新的数据
        SysResource sysResource = service.getById(resource.getId());
        sysResource.setCount(sysResource.getCount() + 1)
                .setUpdateBy(resource.getUpdateBy())
                .setUpdateTime(resource.getUpdateTime());
        service.updateById(resource);
    }

}
