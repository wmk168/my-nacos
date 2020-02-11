package com.my.nacos.sentinel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.my.nacos.base.vo.ResultResVo;
import com.my.nacos.user.api.UserApi;
import com.my.nacos.user.po.UserPo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("sentinel/test")
@Slf4j
public class SentinelTestController {
	@Autowired
	private UserApi userApi;
	
	@RequestMapping("getById")
	public ResultResVo<UserPo> getById(@RequestParam("id") Integer id) {
		log.info("请求ID={}",id);
		//支持自定义返回状态码，中间有问题一直是状态码对象类T类型转换错误
		ResultResVo<UserPo> rt=userApi.getById(id);
		log.info("UserPO={}",rt.getCode());
		return rt;
	}
	
	@RequestMapping("add")
	public ResultResVo<Integer> add(@RequestParam("id") Integer id) {
		log.info("请求ID={}",id);
		return new ResultResVo<Integer>().setData(id);
	}
	
	@RequestMapping("/test-hot")
	@SentinelResource("hot")  // 该注解用于声明是Sentinel需要监控的资源
	public String testHot(@RequestParam(required = false) String a,
	                      @RequestParam(required = false) String b) {
	    return a + " " + b;
	}
}
