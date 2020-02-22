package com.my.rocketmq.sboot.stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyStreamController {
	
	@Autowired
    private MySource source;
    int i=1;
    
    @RequestMapping("/tra/send")
    public void sendMessagesByTra(String msg) {
        String payload = msg+"-"+(i++);
        Message<String> message=MessageBuilder
        .withPayload(payload)
        .setHeader(MessageConst.PROPERTY_TAGS, "testTag")
        .setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID().toString())
        //.setHeader("comment", JSON.toJSONString(forObject))
        .build();
        this.source.output().send(message);
    }
    
	@RequestMapping("/s/send")
    public void sendMessages(String msg) {
        String payload = msg+"-"+(i++);
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "testTag");
        headers.put(MessageConst.PROPERTY_KEYS, "my-key");
        headers.put("DELAY", "1");
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        Message<String> message = MessageBuilder.createMessage(payload, messageHeaders);
        this.source.output().send(message);
    }
	
	@RequestMapping("/s/sends")
    public void sendMessagess(String msg) {
		//out stream这种方式无法保证发送有序
		for (int i = 0; i < 20; i++) {
			//String payload = msg+(i++);
			sendMessages(msg);
		}
    }
	
	@Autowired
	RocketMQTemplate rocketMQTemplate;
	
	@RequestMapping("/t/sends")
    public void sendTemplates(String msg) {
		
		//排序必须加上这个选择器，偶尔出现小乱，效果整体还是不错的
		rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
			@Override
			public MessageQueue select(List<MessageQueue> mqs, org.apache.rocketmq.common.message.Message msg, Object arg) {
				Long id = Long.valueOf((String)arg);
                long index = id % mqs.size();
                MessageQueue messageQueue=mqs.get((int)index);
                return messageQueue;
			}
		});
		
		for (long i = 1; i < 100; i++) {
			
			String payload = "template Helloe"+i;
			//tag标签不是在header设置了，而是destination：topic:tags，坑人
	        Message<String>  message = MessageBuilder.withPayload(payload).build(); 
			rocketMQTemplate.syncSendOrderly("test-topic:testTag",message,"0");
			
			 //rocketMQTemplate.send("test-topic",message);
			 
			 //rocketMQTemplate.convertAndSend("test-topic","template Helloe"+i);
			 System.out.println("rocketMQTemplate======>"+i);
		}
    }
	
	
}
