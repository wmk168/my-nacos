/*
 * 文 件 名 : EventTest.java
 * 版    权 : SHENZHEN HJM TECHNOLOGY CO.,LTD.Copyright 2019-2020.All rights reserved
 * 描    述 : <描述>
 * 修 改 人 : weiminke
 * 修改时间 : 2020年4月14日
 * 修改内容 : <修改内容>
 */
package com.my.simple.web;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.test.context.junit4.SpringRunner;

import com.my.simple.web.vo.UserVo;

/**
 * 一句话功能简介<br>
 * 功能详细描述
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleWebApplication.class)
public class EventTest {
	
	@Autowired
	private ApplicationEventPublisher applicationContext;
	
	@Test
	public void regUser() {
		UserVo event=new UserVo();
		event.setUserid(434234l);
		applicationContext.publishEvent(event);
	}
}
