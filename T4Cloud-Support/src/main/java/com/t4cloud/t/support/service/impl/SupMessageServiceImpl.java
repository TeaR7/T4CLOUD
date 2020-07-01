package com.t4cloud.t.support.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.t4cloud.t.base.entity.SmsTemplate;
import com.t4cloud.t.base.exception.T4CloudException;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.base.utils.EmailUtil;
import com.t4cloud.t.base.utils.SmsUtil;
import com.t4cloud.t.support.entity.SupMessage;
import com.t4cloud.t.support.entity.SupMessageTemplate;
import com.t4cloud.t.support.mapper.SupMessageMapper;
import com.t4cloud.t.support.service.ISupMessageService;
import com.t4cloud.t.support.service.ISupMessageTemplateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    @Autowired
    private ISupMessageTemplateService supMessageTemplateService;

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
        List<SupMessage> supMessageList = baseMapper.getTodoMessage(2, Arrays.asList(0, 2), DateUtil.date(), size);

        for (SupMessage record : supMessageList) {
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
            baseMapper.changeMessageStatus(record.getId(), record.getSendNum(), record.getUpdateBy(), record.getSendStatus(), record.getSendResult());
        }

        return successCount;
    }

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
    @Override
    public int smsSender(int size) {
        int successCount = 0;

        //读取所有待发送的短信
        List<SupMessage> supMessageList = baseMapper.getTodoMessage(1, Arrays.asList(0, 2), DateUtil.date(), size);

        for (SupMessage record : supMessageList) {
            record.setSendNum(record.getSendNum() + 1);
            record.setUpdateBy("短信发送任务");
            try {
                //默认当做国内手机号
                String areaCode = "086";
                String phone = record.getTarget();
                if (record.getTarget().contains("-")) {
                    areaCode = record.getTarget().split("-")[0];
                    phone = record.getTarget().split("-")[1];
                }

                if (areaCode.equalsIgnoreCase("086") || areaCode.equalsIgnoreCase("86")) {
                    //发送国内短信

                    //获取短信编码信息
                    SmsTemplate template = new SmsTemplate();
                    JSONObject jsonObject = JSONUtil.parseObj(record.getContent());
                    template.setSignName(jsonObject.getStr("signName"));
                    template.setCode(jsonObject.getStr("code"));

                    //填充短信参数
                    boolean sendSms = SmsUtil.sendSms(phone, JSON.parseObject(record.getParam()), template);
                    if (!sendSms) {
                        throw new T4CloudException("短信发送失败");
                    }
                } else {
                    //发送国外短信
                    throw new T4CloudException("暂时不支持国际短信");
                }

                record.setSendStatus(1);
                //成功计数器+1
                successCount++;
                log.debug("短信发送成功，name：" + record.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
                //短信重试3次
                if (record.getSendNum() >= 3) {
                    record.setSendStatus(-1);
                } else {
                    record.setSendStatus(2);
                }
                record.setSendResult(e.getMessage());
                log.error("短信发送失败，ID：" + record.getId());
            }
            //更新发送记录
            baseMapper.changeMessageStatus(record.getId(), record.getSendNum(), record.getUpdateBy(), record.getSendStatus(), record.getSendResult());
        }

        return successCount;
    }

    /**
     * 根据模板发送消息
     *
     * @param supMessage 消息内容
     *                   <p>
     * @return boolean
     * --------------------
     * @author TeaR
     * @date 2020/6/8 17:35
     */
    @Override
    public boolean saveByTemplate(SupMessage supMessage) {
        //获取模板
        SupMessageTemplate supMessageTemplate = supMessageTemplateService.
                lambdaQuery().eq(SupMessageTemplate::getTemplateCode, supMessage.getMessageTemplateCode()).one();
        if (supMessageTemplate == null) {
            throw new T4CloudException("找不到该模板");
        }

        switch (supMessageTemplate.getTemplateType()) {
            case 1:
                //从模板确定类型
                supMessage.setMessageType(supMessageTemplate.getTemplateType());
                supMessage.setTitle(supMessageTemplate.getTemplateName());
                supMessage.setContent(supMessageTemplate.getTemplateContent());
                break;
            case 2:
                //从模板生成内容
                String title = supMessageTemplate.getTemplateName();
                String content = supMessageTemplate.getTemplateContent();
                JSONObject param = JSONUtil.parseObj(supMessage.getParam());
                for (String key : param.keySet()) {
                    title = title.replace("${" + key + "}", param.getStr(key));
                    content = content.replace("${" + key + "}", param.getStr(key));
                }
                //从模板确定类型
                supMessage.setMessageType(supMessageTemplate.getTemplateType());
                supMessage.setTitle(title);
                supMessage.setContent(content);
                break;
            default:
                throw new T4CloudException("暂不支持该类消息发送");
        }

        if (ObjectUtil.isEmpty(supMessage.getSendTime())) {
            //如果没有填写，就填充当前时间，即刻发送
            supMessage.setSendTime(DateUtil.date());
        }

        return save(supMessage);
    }
}
