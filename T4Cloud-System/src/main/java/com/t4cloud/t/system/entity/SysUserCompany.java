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
 * 用户公司表 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-15
 */
@Data
@TableName("sys_user_company")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysUserCompany对象" , description = "用户公司表")
public class SysUserCompany extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 用户id
     */
    @Excel(name = "用户id" , width = 10.0, orderNum = "0")
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 公司id
     */
    @Excel(name = "公司id" , width = 10.0, orderNum = "1")
    @ApiModelProperty(value = "公司id")
    private String companyId;

    /**
     * 用户id集合
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "用户id集合，用[,]分割")
    private String userIds;

}
