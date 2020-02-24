package com.my.nacos.geteway.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class GetWayController{
	 /**
     * @Title: mybackUri
     * @Description: 一个简单的降级页面
     * @return
     */
    @RequestMapping("/mybackUri")
    public Mono<String> fallback() {
        // Mono是一个Reactive stream，对外输出一个“fallback”字符串。
        return Mono.just("mybackUri le ");
    }
}
