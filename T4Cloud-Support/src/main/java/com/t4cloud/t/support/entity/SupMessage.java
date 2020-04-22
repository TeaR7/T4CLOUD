package com.t4cloud.t.support.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t4cloud.t.base.annotation.Dict;
import com.t4cloud.t.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 消息列表 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-31
 */
@Data
@TableName("sup_message")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SupMessage对象" , description = "消息列表")
public class SupMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 消息标题
     */
    @Excel(name = "消息标题" , width = 10.0, orderNum = "0")
    @ApiModelProperty(value = "消息标题")
    private String title;

    /**
     * 发送方式：1短信 2邮件 3微信 4站内信
     */
    @Excel(name = "发送方式：1短信 2邮件 3微信 4站内信 " , width = 55.0, orderNum = "1" , dict = "msg_template_type")
    @ApiModelProperty(value = "发送方式：1短信 2邮件 3微信 4站内信 ")
    @NotNull(message = "发送方式不允许为空")
    @Dict(code = "msg_template_type")
    private Integer messageType;

    /**
     * 接收人
     */
    @Excel(name = "接收人" , width = 7.5, orderNum = "2")
    @ApiModelProperty(value = "接收人")
    @NotNull(message = "接收人不允许为空")
    private String target;

    /**
     * 动态参数：Json格式
     */
    @Excel(name = "动态参数：Json格式" , width = 27.5, orderNum = "3")
    @ApiModelProperty(value = "动态参数：Json格式")
    private String param;

    /**
     * 内容
     */
    @Excel(name = "内容" , width = 5.0, orderNum = "4")
    @ApiModelProperty(value = "内容")
    @NotNull(message = "消息内容不允许为空")
    private String content;

    /**
     * 推送时间
     */
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "推送时间" , width = 10.0, orderNum = "5" , format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "推送时间")
    private Date sendTime;

    /**
     * 发送次数 超过5次不再发送
     */
    @Excel(name = "发送次数 超过5次不再发送" , width = 32.5, orderNum = "6")
    @ApiModelProperty(value = "发送次数 超过5次不再发送")
    private Integer sendNum;

    /**
     * 推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送
     */
    @Excel(name = "推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送" , width = 75.0, orderNum = "7" , dict = "msg_send_status")
    @ApiModelProperty(value = "推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送")
    @Dict(code = "msg_send_status")
    private Integer sendStatus;

    /**
     * 推送失败原因
     */
    @Excel(name = "推送失败原因" , width = 15.0, orderNum = "8")
    @ApiModelProperty(value = "推送失败原因")
    private String sendResult;

    /**
     * 备注
     */
    @Excel(name = "备注" , width = 5.0, orderNum = "9")
    @ApiModelProperty(value = "备注")
    private String remark;


    // ----------------------------------------------- DTO -----------------------------------------------

    /**
     * 模板的Code，如果存在模板ID，就需要渲染内容
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "模板的Code")
    private String messageTemplateCode;

}
