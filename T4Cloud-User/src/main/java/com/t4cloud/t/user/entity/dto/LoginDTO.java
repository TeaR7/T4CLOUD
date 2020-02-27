package com.t4cloud.t.user.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * LoginDTO
 * <p>
 * 登录请求用的传递类
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @date 2020/2/10 16:00
 */
@Data
@ApiModel(value = "登录对象", description = "各种登录接口可能需要传递的参数")
public class LoginDTO {


    @NotBlank(message = "登录账号不允许为空！")
    @ApiModelProperty(value = "账号")
    private String username;
    @NotBlank(message = "密码不允许为空！")
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "验证码")
    private String code;
    @ApiModelProperty(value = "验证码key")
    private String codeKey;

}
