package com.my.nacos.web.controller;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.nacos.base.vo.ResultResVo;
import com.my.nacos.user.po.UserPo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

//熔断测试,@EnableCircuitBreaker要配置
@RestController
@RequestMapping("hy")
@Slf4j
public class HystrixController {
	
	//只支持在方法体里面，可以特殊处理针对某个方法执行熔断效果
	@HystrixCommand(defaultFallback ="mydefaultFallback")
	@RequestMapping("test")
	public ResultResVo<UserPo> test(@RequestParam("id") Integer id) {
		log.info("请求ID={}",id);
		
		try {
			Thread.sleep(10*10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ResultResVo<UserPo> mydefaultFallback(Throwable e) {
		log.info("@HystrixCommand=========>{},{}",e);
		return ResultResVo.fallbackFail();
	}
	
}
