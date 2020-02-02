package com.my.nacos.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.nacos.base.api.BaseControllerApiImpl;
import com.my.nacos.order.api.OrderApi;
import com.my.nacos.order.po.OrderPo;

@RestController
@RequestMapping("order")
public class OrderController extends BaseControllerApiImpl<OrderPo> implements OrderApi{
	
}
