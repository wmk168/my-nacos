package com.my.nacos.geteway.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

//测试有效，统一前缀
//参考：https://gitee.com/felord/tino-cloud.git
//@Configuration
public class MyGatewayPrefixConfig {
	
	@Value("${spring.cloud.gateway.api-prefix:/v1/api}")
    private String prefix;

    @Bean
    @Order(-1)
    public WebFilter apiPrefixFilter() {
    	WebFilter webFilter=new WebFilter() {
			
			@Override
			public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
				ServerHttpRequest request = exchange.getRequest();

	            String path = request.getURI().getRawPath();
	            if (!path.contains(prefix)) {
	                ServerHttpResponse response = exchange.getResponse();
	                response.setStatusCode(HttpStatus.BAD_GATEWAY);

	                DataBuffer buffer = response
	                        .bufferFactory()
	                        .wrap(HttpStatus.BAD_GATEWAY.getReasonPhrase().getBytes());
	                return response.writeWith(Mono.just(buffer));
	            }
	            String newPath = path.replaceFirst(prefix, "");
	            ServerHttpRequest newRequest = request.mutate().path(newPath).build();

	            return chain.filter(exchange.mutate().request(newRequest).build());
	            
			}
		};
		return webFilter;
		
		//等价于
		/*
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String path = request.getURI().getRawPath();
            if (!path.contains(prefix)) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.BAD_GATEWAY);

                DataBuffer buffer = response
                        .bufferFactory()
                        .wrap(HttpStatus.BAD_GATEWAY.getReasonPhrase().getBytes());
                return response.writeWith(Mono.just(buffer));
            }
            String newPath = path.replaceFirst(prefix, "");
            ServerHttpRequest newRequest = request.mutate().path(newPath).build();

            return chain.filter(exchange.mutate().request(newRequest).build());
        };
        */
    }

}
