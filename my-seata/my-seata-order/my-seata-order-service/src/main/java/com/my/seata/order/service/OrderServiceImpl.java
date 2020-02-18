package com.my.seata.order.service;

import org.springframework.stereotype.Service;

import com.my.nacos.base.service.BaseServiceImpl;
import com.my.seata.order.mobel.po.OrderPo;

@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderPo> implements OrderService{

}
