package com.my.swagger.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Configuration
//@EnableSwagger2
//使用升级版本 swagger-bootstrap-ui
//@EnableKnife4j
//@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
	
	@Bean
	public Docket createRestApi() {
		Docket docket=new Docket(DocumentationType.SWAGGER_2);
		docket.groupName("后台管理接口");
		docket.pathMapping("/")
		// 是否开启,通过这个配置来区分正式环境
		.enable(true)
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
	
	@Bean
    public Docket createWxRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("微信管理接口")
                .apiInfo(apiWxInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.my.swagger.web.controller"))//api接口包扫描路径
                .paths(PathSelectors.regex(".*/weixin/.*"))//可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build();
    }
    private ApiInfo apiWxInfo() {
        return new ApiInfoBuilder()
                .title("微信管理接口")//设置文档的标题
                .description("微信开发接口实现的文档")//设置文档的描述->1.Overview
                .version("1.0.0")//设置文档的版本信息-> 1.1 Version information
                .build();
    }

}
