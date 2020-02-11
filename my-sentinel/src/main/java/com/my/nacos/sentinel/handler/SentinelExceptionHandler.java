package com.my.nacos.sentinel.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.my.nacos.base.constants.BaseResultStatus;
import com.my.nacos.base.execution.ServiceException;
import com.my.nacos.base.vo.ResultResVo;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class SentinelExceptionHandler {
	@ExceptionHandler(value=ParamFlowException.class)
	public ResultResVo<?> paramFlowException(ParamFlowException e){
		log.error("Sentine参数流量控制服务异常",e);	
		return ResultResVo.setResult(BaseResultStatus.FLOW_ERROR).setError("控制参数值："+e.getMessage());
	}
}
