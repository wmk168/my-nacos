package com.my.nacos.base.execution;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.nacos.base.vo.ResultResVo;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class SuperExceptionHandler {
	
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Object exceptionHandler(Exception e){
		log.info("服务异常",e);
		return ResultResVo.fail(null).setError(e.getMessage());
	}
}
