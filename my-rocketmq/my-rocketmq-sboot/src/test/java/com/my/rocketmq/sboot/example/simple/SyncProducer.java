package com.my.rocketmq.sboot.example.simple;

import java.util.ArrayList;
import java.util.List;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
//生产者，链接，测试通过
public class SyncProducer {
    public static void main(String[] args) throws Exception {
    	//sendMsg();
    	//sendMsgByOrder();
    	sendMsgList();
    }
    //无序发送
    public  static void sendMsg() throws Exception{
    	 //Instantiate with a producer group name.可以与消费端不一样，simple-Producer-group会自动创建
        DefaultMQProducer producer = new DefaultMQProducer("simple-Producer-group");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        producer.start();
        for (int i = 1; i < 20; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("test-topicvvvvv" /* Topic */,
                "tag0" /* Tag 可以不设置=============*/,
                (">>>>Hello RocketMQ " +i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
    
    //排序发送,消费端偶尔还是出现乱序，整体还好
    public  static void sendMsgByOrder() throws Exception{
   	 //Instantiate with a producer group name.可以与消费端不一样，simple-Producer-group会自动创建
       DefaultMQProducer producer = new DefaultMQProducer("simple-Producer-group");
       // Specify name server addresses.
       producer.setNamesrvAddr("localhost:9876");
       //Launch the instance.
       producer.start();
       for (int i = 1; i < 10; i++) {
           //Create a message instance, specifying topic, tag and message body.
           Message msg = new Message("test-topicyrt" /* Topic */,
               "testTag" /* Tag 可以不设置,业务区分=============*/,
               ("Hello RocketMQ " +i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
           );
           //Call send message to deliver message to one of brokers.
           SendResult sendResult = producer.send(msg,new MessageQueueSelector() {
			@Override
			public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
				Long id = Long.valueOf((String)arg);
                long index = id % mqs.size();
                System.out.println("==========>index:"+index);
                MessageQueue messageQueue=mqs.get((int)index);
                return messageQueue;
			}
           },i+"");
           System.out.printf("%s%n", sendResult);
       }
       //Shut down once the producer instance is not longer in use.
       producer.shutdown();
   }
    
    //无序 批量发送
    public  static void sendMsgList() throws Exception{
    	 //Instantiate with a producer group name.可以与消费端不一样，simple-Producer-group会自动创建
        DefaultMQProducer producer = new DefaultMQProducer("simple-Producer-group");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        producer.start();
        List<Message> messages=new ArrayList<Message>();
        for (int i = 1; i < 20; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("myo-topic" /* Topic */,
                "tag0" /* Tag 可以不设置=============*/,
                (">>>>Hello RocketMQ " +i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            messages.add(msg);
        }
        //Call send message to deliver message to one of brokers.
        SendResult sendResult = producer.send(messages);
        System.out.printf("%s%n", sendResult);
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
