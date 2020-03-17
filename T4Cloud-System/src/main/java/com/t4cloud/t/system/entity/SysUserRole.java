package com.t4cloud.t.system.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.t4cloud.t.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户角色表 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-02-27 
 */
@Data
@TableName("sys_user_role")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysUserRole对象", description = "用户角色表")
public class SysUserRole extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	* 用户id
	 */
	@Excel(name = "用户id", width = 10.0, orderNum = "0")
	@ApiModelProperty(value = "用户id")
	private String userId;
	/**
	* 角色id
	 */
	@Excel(name = "角色id", width = 10.0, orderNum = "1")
	@ApiModelProperty(value = "角色id")
	private String roleId;

	/**
	 * 角色id集合
	 */
	@TableField(exist = false)
	@ApiModelProperty(value = "角色id集合，用[,]分割")
	private String roleIds = "";

	/**
	 * 用户id集合
	 */
	@TableField(exist = false)
	@ApiModelProperty(value = "用户id集合，用[,]分割")
	private String userIds;


}
