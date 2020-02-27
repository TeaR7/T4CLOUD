package com.t4cloud.t.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.t4cloud.t.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色表 实体类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-21 
 */
@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysRole对象", description = "角色表")
public class SysRole extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 角色名称
	*/
	@ApiModelProperty(value = "角色名称")
	private String roleName;
	/**
	* 角色编码
	*/
	@ApiModelProperty(value = "角色编码")
	private String roleCode;
	/**
	* 描述
	*/
	@ApiModelProperty(value = "描述")
	private String description;


}
