package com.t4cloud.t.user.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 用户第三方登录数据 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-07
 */
@Data
@TableName("sys_user_third")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysUserThird对象" , description = "用户第三方登录数据")
public class SysUserThird extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 用户外部ID
     */
    @Excel(name = "用户外部ID" , width = 15.0, orderNum = "0")
    @ApiModelProperty(value = "用户外部ID")
    private String outId;

    /**
     * 用户内部ID
     */
    @Excel(name = "用户内部ID" , width = 15.0, orderNum = "1")
    @ApiModelProperty(value = "用户内部ID")
    private String inId;

    /**
     * 用户外部token(密码)
     */
    @Excel(name = "用户外部token(密码)" , width = 32.5, orderNum = "4")
    @ApiModelProperty(value = "用户外部token(密码)")
    private String accessToken;

    /**
     * 用户头像
     */
    @Excel(name = "用户头像" , width = 10.0, orderNum = "5")
    @ApiModelProperty(value = "用户头像")
    private String img;

    /**
     * 用户手机
     */
    @Excel(name = "用户手机" , width = 10.0, orderNum = "6")
    @ApiModelProperty(value = "用户手机")
    private String phone;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称" , width = 10.0, orderNum = "7")
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别" , width = 10.0, orderNum = "7")
    @ApiModelProperty(value = "用户性别")
    private String gender;

    /**
     * 绑定邮箱
     */
    @Excel(name = "绑定邮箱" , width = 10.0, orderNum = "8")
    @ApiModelProperty(value = "绑定邮箱")
    private String email;

    /**
     * 用户平台种类 WX|QQ|WEB
     */
    @Excel(name = "用户平台种类 WX|QQ|WEB" , width = 40.0, orderNum = "9")
    @ApiModelProperty(value = "用户平台种类 WX|QQ|WEB")
    @NotNull(message = "用户平台种类 WX|QQ|WEB不允许为空")
    private String loginType;

    /**
     * 过期时间
     */
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "过期时间" , width = 10.0, orderNum = "10" , format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "过期时间")
    private Date express;

    /**
     * 删除状态(0-正常,1-已删除)
     */
    @Excel(name = "删除状态(0-正常,1-已删除)" , width = 40.0, orderNum = "11")
    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)")
    @NotNull(message = "删除状态(0-正常,1-已删除)不允许为空")
    private Boolean status;

    /**
     * 备注信息)
     */
    @Excel(name = "备注信息)" , width = 12.5, orderNum = "12")
    @ApiModelProperty(value = "备注信息)")
    private String reamrk;


}
