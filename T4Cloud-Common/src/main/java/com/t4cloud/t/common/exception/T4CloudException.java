package com.t4cloud.t.common.exception;

/**
 * 项目自定义异常
 *
 * --------------------
 * @author TeaR
 * @date 2020/1/15 16:00
 */
public class T4CloudException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public T4CloudException(String message){
		super(message);
	}
	
	public T4CloudException(Throwable cause)
	{
		super(cause);
	}
	
	public T4CloudException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
