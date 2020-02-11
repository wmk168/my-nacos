package com.my.nacos.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.alibaba.nacos.client.naming.net.NamingProxy;

//Sentinel测试
//java -jar -Dsentinel.dashboard.auth.username=admin -Dsentinel.dashboard.auth.password=123456 sentinel-dashboard-1.6.3.jar
//如果；类目不在本类的下面，要指定基础类包才可以
@SpringBootApplication(scanBasePackages = "com.my.nacos",exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.my.nacos")
public class MySentinelApplication {
	
	public static void main(String[] args) {
		//为了处理同一个appName相同情况下，sentinel控制台统计集群流量错误
		System.setProperty("csp.sentinel.log.use.pid", "true");//等价于-Dcsp.sentinel.log.use.pid
		
		SpringApplication.run(MySentinelApplication.class, args);
	}
}
