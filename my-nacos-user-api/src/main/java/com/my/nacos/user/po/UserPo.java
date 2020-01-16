package com.my.nacos.user.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "t_user")//指定表名
public class UserPo {
	Long id;//主键
	//若没有开启驼峰命名，或者表中列名不符合驼峰规则，可通过该注解指定数据库表中的列名，exist标明数据表中有没有对应列
	@TableField(value = "userName",exist = true)
	String userName;//如果大小号就自动分割成了user_name
	Integer age;//自动对应
}
