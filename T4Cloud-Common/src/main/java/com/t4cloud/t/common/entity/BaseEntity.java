package com.t4cloud.t.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类基类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @date 2020/1/15 22:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    protected static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId()
    protected String id;


    /**
     * 状态(1：正常  2：冻结 ）
     */
    protected Integer status;

    /**
     * 删除状态（0，正常，1已删除）
     */
    @TableLogic
    protected Integer flag;

    /**
     * 创建人
     */
    protected String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;

    /**
     * 更新人
     */
    protected String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;


}
