package com.t4cloud.t.mock.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.t4cloud.t.base.entity.BaseEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Mock数据 实体类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-14 
 */
@Data
@TableName("sys_mock")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysMock对象", description = "Mock数据")
public class SysMock extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* MOCK对应的URL
	*/
	@ApiModelProperty(value = "MOCK对应的URL")
	private String url;
	/**
	* MOCK对应的DATA数据
	*/
	@ApiModelProperty(value = "MOCK对应的DATA数据")
	private String data;
	/**
	* MOCK对应的URL的请求方法
	*/
	@ApiModelProperty(value = "MOCK对应的URL的请求方法")
	private String method;


}
