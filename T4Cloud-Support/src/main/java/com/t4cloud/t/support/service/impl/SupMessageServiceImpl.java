package com.t4cloud.t.support.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.base.utils.EmailUtil;
import com.t4cloud.t.support.entity.SupMessage;
import com.t4cloud.t.support.mapper.SupMessageMapper;
import com.t4cloud.t.support.service.ISupMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 消息列表 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-31
 */
@Slf4j
@Service
@AllArgsConstructor
public class SupMessageServiceImpl extends T4ServiceImpl<SupMessageMapper, SupMessage> implements ISupMessageService {


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
    @Override
    public int mailSender(int size) {
        int successCount = 0;

        //读取所有待发送的邮件
        Page<SupMessage> supMessagePage = lambdaQuery()
                .eq(SupMessage::getMessageType, 2).in(SupMessage::getSendStatus, 0, 2)
                .le(SupMessage::getSendTime, DateUtil.date())
                .orderByAsc(SupMessage::getSendTime).page(new Page<>(1, size));

        for (SupMessage record : supMessagePage.getRecords()) {
            record.setSendNum(record.getSendNum() + 1);
            record.setUpdateBy("mailSender任务");
            try {
                EmailUtil.SendMsg(record.getTarget(), record.getTitle(), record.getContent());
                record.setSendStatus(1);
                //成功计数器+1
                successCount++;
                log.debug("邮件发送成功，title：" + record.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
                if (record.getSendNum() >= 5) {
                    record.setSendStatus(-1);
                } else {
                    record.setSendStatus(2);
                }
                record.setSendResult(e.getMessage());
                log.error("邮件发送失败，ID：" + record.getId());
            }
            //更新发送记录
            updateById(record);
        }

        return successCount;
    }
}
