package com.t4cloud.t.common.exception;

import com.t4cloud.t.common.entity.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器
 *
 * --------------------
 * @author TeaR
 * @date 2020/1/15 16:00
 */
@RestControllerAdvice
@Slf4j
public class T4CloudExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(T4CloudException.class)
	public Result<?> handleRRException(T4CloudException e){
		log.error(e.getMessage(), e);
		return Result.error(e.getMessage());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public Result<?> handlerNoFoundException(Exception e) {
		log.error(e.getMessage(), e);
		return Result.error(404, "路径不存在，请检查路径是否正确");
	}

//	@ExceptionHandler(DuplicateKeyException.class)
//	public Result<?> handleDuplicateKeyException(DuplicateKeyException e){
//		log.error(e.getMessage(), e);
//		return Result.error("数据库中已存在该记录");
//	}
//
//	@ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
//	public Result<?> handleAuthorizationException(AuthorizationException e){
//		log.error(e.getMessage(), e);
//		return Result.noauth("没有权限，请联系管理员授权");
//	}

	@ExceptionHandler(Exception.class)
	public Result<?> handleException(Exception e){
		log.error(e.getMessage(), e);
		return Result.error("操作失败，"+e.getMessage());
	}
	

	 /** 
	  * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException 
	  */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
    	log.error(e.getMessage(), e);
        return Result.error("文件大小超出指定的大小限制, 请压缩或降低文件质量! ");
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public Result<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
//    	log.error(e.getMessage(), e);
//        return Result.error("字段太长,超出数据库字段的长度");
//    }
//
//    @ExceptionHandler(PoolException.class)
//    public Result<?> handlePoolException(PoolException e) {
//    	log.error(e.getMessage(), e);
//        return Result.error("Redis 连接异常!");
//    }

}
