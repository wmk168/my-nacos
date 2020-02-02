package com.my.nacos.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages ="com.my.nacos")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.my.nacos")
@MapperScan(basePackages = "com.my.nacos.*.dao")
@Configuration
@EnableWebMvc
public class OrderProvideApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderProvideApplication.class, args);
	}
}
