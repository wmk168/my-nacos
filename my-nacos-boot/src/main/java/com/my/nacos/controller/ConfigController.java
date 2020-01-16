package com.my.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.nacos.api.config.annotation.NacosValue;

@RestController
@RequestMapping("config")
public class ConfigController {
	
	//autoRefreshed = true 必须加上这个后才能自动刷新
    @NacosValue(value = "${test.refresh.name:'小蓝'}",autoRefreshed = true)
    private String testRrefreshName;
    
    //不支持
    @Value("${test.refresh.name:'小蓝2'}")
    private String testRrefreshName2;

    @RequestMapping(value = "/get")
    public String get() {
        return testRrefreshName2+","+testRrefreshName;
    }
}