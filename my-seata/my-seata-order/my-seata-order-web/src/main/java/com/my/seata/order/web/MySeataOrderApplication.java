package com.my.seata.order.web;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

import io.seata.rm.datasource.DataSourceProxy;

@SpringBootApplication(scanBasePackages = {"com.my"})
@EnableDiscoveryClient
//@EnableFeignClients(basePackages = "com.my.seata")
@MapperScan(basePackages = "com.my.seata.*.dao")
//@EnableWebMvc
//@Configuration
public class MySeataOrderApplication {
	public static void main(String[] args) {

		SpringApplication.run(MySeataOrderApplication.class, args);
	}
	
	/*
	 @Bean
	    @ConfigurationProperties(prefix = "spring.datasource")
	    public DataSource druidDataSource(){
	        DruidDataSource druidDataSource = new DruidDataSource();
	        return druidDataSource;
	    }

	    @Primary
	    @Bean("dataSource")
	    public DataSourceProxy dataSource(DataSource druidDataSource){
	        return new DataSourceProxy(druidDataSource);
	    }
*/
}
