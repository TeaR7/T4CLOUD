package com.t4cloud.t.feign.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息模板 DTO类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-16 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysMessageTemplateDTO对象", description = "消息模板")
public class SysMessageTemplateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "数据标识，主键")
	private String id;


	/**
	* 模板CODE
	 */
	@ApiModelProperty(value = "模板CODE")
	private String templateCode;
	/**
	* 模板标题
	 */
	@ApiModelProperty(value = "模板标题")
	private String templateName;
	/**
	* 模板类型：1短信 2邮件 3微信
	 */
	@ApiModelProperty(value = "模板类型：1短信 2邮件 3微信")
	private String templateType;
	private String templateContent;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人（用户名，唯一）")
	protected String createBy;

	/**
     * 创建时间
     */
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date updateTime;
}
