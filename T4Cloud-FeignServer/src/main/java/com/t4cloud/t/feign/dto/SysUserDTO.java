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
 * 用户表 DTO类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUserDTO对象" , description = "用户表")
public class SysUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 公司ID（租户ID）
     */
    @ApiModelProperty(value = "公司ID（租户ID）")
    protected String tenantId;
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
     * 登录账号
     */
    @ApiModelProperty(value = "登录账号")
    private String username;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realname;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * md5密码盐
     */
    @ApiModelProperty(value = "md5密码盐")
    private String salt;
    /**
     * 用户角色
     */
    @ApiModelProperty(value = "用户角色")
    private String roles;
    /**
     * 工号，唯一键
     */
    @ApiModelProperty(value = "工号，唯一键")
    private String workNo;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    @ApiModelProperty(value = "性别(0-默认未知,1-男,2-女)")
    private Integer gender;
    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String email;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;
    /**
     * 职务，关联职务表
     */
    @ApiModelProperty(value = "职务，关联职务表")
    private String post;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idCard;
    /**
     * 住址
     */
    @ApiModelProperty(value = "住址")
    private String address;
    /**
     * 性别(1-正常,2-冻结)
     */
    @ApiModelProperty(value = "性别(1-正常,2-冻结)")
    private Integer status;
}
