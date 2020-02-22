/**
 * 
 */
package com.my.rocketmq.sboot.stream;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @author minkeWei
 *
 */
@RocketMQTransactionListener(txProducerGroup = "my-p-test-group")
public class DemoTransactionalListener implements RocketMQLocalTransactionListener {

	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
		System.out.println("==========提交事务消息executeLocalTransaction===========>"+msg);
		//当出现非提交状态后，就会进入checkLocalTransaction方法
		//RocketMQLocalTransactionState.UNKNOWN，过1分钟 出现后需要点延时后才进入checkLocalTransaction
		//RocketMQLocalTransactionState.ROLLBACK回滚后，不会进入checkLocalTransaction
		//RocketMQLocalTransactionState.COMMIT//一提交就进入发送消息
		if(true) {
			//异常后，过个几秒钟就会进入checkLocalTransaction
			throw new RuntimeException("异常了事务");
		}
		return RocketMQLocalTransactionState.UNKNOWN;//一提交就发送消息
	}

	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
		System.out.println("==========提交事务消息checkLocalTransaction===========>"+msg);
		return RocketMQLocalTransactionState.COMMIT;
	}

}
