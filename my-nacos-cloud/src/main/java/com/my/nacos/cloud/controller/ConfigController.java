package com.my.nacos.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.nacos.cloud.config.UserConfig;

@RestController
@RequestMapping("/cloud/config")
@RefreshScope//只能放在刷新类上面才行
public class ConfigController {

    @Value("${test.refresh.name:'小蓝'}")
    private String testRrefreshName;
    
    @Autowired
    private UserConfig userConfig;

    /**
     * http://localhost:8080/config/get
     */
    @RequestMapping("/get")
    public String get() {
    	System.out.println(testRrefreshName);
        return testRrefreshName+","+userConfig.getName();
    }
}