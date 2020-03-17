package com.t4cloud.t.support.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.t4cloud.t.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 消息模板 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-16 
 */
@Data
@TableName("sys_message_template")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysMessageTemplate对象", description = "消息模板")
public class SysMessageTemplate extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	* 模板CODE
	 */
	@Excel(name = "模板CODE", width = 15.0, orderNum = "0")
	@ApiModelProperty(value = "模板CODE")
	@NotNull(message = "模板CODE不允许为空")
	private String templateCode;

	/**
	* 模板标题
	 */
	@Excel(name = "模板标题", width = 10.0, orderNum = "1")
	@ApiModelProperty(value = "模板标题")
	private String templateName;

	/**
	* 模板类型：1短信 2邮件 3微信
	 */
	@Excel(name = "模板类型：1短信 2邮件 3微信", width = 40.0, orderNum = "2")
	@ApiModelProperty(value = "模板类型：1短信 2邮件 3微信")
	@NotNull(message = "模板类型：1短信 2邮件 3微信不允许为空")
	private String templateType;

	private String templateContent;




}
