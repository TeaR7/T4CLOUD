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

/**
 * 字典详细值 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-04
 */
@Data
@TableName("sys_dict_value")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysDictValue对象" , description = "字典详细值")
public class SysDictValue extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 字典id
     */
    @Excel(name = "字典id" , width = 10.0, orderNum = "0")
    @ApiModelProperty(value = "字典id")
    private String dictId;

    /**
     * 字典项文本
     */
    @Excel(name = "字典项文本" , width = 12.5, orderNum = "1")
    @ApiModelProperty(value = "字典项文本")
    private String text;

    /**
     * 字典项值
     */
    @Excel(name = "字典项值" , width = 10.0, orderNum = "2")
    @ApiModelProperty(value = "字典项值")
    private Integer value;

    /**
     * 描述
     */
    @Excel(name = "描述" , width = 5.0, orderNum = "3")
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 排序
     */
    @Excel(name = "排序" , width = 5.0, orderNum = "4")
    @ApiModelProperty(value = "排序")
    private Integer position;

    /**
     * 状态（1启用 0不启用）
     */
    @Excel(name = "状态（1启用 0不启用）" , width = 30.0, orderNum = "5" , dict = "common_status")
    @ApiModelProperty(value = "状态（1启用 0不启用）")
    @Dict(code = "common_status")
    private Integer status;


}
