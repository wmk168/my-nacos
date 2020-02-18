package com.my.seata.client.scloud.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.seata.config.FileConfiguration;
import io.seata.core.rpc.netty.NettyPoolableFactory;
import io.seata.discovery.registry.nacos.NacosRegistryProvider;
import io.seata.spring.boot.autoconfigure.SeataAutoConfiguration;
import io.seata.spring.boot.autoconfigure.StarterConstants;
//扫描包要到跟目录，否则会报错，报xid全局事务没有设置,配置全局com,这样就可以解决了
//流程：
/*
 * 1、file文件模式：下载服务端，启动，客户端引入file.conf,register.conf，
 *    提供基础服务的数据库需要导入undo_log表，如user-web服务，如果很多个数据库，这样搞很麻烦的，不利于拓展，还需要研究看看
 * 
 * 2、nacos配置中心集成：
 * 	 客户端：bootstrap配置文件修改，注意事项
 *  调用事务出现诡异问题：调用者先启动，提供者后启动，然后调用的时候是通的，但是出现xid null异常，再次调用就正常了，现在还无法解决
 *  
 *  整体说明：seata整体架构还是乱，配置说明不清楚，版本不稳定，问题多，生产环境还是不要用了
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages = "com.my")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.my.seata")
public class MySeataClientScloudApplication {
	public static void main(String[] args) {

		SpringApplication.run(MySeataClientScloudApplication.class, args);
		
		//相关开源代码类
		/*
		FileConfiguration configuration;
		StarterConstants starterConstants;
		//NettyClientChannelManager a;
		NacosRegistryProvider nacosRegistryProvider;
		SeataAutoConfiguration autoConfiguration;
		NettyPoolableFactory nettyPoolableFactory;
		*/
	}
}
