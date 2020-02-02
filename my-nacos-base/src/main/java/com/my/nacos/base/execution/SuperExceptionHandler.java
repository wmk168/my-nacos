package com.my.nacos.base.execution;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.my.nacos.base.vo.ResultResVo;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class SuperExceptionHandler {
	
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public ResultResVo<?> exceptionHandler(Exception e){
		log.error("服务异常",e);
		return ResultResVo.fail(null).setError(e.getMessage());
	}
	
	@ExceptionHandler(value=ServiceException.class)
	public ResultResVo<?> exceptionHandler(ServiceException e){
		log.error("业务服务异常",e);	
		return ResultResVo.fail(null).setError(e.getMessage());
	}
}
