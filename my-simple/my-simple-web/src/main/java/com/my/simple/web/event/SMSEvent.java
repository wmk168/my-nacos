/*
 * 文 件 名 : EmailEvent.java
 * 版    权 : SHENZHEN HJM TECHNOLOGY CO.,LTD.Copyright 2019-2020.All rights reserved
 * 描    述 : <描述>
 * 修 改 人 : weiminke
 * 修改时间 : 2020年4月14日
 * 修改内容 : <修改内容>
 */
package com.my.simple.web.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.my.simple.web.vo.UserVo;

/**
 *短信
 * @author  weiminke
 */
@Component
public class SMSEvent {
	
	@EventListener//监听发送
	public void sendRegEmail(UserVo userVo) {
		System.out.println("短信发送短信===》"+userVo.getUserid());
		try {
			throw new RuntimeException("分担分担对方");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
}
