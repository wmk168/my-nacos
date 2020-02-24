package com.my.nacos.geteway.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.factory.PrefixPathGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.RoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * 1、spring-cloud-getway不能与spring-boot-starter-web的springmvc兼容使用，有点坑爹
 * 2、不能引入feign客户端，这个也是我理解出现错误问题
 * 3、getway集成nacos后，配置属性不能配置错误，配置错误无法动态更新
 * 
 * 
 *  如果gateway前面有nginx代理，或者转发的后端服务前面也有nginx，需要在每个nginx中添加websocket协议配置
 *  https://yq.aliyun.com/articles/695279
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class MyGetwayApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(MyGetwayApplication.class, args);
        
		/*
		 * reactor.netty.http.client.HttpClient client;
		 * 
		 * org.springframework.cloud.gateway.config.GatewayAutoConfiguration a;
		 * PrefixPathGatewayFilterFactory factory; ServerProperties properties;
		 * RedisAutoConfiguration autoConfiguration;
		 * 
		 * RoutePredicateFactory<?> factory2;
		 */
    }
    
    /**
     * @Title: fallback
     * @Description: 一个简单的降级页面
     * @return
     */
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        // Mono是一个Reactive stream，对外输出一个“fallback”字符串。
        return Mono.just("fallback");
    }
    
    //代码设置路由
    //@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
                // 配制一个路由,把 http://网关地址:网关端口/demo/ 下的请求路由到 demo-service 微服务中
        .route(p -> p
                    .path("/api5/user/**") 
                    .filters(f -> f
                        .hystrix(config -> config   // 对path()指定的请求使用熔断器
                                    .setName("mycmd")   // 熔断器的名字
                                    .setFallbackUri("forward:/fallback"))// 熔断到 /fallback, 就是上面配制的那个 
                        //.prefixPath("/api3/**")加入这个前缀，不是访问getway项目的前缀，而是转发的时候加入前缀,如：访问/user,实际访问/api3/user/
                        .stripPrefix(1)//如果要加入前缀后再转发的去掉第一个路径/api5
                    	)  
                    .uri("lb://user-provider"))  // 将请求路由到指定目标, lb开头是注册中心中的服务, http/https 开头你懂的
        .build();
    }

}
