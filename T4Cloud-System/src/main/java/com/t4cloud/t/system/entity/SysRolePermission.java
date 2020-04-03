package com.t4cloud.t.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.t4cloud.t.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 角色权限表 实体类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-21
 */
@Data
@TableName("sys_role_permission")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysRolePermission对象", description = "角色权限表")
public class SysRolePermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @NotBlank
    @ApiModelProperty(value = "角色id")
    private String roleId;
    /**
     * 权限id
     */
    @ApiModelProperty(value = "权限id")
    private String permissionId;

    /**
     * 批量传入权限ID
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "批量传入权限ID，用,分割")
    private String permissionIds;


}
