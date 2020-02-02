package com.my.nacos.order.api;

import org.springframework.cloud.openfeign.FeignClient;

import com.my.nacos.base.api.BaseControllerApi;
import com.my.nacos.order.po.OrderPo;

@FeignClient(value ="order-provider",path = "order")
public interface OrderApi extends BaseControllerApi<OrderPo>{

}