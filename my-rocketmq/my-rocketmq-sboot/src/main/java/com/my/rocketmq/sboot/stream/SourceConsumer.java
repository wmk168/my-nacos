package com.my.rocketmq.sboot.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class SourceConsumer {
	
	@StreamListener(MySource.input)
    public void consumeUser(String msg) {
        System.out.println("从Binding-收到类型消息-"+msg);
        //如果抛出异常，那么系统默认重置3次，3次后如果还有异常，就会进入异常接收消息代码
        //throw new RuntimeException("xxxxxxx接收处理消息异常测试");
    }
	
	//接收异常消息定义
	@ServiceActivator(inputChannel = "test-topic.test-group.errors")
	public void handleConsumeUserError(ErrorMessage message) {
	    System.out.println("<============收到处理失败的消息{}"+message.getPayload());
	}
	
	//接收原始消息
	//@StreamListener(MySource.input)
	public void inputConsumer(org.springframework.messaging.Message<String> message) {
	    String payload = message.getPayload();
	    MessageHeaders headers = message.getHeaders();
	    log.info("从Binding-{}收到信息-{}， headers：{}", MySource.input, payload, headers);
	}

	//reactive方式接收消息时方法参数需要定义为Flux
	//@StreamListener(target = MySource.input,condition = "headers['type']=='order'")
	//@StreamListener(target = MySource.input)
	public void reactiveReceive(//@Input(MySource.input) 
				Flux<String> flux) {
	    flux.subscribe(message -> {
	        log.info("通过reactive方式收到信息: {}", message);
	    });
	}

}
