package com.t4cloud.t.feign.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司表 DTO类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-15
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysCompanyDTO对象" , description = "公司表")
public class SysCompanyDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人（用户名，唯一）")
    protected String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;
    @ApiModelProperty(value = "数据标识，主键")
    private String id;
    /**
     * 公司名
     */
    @ApiModelProperty(value = "公司名")
    private String name;
    /**
     * 英文名
     */
    @ApiModelProperty(value = "英文名")
    private String nameEn;
    /**
     * 公司名缩写
     */
    @ApiModelProperty(value = "公司名缩写")
    private String nameAbbr;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String phone;
    /**
     * 传真
     */
    @ApiModelProperty(value = "传真")
    private String fax;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;
}
