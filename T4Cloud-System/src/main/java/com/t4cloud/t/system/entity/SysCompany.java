package com.t4cloud.t.system.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.t4cloud.t.base.annotation.Dict;
import com.t4cloud.t.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 公司表 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-15
 */
@Data
@TableName("sys_company")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysCompany对象" , description = "公司表")
public class SysCompany extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 公司名
     */
    @Excel(name = "公司名" , width = 7.5, orderNum = "0")
    @ApiModelProperty(value = "公司名")
    @NotNull(message = "公司名不允许为空")
    private String name;

    /**
     * 英文名
     */
    @Excel(name = "英文名" , width = 7.5, orderNum = "1")
    @ApiModelProperty(value = "英文名")
    private String nameEn;

    /**
     * 公司名缩写
     */
    @Excel(name = "公司名缩写" , width = 12.5, orderNum = "2")
    @ApiModelProperty(value = "公司名缩写")
    private String nameAbbr;

    /**
     * 描述
     */
    @Excel(name = "描述" , width = 5.0, orderNum = "3")
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式" , width = 10.0, orderNum = "4")
    @ApiModelProperty(value = "联系方式")
    private String phone;

    /**
     * 传真
     */
    @Excel(name = "传真" , width = 5.0, orderNum = "5")
    @ApiModelProperty(value = "传真")
    private String fax;

    /**
     * 地址
     */
    @Excel(name = "地址" , width = 5.0, orderNum = "6")
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 备注
     */
    @Excel(name = "备注" , width = 5.0, orderNum = "7")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态
     */
    @Excel(name = "状态" , width = 5.0, orderNum = "8" , dict = "common_status")
    @ApiModelProperty(value = "状态")
    @Dict(code = "common_status")
    private Integer status;


}
