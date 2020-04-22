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

import javax.validation.constraints.NotBlank;

/**
 * 字典 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-04
 */
@Data
@TableName("sys_dict")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysDict对象" , description = "字典")
public class SysDict extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 字典名称
     */
    @Excel(name = "字典名称" , width = 10.0, orderNum = "0")
    @ApiModelProperty(value = "字典名称")
    @NotBlank(message = "字典名称不允许为空")
    private String name;

    /**
     * 字典编码
     */
    @Excel(name = "字典编码" , width = 10.0, orderNum = "1")
    @ApiModelProperty(value = "字典编码")
    @NotBlank(message = "字典编码不允许为空")
    private String code;

    /**
     * 描述
     */
    @Excel(name = "描述" , width = 5.0, orderNum = "2")
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 状态
     */
    @Excel(name = "删除状态" , width = 10.0, orderNum = "4" , dict = "common_status")
    @ApiModelProperty(value = "删除状态")
    @Dict(code = "common_status")
    private Integer status;


}
