package com.my.nacos.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

//如果；类目不在本类的下面，要指定基础类包才可以
@SpringBootApplication(scanBasePackages = "com.my.nacos",exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.my.nacos")
//这个熔断配置是单独组件，Feign可以不需要这个，这个是配合注解@HystrixCommand使用，只能在指定某个方法上面
//spring-cloud-starter-netflix-hystrix 要导入该依赖
@EnableCircuitBreaker
//@EnableWebMvc//启动这个会导致static无法访问
public class MyNacosWebApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MyNacosWebApplication.class, args);
	}
}
