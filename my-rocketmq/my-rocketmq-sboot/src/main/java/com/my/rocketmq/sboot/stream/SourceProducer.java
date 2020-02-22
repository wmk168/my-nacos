package com.my.rocketmq.sboot.stream;

import java.util.HashMap;
import java.util.Map;

import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SourceProducer {

    @Autowired
    private MySource source;
    
    public void sendMessages(String msg) {
        String payload = msg;
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "testTag");
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        Message<String> message = MessageBuilder.createMessage(payload, messageHeaders);
        this.source.output().send(message);
    }
    
    @ServiceActivator(outputChannel = "test-topic.test-group.errors")
	public void handleConsumeUserError(ErrorMessage message) {
	    System.out.println("==============>发生处理失败的消息{}"+message.getPayload());
	}
    
}