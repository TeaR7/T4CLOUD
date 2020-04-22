package com.t4cloud.t.feign.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息列表 DTO类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-31
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SupMessageDTO对象" , description = "消息列表")
public class SupMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人（用户名，唯一）")
    protected String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人（用户名，唯一）")
    protected String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;
    @ApiModelProperty(value = "数据标识，主键")
    private String id;
    /**
     * 消息标题
     */
    @ApiModelProperty(value = "消息标题")
    private String title;
    /**
     * 发送方式：1短信 2邮件 3微信 4站内信
     */
    @ApiModelProperty(value = "发送方式：1短信 2邮件 3微信 4站内信 ")
    private String messageType;
    /**
     * 接收人
     */
    @ApiModelProperty(value = "接收人")
    private String target;
    /**
     * 动态参数：Json格式
     */
    @ApiModelProperty(value = "动态参数：Json格式")
    private String param;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;
    /**
     * 推送时间
     */
    @ApiModelProperty(value = "推送时间")
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    /**
     * 发送次数 超过5次不再发送
     */
    @ApiModelProperty(value = "发送次数 超过5次不再发送")
    private Integer sendNum;
    /**
     * 推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送
     */
    @ApiModelProperty(value = "推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送")
    private String sendStatus;
    /**
     * 推送失败原因
     */
    @ApiModelProperty(value = "推送失败原因")
    private String sendResult;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 模板的Code，如果存在模板ID，就需要渲染内容
     */
    @ApiModelProperty(value = "模板的Code")
    private String messageTemplateCode;
}
