package com.my.swagger.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket createRestApi() {
		Docket docket=new Docket(DocumentationType.SWAGGER_2);
		docket.pathMapping("/")
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.my.swagger.web.controller"))
		.paths(PathSelectors.any())
		.build().apiInfo(
				new ApiInfoBuilder()
				.title("my-boot-title")
				.description("我的第一个swagger2应用")
				.version("9.0")
				.contact(new Contact("联系我","blog.xx.com","wmk@qq.com"))
		.license("The Apache License")
		.licenseUrl("http://www.xx.com")
		.build());
		
		return docket;
				
	}
}
