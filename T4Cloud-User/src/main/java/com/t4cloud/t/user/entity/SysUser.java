package com.t4cloud.t.user.entity;

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
 * 用户表 实体类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-08
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "User对象", description = "用户表")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "生日")
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
    @Dict(code = "common_status")
    @ApiModelProperty(value = "状态(0-冻结，1-正常,)")
    private Integer status;


}
