package com.t4cloud.t.user.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t4cloud.t.base.annotation.RSA;
import com.t4cloud.t.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 通用示例 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-31
 */
@Data
@TableName("example_common")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "ExampleCommon对象" , description = "通用示例")
public class ExampleCommon extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 字符串查询
     */
    @Excel(name = "字符串查询" , width = 12.5, orderNum = "0")
    @ApiModelProperty(value = "字符串查询")
    @NotNull(message = "字符串查询不允许为空")
    private String str;

    /**
     * 字符串加密传输
     */
    @Excel(name = "字符串加密传输" , width = 12.5, orderNum = "0")
    @ApiModelProperty(value = "字符串加密传输")
    @RSA
    private String encryptStr;


    /**
     * 长文本查询
     */
    @Excel(name = "长文本查询" , width = 12.5, orderNum = "1")
    @ApiModelProperty(value = "长文本查询")
    private String txt;

    /**
     * 数字类型查询
     */
    @Excel(name = "数字类型查询" , width = 15.0, orderNum = "2")
    @ApiModelProperty(value = "数字类型查询")
    @NotNull(message = "数字类型查询不允许为空")
    private Integer num;

    /**
     * 日期查询
     */
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期查询" , width = 10.0, orderNum = "3" , format = "yyyy-MM-dd")
    @ApiModelProperty(value = "日期查询")
    private Date queryDate;

    /**
     * 日期时间查询
     */
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "日期时间查询" , width = 15.0, orderNum = "4" , format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "日期时间查询")
    private Date queryDateTime;

    /**
     * 时间查询
     */
    @JsonFormat(timezone = "GMT+8" , pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @Excel(name = "时间查询" , width = 10.0, orderNum = "5" , format = "HH:mm:ss")
    @ApiModelProperty(value = "时间查询")
    private Date queryTime;

    /**
     * 图片演示
     */
    @Excel(name = "图片演示" , orderNum = "6" , type = 2, imageType = 1)
    @ApiModelProperty(value = "图片演示")
    private String picture;

    /**
     * 音频演示
     */
    @Excel(name = "音频演示" , width = 15.0, orderNum = "7")
    @ApiModelProperty(value = "音频演示")
    private String audio;

    /**
     * 文件演示
     */
    @Excel(name = "文件演示" , width = 15.0, orderNum = "8")
    @ApiModelProperty(value = "文件演示")
    private String file;


}
