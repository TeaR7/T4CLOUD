package com.t4cloud.t.feign.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户第三方登录数据 DTO类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-07
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUserThirdDTO对象" , description = "用户第三方登录数据")
public class SysUserThirdDTO implements Serializable {

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
     * 用户外部ID
     */
    @ApiModelProperty(value = "用户外部ID")
    private String outId;
    /**
     * 用户内部ID
     */
    @ApiModelProperty(value = "用户内部ID")
    private String inId;
    /**
     * 用户外部token(密码)
     */
    @ApiModelProperty(value = "用户外部token(密码)")
    private String accessToken;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String img;
    /**
     * 用户手机
     */
    @ApiModelProperty(value = "用户手机")
    private String phone;
    /**
     * 用户昵称
     */
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
    @ApiModelProperty(value = "绑定邮箱")
    private String email;
    /**
     * 用户平台种类 WX|QQ|WEB
     */
    @ApiModelProperty(value = "用户平台种类 WX|QQ|WEB")
    private Integer loginType;
    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间")
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date express;
    /**
     * 删除状态(0-正常,1-已删除)
     */
    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)")
    private Boolean status;
    /**
     * 备注信息)
     */
    @ApiModelProperty(value = "备注信息)")
    private String reamrk;
}
