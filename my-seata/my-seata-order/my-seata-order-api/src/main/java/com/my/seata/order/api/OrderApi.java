package com.my.seata.order.api;

import org.springframework.cloud.openfeign.FeignClient;

import com.my.nacos.base.api.BaseControllerApi;
import com.my.seata.order.mobel.po.OrderPo;

@FeignClient(value ="order-web",path = "order")
public interface OrderApi extends BaseControllerApi<OrderPo>{

}