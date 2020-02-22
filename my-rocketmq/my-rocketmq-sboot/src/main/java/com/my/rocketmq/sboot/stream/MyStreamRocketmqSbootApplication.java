package com.my.rocketmq.sboot.stream;

import org.springframework.cloud.stream.config.BindingServiceProperties;

import com.alibaba.cloud.stream.binder.rocketmq.config.RocketMQBinderAutoConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.config.BindingProperties;

/**
 * 总结：
 * steam模式：
 * 	1、发送者模式，不支持排序，批量发送，其他功能以及消费端都支持
 * 
 * Template模式：
 * 	1、发送端：不支持批量发送，其他功能都支持
 * 
  *   能单纯使用api最好
 * 
 * @author minkeWei
 *
 */
@EnableBinding(MySource.class)
@SpringBootApplication
public class MyStreamRocketmqSbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyStreamRocketmqSbootApplication.class, args);
		
		
		BindingProperties bindingProperties;
		RocketMQBinderAutoConfiguration rocketMQBinderAutoConfiguration;
		com.alibaba.cloud.stream.binder.rocketmq.RocketMQMessageChannelBinder a;
	}
	
}
