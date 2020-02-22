package com.my.rocketmq.sboot.template;

import org.apache.rocketmq.common.message.MessageBatch;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyTemplateController {
	@Autowired
    private RocketMQTemplate rocketMQTemplate;
    
	@RequestMapping("/template/send")
	public void send() {
		//发送自定对象，测试成功，配置文件不用配置group,配置了就错误
        rocketMQTemplate.convertAndSend("my-Template-Topic","template Helloe");
        
        //MessageBatch msgBatch = //MessageBatch.generateFromList(msgs);
		/*
		 * MessageBuilderFactory messageBuilderFactory = new
		 * DefaultMessageBuilderFactory(); messageBuilderFactory. MessagePostProcessor
		 * postProcessor=new rocketMQTemplate.convertAndSend(payload, postProcessor);
		 */
	}
}
