package com.my.rocketmq.sboot.stream;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * 所谓的边接收边发送是指接收到消息经过处理后可以产生新的消息，然后允许通过配置指定新的消息的发送目的地。
 * 比如下面的代码就定义了从名为input的Binding接收消息，经过处理（加了前缀receiveAndSend:）后再返回，
 * 然后经过方法上的@SendTo指定返回的内容将发送到名为output的Binding
————————————————

原文链接：https://blog.csdn.net/elim168/article/details/101082105

 * @author minkeWei
 *
 */
@Component
public class ReceiveAndSend {
	
	//接收后又转发,测试通过
	//@StreamListener(MySource.input)
	//@SendTo(MySource.output2)
	public String receiveAndSend(String message) {
		System.out.println("接收消息="+message+",进行转发");
	    return "receiveAndSend:" + message;
	}
	
	@StreamListener(MySource.input2)
    public void consumeUser(String msg) {
        System.out.println("从Binding222222-{}收到的消息-{}"+msg);
        //如果抛出异常，那么系统默认重置3次，3次后如果还有异常，就会进入异常接收消息代码
        //throw new RuntimeException("xxxxxxx接收处理消息异常测试");
    }
}
