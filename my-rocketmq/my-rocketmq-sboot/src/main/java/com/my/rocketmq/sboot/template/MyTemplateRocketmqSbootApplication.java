package com.my.rocketmq.sboot.template;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class MyTemplateRocketmqSbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyTemplateRocketmqSbootApplication.class, args);
	}
	//topic：和消费者发送的topic相同
	//group：不用和生产者group相同
	//selectorExpression = "*" 可以不设置 tag
	@RocketMQMessageListener(topic = "my-Template-Topic",consumerGroup = "my-listener-group"//selectorExpression = "*"
            ) 			    //tag
	@Component
    class Mylistener implements  RocketMQListener<String>{
        @Override
        public void onMessage(String messageExt) {
            System.out.println("================>"+Thread.currentThread().getName() + " onMessage: " + messageExt);
        }
    }
    
	
	
}
