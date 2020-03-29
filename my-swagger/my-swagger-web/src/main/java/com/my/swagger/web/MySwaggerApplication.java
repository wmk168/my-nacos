package com.my.swagger.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**swagger api 文档应用
 */
@SpringBootApplication
@EnableEurekaServer//注册自己为一个服务中心
@EnableDiscoveryClient//注册客户端
public class MySwaggerApplication 
{
    public static void main( String[] args )
    {
       SpringApplication.run(MySwaggerApplication.class, args);
    }
}
