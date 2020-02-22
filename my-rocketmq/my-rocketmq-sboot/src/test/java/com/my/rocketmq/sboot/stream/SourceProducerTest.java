package com.my.rocketmq.sboot.stream;

import java.util.HashMap;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MyStreamRocketmqSbootApplication.class)
public class SourceProducerTest {

    @Autowired
    private SourceProducer producer;
    
    @Test
    public void test() {
    	try {
	        for (int i=0; i<10; i++) {
	            //this.producer.sendMessages("Message-" + i);
	        }
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    

}
