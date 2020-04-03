package com.t4cloud.t.support.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.t4cloud.t.base.annotation.Dict;
import com.t4cloud.t.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统日志表 实体类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-27
 */
@Data
@TableName("sys_log")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysLog对象", description = "系统日志表")
public class SysLog extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 日志类型（1-管理员操作，2-登录日志，3-用户操作，4-定时任务，5-其他日志）
     */
    @Excel(name = "日志类型", width = 102.5, orderNum = "0", dict = "log_type")
    @ApiModelProperty(value = "日志类型（1-管理员操作，2-登录日志，3-用户操作，4-定时任务，5-其他日志）")
    @Dict(code = "log_type")
    private Integer logType;

    /**
     * 日志内容
     */
    @Excel(name = "日志内容", width = 10.0, orderNum = "1")
    @ApiModelProperty(value = "日志内容")
    private String logContent;

    /**
     * 操作类型(1-增，2-删，3-改，4-查)
     */
    @Excel(name = "操作类型", width = 52.5, orderNum = "2", dict = "operate_type")
    @ApiModelProperty(value = "操作类型(1-增，2-删，3-改，4-查)")
    @Dict(code = "operate_type")
    private Integer operateType;

    /**
     * 操作结果记录
     */
    @Excel(name = "操作结果记录", width = 15.0, orderNum = "3")
    @ApiModelProperty(value = "操作结果记录")
    private String result;

    /**
     * 是否异常（0-异常，1-正常）
     */
    @Excel(name = "是否异常（0-异常，1-正常）", width = 37.5, orderNum = "4", dict = "common_valid")
    @ApiModelProperty(value = "是否异常（0-异常，1-正常）")
    @Dict(code = "common_valid")
    private Integer resultType;

    /**
     * 操作用户账号
     */
    @Excel(name = "操作用户账号", width = 15.0, orderNum = "5")
    @ApiModelProperty(value = "操作用户账号")
    private String userId;

    /**
     * 操作用户名称
     */
    @Excel(name = "操作用户名称", width = 15.0, orderNum = "6")
    @ApiModelProperty(value = "操作用户名称")
    private String username;

    /**
     * IP
     */
    @Excel(name = "IP", width = 5.0, orderNum = "7")
    @ApiModelProperty(value = "IP")
    private String ip;

    /**
     * 请求java方法
     */
    @Excel(name = "请求java方法", width = 20.0, orderNum = "8")
    @ApiModelProperty(value = "请求java方法")
    private String method;

    /**
     * 请求路径
     */
    @Excel(name = "请求路径", width = 10.0, orderNum = "9")
    @ApiModelProperty(value = "请求路径")
    private String requestUrl;

    /**
     * 请求参数
     */
    @Excel(name = "请求参数", width = 10.0, orderNum = "10")
    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    /**
     * 请求类型
     */
    @Excel(name = "请求类型", width = 10.0, orderNum = "11")
    @ApiModelProperty(value = "请求类型")
    private String requestType;

    /**
     * 耗时
     */
    @Excel(name = "耗时", width = 5.0, orderNum = "12")
    @ApiModelProperty(value = "耗时")
    private Long costTime;


}
