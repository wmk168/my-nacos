package com.my.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

/**
 */
@SpringBootApplication
//必须要@NacosPropertySource 才行
@NacosPropertySource(dataId = "nacos-boot", autoRefreshed = true)
public class NacosBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(NacosBootApplication.class, args);
	}
}
