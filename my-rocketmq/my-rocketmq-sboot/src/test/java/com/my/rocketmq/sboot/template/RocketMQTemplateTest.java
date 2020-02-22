package com.my.rocketmq.sboot.template;

import java.util.HashMap;
import java.util.Map;

import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MyTemplateRocketmqSbootApplication.class)
public class RocketMQTemplateTest {
	
	@Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    @Test
    public void sendByRocketMQTemplate(){
    	//实体类User
        //User user=new User(999L,"testUser");
    	HashMap<String, Object> map=new HashMap<String, Object>();
    	map.put("name", "小明");
        //发送自定对象，测试成功，配置文件不用配置group,配置了就错误
        rocketMQTemplate.convertAndSend("my-Template-Topic","template Helloe");
        
       
    }
    

    @Test
    public void sendByRocketMQTemplatedOrder(){
    	for (int i = 1; i < 200; i++) {
    		String payload = "template Helloe"+i;
            Map<String, Object> headers = new HashMap<>();
            headers.put(MessageConst.PROPERTY_TAGS, "testTag");
            MessageHeaders messageHeaders = new MessageHeaders(headers);
            Message<String> message = MessageBuilder.createMessage(payload, messageHeaders);
    		 rocketMQTemplate.syncSendOrderly("test-topic",message,i+"");
		}
     
    }
    
}
