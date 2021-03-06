package com.my.nacos.base.fallback;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.nacos.base.vo.ResultResVo;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import lombok.extern.slf4j.Slf4j;

//熔断异常回调统一处理
@ControllerAdvice
@Slf4j
public class FallbackControllerAdvice {
	 /**
     * 熔断异常捕捉处理，链接超时，服务不可用
     */
    @ResponseBody
    @ExceptionHandler(value = HystrixRuntimeException.class)
    public ResultResVo<?> hystrixRuntimeException(HystrixRuntimeException e) {
    	log.info("熔断异常===>{}",e.getMessage(),e);
        return ResultResVo.fallbackFail();
    }
}
