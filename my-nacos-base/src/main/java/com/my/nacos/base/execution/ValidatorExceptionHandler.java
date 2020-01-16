package com.my.nacos.base.execution;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.nacos.base.vo.FieldErrorVo;
import com.my.nacos.base.vo.ResultResVo;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ValidatorExceptionHandler {
	
	
	@ExceptionHandler(value=ValidationException.class)
	@ResponseBody
	public Object exceptionHandler(ValidationException e){

		String msg =e.getMessage();
		/*
		if(e instanceof ConstraintViolationException){
			ConstraintViolationException exs = (ConstraintViolationException) e;
 
			Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
			for (ConstraintViolation<?> item : violations) {
				msg = item.getMessage();
	        }
		}
		*/
		return ResultResVo.validError(msg);
	}
	
	/**
	 * 校验参数异常，显示输出具体字段错误列表
	 */
	@ExceptionHandler(value=BindException.class)
	@ResponseBody
	public Object bindException(BindException e){
		List<FieldErrorVo> fieldErrorVos=new ArrayList<FieldErrorVo>();
		try {
			BindingResult bindingResult=e.getBindingResult();
			if(bindingResult.hasErrors()) {
				List<FieldError> fieldErrors=bindingResult.getFieldErrors();
	
				FieldErrorVo fieldErrorVo;
				for (FieldError fieldError:fieldErrors) {
					fieldErrorVo=new FieldErrorVo(fieldError.getField(),fieldError.getDefaultMessage());
					fieldErrorVos.add(fieldErrorVo);
				}
			}
		} catch (Throwable  tx) {
			log.info("转换校验参数异常",tx);
		}
		return ResultResVo.validError(null,fieldErrorVos);
	}
	
	

}
