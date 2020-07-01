package com.t4cloud.t.third.entity;

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
 * 第三方API秘钥配置 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-26
 */
@Data
@TableName("third_config")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "ThirdConfig对象", description = "第三方API秘钥配置")
public class ThirdConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 编号（可以用来区分同一应用下的公众号、小程序、H5等）
     */
    @Excel(name = "编号（可以用来区分同一应用下的公众号、小程序、H5等）", width = 67.5, orderNum = "0", dict = "code_type")
    @ApiModelProperty(value = "编号（可以用来区分同一应用下的公众号、小程序、H5等）")
    @NotNull(message = "编号（可以用来区分同一应用下的公众号、小程序、H5等）不允许为空")
    @Dict(code = "code_type")
    private Integer code;


    /**
     * 应用名称
     */
    @Excel(name = "应用名称", width = 20.0, orderNum = "1")
    @ApiModelProperty(value = "应用名称")
    private String name;

    /**
     * APPID
     */
    @Excel(name = "APPID", width = 20.0, orderNum = "1")
    @ApiModelProperty(value = "APPID")
    @NotNull(message = "APPID不允许为空")
    private String appId;

    /**
     * 对应的secret
     */
    @Excel(name = "对应的secret", width = 22.5, orderNum = "2")
    @ApiModelProperty(value = "对应的secret")
    private String secret;

    /**
     * 状态(1-正常,0-删除)
     */
    @Excel(name = "状态", width = 32.5, orderNum = "2", dict = "common_status")
    @ApiModelProperty(value = "状态")
    @Dict(code = "common_status")
    private Integer status;


}
