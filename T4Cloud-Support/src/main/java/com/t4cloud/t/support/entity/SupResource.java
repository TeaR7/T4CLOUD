package com.t4cloud.t.support.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t4cloud.t.base.annotation.Dict;
import com.t4cloud.t.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 资源管理表 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-02-24
 */
@Data
@TableName("sup_resource")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SupResource对象" , description = "资源管理表")
public class SupResource extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 资源名称 4
     */
    @Excel(name = "资源名称" , width = 10.0, orderNum = "0")
    @ApiModelProperty(value = "资源名称")
    private String name;
    /**
     * 相对路径 4
     */
    @Excel(name = "相对路径" , width = 10.0, orderNum = "1")
    @ApiModelProperty(value = "相对路径")
    private String path;

    /**
     * 相对路径 4
     */
    @Excel(name = "桶" , width = 10.0, orderNum = "1")
    @ApiModelProperty(value = "桶")
    private String bucket;


    /**
     * 完整资源路径 6
     */
    @Excel(name = "完整资源路径" , width = 15.0, orderNum = "2")
    @ApiModelProperty(value = "完整资源路径")
    private String url;
    /**
     * 资源类型 4
     */
    @Excel(name = "资源类型" , width = 10.0, orderNum = "3")
    @ApiModelProperty(value = "资源类型")
    private String mimeType;
    /**
     * 权限策略（1-公开，2-私有） 15
     */
    @Excel(name = "权限策略（1-公开，2-站内私有，3-个人私有）" , width = 37.5, orderNum = "4" , dict = "policy_type")
    @ApiModelProperty(value = "权限策略（1-公开，2-站内私有，3-个人私有）")
    @Dict(code = "policy_type")
    private Integer policy;
    /**
     * 失效时间 4
     */
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "失效时间" , width = 10.0, orderNum = "5" , format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "失效时间")
    private Date expireTime;
    /**
     * 总访问次数 5
     */
    @Excel(name = "总访问次数" , width = 12.5, orderNum = "6")
    @ApiModelProperty(value = "总访问次数")
    private Integer count;


}
