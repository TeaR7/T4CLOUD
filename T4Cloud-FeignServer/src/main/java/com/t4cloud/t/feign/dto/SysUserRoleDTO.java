package com.t4cloud.t.feign.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色表 DTO类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-17 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUserRoleDTO对象", description = "用户角色表")
public class SysUserRoleDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "数据标识，主键")
	private String id;


	/**
	* 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private String userId;
	/**
	* 角色id
	 */
	@ApiModelProperty(value = "角色id")
	private String roleId;

	/**
	 * 角色id集合
	 */
	@ApiModelProperty(value = "角色id集合，用[,]分割")
	private String roleIds;

	/**
	 * 用户id集合
	 */
	@ApiModelProperty(value = "用户id集合，用[,]分割")
	private String userIds;

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
