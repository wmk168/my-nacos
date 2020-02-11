package com.my.nacos.sentinel.test;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentinelRuleTest {

	public static void main(String[] args) {
		run();
	}
   
    public static void run() {
        try{
            List<FlowRule> rules = FlowRuleManager.getRules();
            if(rules == null ){
                rules = new ArrayList<>();
            }
            FlowRule flowRule = new FlowRule("/sentinel/test/add");
            flowRule.setCount(0)//控制阀值
            .setControlBehavior(0)//阈值类型
            .setClusterMode(false)//是否集群
            ;
            flowRule.setLimitApp("default");//控制来源
            rules.add(flowRule);
            log.info("FlowRuleRunner loadRules reules:{}  ",rules);
            JSONObject.toJSONString(rules);
            //FlowRuleManager.loadRules(rules);
        }catch (Exception e){
            log.error("FlowRuleRunner 加载异常 :{}",e.getMessage());
        }
    }
}
