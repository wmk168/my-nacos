package com.my.nacos.user;

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
public class UserProviderApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserProviderApplication.class, args);
	}
	
	/*
	 * @Bean public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
	 * RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
	 * 
	 * List<HttpMessageConverter<?>> converters = adapter.getMessageConverters();
	 * 
	 * MappingJackson2HttpMessageConverter jsonConverter = new
	 * MappingJackson2HttpMessageConverter(); List<MediaType> supportedMediaTypes =
	 * new ArrayList<MediaType>(); MediaType textMedia = new
	 * MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
	 * supportedMediaTypes.add(textMedia); MediaType jsonMedia = new
	 * MediaType(MediaType.APPLICATION_JSON, Charset.forName("UTF-8"));
	 * supportedMediaTypes.add(jsonMedia);jsonConverter.setSupportedMediaTypes(
	 * supportedMediaTypes);
	 * 
	 * converters.add(jsonConverter);
	 * 
	 * 
	 * adapter.setMessageConverters(converters);
	 * 
	 * return adapter; }
	 */
}
