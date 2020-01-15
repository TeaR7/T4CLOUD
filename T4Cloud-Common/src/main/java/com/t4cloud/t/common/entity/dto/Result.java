package com.t4cloud.t.common.entity.dto;

import com.t4cloud.t.common.constant.ResultConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用的高复用类
 *
 * <p>
 * 作为通用的返回对象，提供标准格式和快速调用的方法
 * --------------------
 *
 * @author TeaR
 * @date 2020/1/15 13:01
 */
@Data
@ApiModel(value = "通用返回对象", description = "接口返回对象")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    @ApiModelProperty(value = "成功标志")
    private boolean success = true;

    /**
     * 返回处理消息
     */
    @ApiModelProperty(value = "返回处理消息")
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    @ApiModelProperty(value = "返回代码")
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    @ApiModelProperty(value = "返回数据对象")
    private T result;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private long timestamp = System.currentTimeMillis();

    private Result() {

    }


    // ----------------------------------------------- 成功 -----------------------------------------------


    public static <T> Result<T> ok() {
        Result<T> r = new Result<>();
        r.setSuccess(true);
        r.setCode(ResultConstant.OK);
        r.setMessage("成功");
        return r;
    }

    public static <T> Result<T> ok(String msg) {
        Result<T> r = new Result<>();
        r.setSuccess(true);
        r.setCode(ResultConstant.OK);
        r.setMessage(msg);
        return r;
    }

    public static <T> Result<T> ok(String msg, T data) {
        Result<T> r = new Result<>();
        r.setSuccess(true);
        r.setCode(ResultConstant.OK);
        r.setResult(data);
        return r;
    }

    // ----------------------------------------------- 异常 -----------------------------------------------


    public static Result error(String msg) {
        return error(ResultConstant.INTERNAL_SERVER_ERROR, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result<Object>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    // ----------------------------------------------- 授权异常 -----------------------------------------------

    public Result<T> noAuthz(String message) {
        this.message = message;
        this.code = ResultConstant.NO_AUTHZ;
        this.success = false;
        return this;
    }

    public Result<T> noAuthz() {
        this.message = "您的登录已失效！请尝试重新登录。";
        this.code = ResultConstant.NO_AUTHZ;
        this.success = false;
        return this;
    }
}