package com.my.swagger.zuul.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Component
@Primary
@Import(BeanValidatorPluginsConfiguration.class)
@Slf4j
public class MyZuulSwaggerResourcesProvider implements SwaggerResourcesProvider {
	@Autowired
	private RouteLocator routeLocator;

	/*
	  @Override
	    public List<SwaggerResource> get() {
	        List resources = new ArrayList<>();
	        resources.add(swaggerResource("订单系统", "/order/v2/api-docs", "2.0"));
	        resources.add(swaggerResource("支付系统", "/pay/v2/api-docs", "2.0"));
	        return resources;
	    }
*/
	
	 @Bean
	    public Docket createRestApi() {
		 log.info("=================>createRestApi={}","createRestApi");
	        return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(apiInfo())
	                .select()
	                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
	                .paths(PathSelectors.any())
	                .build()
	                //下面这个设置就是在接口的path前加上project-name
	                .pathMapping("/")
	                ;
	    }

	    private ApiInfo apiInfo() {
	    	 log.info("=================>apiInfo={}","分布式购物系统");
	        return new ApiInfoBuilder()
	                .title("分布式购物系统")
	                .description("购物系统接口文档说明")
	                .termsOfServiceUrl("http://localhost:8081")
	                .contact(new Contact("林塬", "", "765371578@qq.com"))
	                .version("1.0")
	                .build();
	    }
	
	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
        log.info("=================>route={}",routes);
        
        for (Route route:routes) {
        	log.info("=================>route.getFullPath()={}",route.getFullPath());
           // resources.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs")));
        }
        
        //必须要存在路由路径，比如在配置文件里面配置，如/user 或者 /api必须存在
        resources.add(swaggerResource("用户系统", "/api/v2/api-docs"));
        resources.add(swaggerResource("支付系统", "/api/v2/api-docs"));
        
        return resources;
	}
	private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);//显示分组
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0.0");
        return swaggerResource;
    }
	
	 //自定义 serviceId 和路由之间的相互映射,无效
   // @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        return new PatternServiceRouteMapper(
                "(?<project>^.+)-(?<subProject>.+$)",
                "${project}/${subProject}");
    }

}
