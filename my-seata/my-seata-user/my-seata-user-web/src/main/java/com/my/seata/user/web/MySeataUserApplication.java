package com.my.seata.user.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//提供者无法，直接如果seata服务端都是同一个端口，不用改变，直接引入包即可

@SpringBootApplication(scanBasePackages = {"com.my"})
@EnableDiscoveryClient
//@EnableFeignClients(basePackages = "com.my.seata")
@MapperScan(basePackages = "com.my.seata.*.dao")
public class MySeataUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(MySeataUserApplication.class, args);
	}
}
