package com.my.seata.order.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.nacos.base.api.BaseControllerApiImpl;
import com.my.seata.order.api.OrderApi;
import com.my.seata.order.mobel.po.OrderPo;

@RestController
@RequestMapping("order")
public class OrderController extends BaseControllerApiImpl<OrderPo> implements OrderApi
{
	
}
