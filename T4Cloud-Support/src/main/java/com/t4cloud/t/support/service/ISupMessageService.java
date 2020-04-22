package com.t4cloud.t.support.service;


import com.t4cloud.t.base.service.T4Service;
import com.t4cloud.t.support.entity.SupMessage;

/**
 * 消息列表 服务类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-31
 */
public interface ISupMessageService extends T4Service<SupMessage> {

    /**
     * mail发送
     *
     * @param size 单次发送数量
     *             <p>
     * @return int 成功发送数量
     * --------------------
     * @author TeaR
     * @date 2020/4/1 11:35
     */
    int mailSender(int size);

    /**
     * 短信发送
     *
     * @param size 单次发送数量
     *             <p>
     * @return int 成功发送数量
     * --------------------
     * @author TeaR
     * @date 2020/4/1 11:35
     */
    int smsSender(int size);

}
