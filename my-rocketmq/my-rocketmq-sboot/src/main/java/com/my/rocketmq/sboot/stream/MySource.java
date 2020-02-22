package com.my.rocketmq.sboot.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
/**
 * https://blog.csdn.net/elim168/article/details/101082105
 * 定义@Input和@Ouput时如果没有自定Binding的名称，默认获取当前方法的名称作为Binding的名称
 * @author minkeWei
 *
 */
public interface MySource {
	  
	  String output="output";
	  @Output(output) 
	  MessageChannel output(); 
	  
	  String output2="output2";
	  @Output(output2) 
	  MessageChannel output2(); 
	  
	  String input="input";
	  @Input(input) 
	  SubscribableChannel  input(); 
	  
	  String input2="input2";
	  @Input(input2) 
	  SubscribableChannel  input2(); 
}
