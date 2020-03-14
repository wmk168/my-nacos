package com.my.simple.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@Configuration
//@Component
public class MyWebMvcConfigurationSupport extends WebMvcConfigurationSupport{
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println("ResourceHandlerRegistry=====>");
		//测试通过
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/","classpath:/template/");
	}
}
