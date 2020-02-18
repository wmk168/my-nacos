package com.my.seata.order.mobel.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "t_order")//指定表名
public class OrderPo {
	Long id;//主键
	@TableField(value = "orderNo",exist = true)
	String orderNo;//订单编号
	BigDecimal amount;//订单金额
	@TableField(value = "userId",exist = true)
	Long userId;//用户ID
}