package com.my.seata.client.scloud.web.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.nacos.base.execution.ServiceException;
import com.my.nacos.base.vo.ResultResVo;
import com.my.seata.order.api.OrderApi;
import com.my.seata.order.mobel.po.OrderPo;
import com.my.seata.user.api.UserApi;
import com.my.seata.user.mobel.po.UserPo;

import io.seata.spring.annotation.GlobalTransactional;

@RestController
@RequestMapping("seata/scloud")
public class SeataClientScloudController {
	
	@Autowired
	private UserApi userApi;
	@Autowired
	private OrderApi orderApi;
	
	@GlobalTransactional(timeoutMills = 5000000)
	@RequestMapping("tran/test")
	public String tranTest() {
		UserPo userPo=new UserPo();
		userPo.setUserName("seata小明");
		userPo.setAge(133);
		ResultResVo<UserPo> userResVo=userApi.addRetKey(userPo);
		
		System.out.println(1/0);
		/*
		OrderPo orderPo=new OrderPo();
		orderPo.setAmount(new BigDecimal("3333"));
		orderPo.setOrderNo("11111");
		orderPo.setUserId(userResVo.getData().getId());
		ResultResVo<Integer> orderResultResVo=orderApi.add(orderPo);
		if(!orderResultResVo.isSuccess()) {
			throw new ServiceException(orderResultResVo);
		}
		*/
		return "ok";
	}
	
}
