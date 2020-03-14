package com.my.auth2.server.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.support.RequestContextUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MyLoginController {
	
	@Autowired
	private ConsumerTokenServices consumerTokenServices;
	
	//登录页面
	@RequestMapping("mylogin")
	public String loginUI(HttpServletRequest request) {
		request.getSession().setAttribute("myKey", "小明");
		log.info("获取到错误信息=================>{},request={},header={},request={}",request.getRequestURI(),request.getHeaderNames(),request);
		return "mylogin";
	}
	
	//登录成功页面
	@RequestMapping("login_success")
	public String login_success() {
		return "login_success";
	}
	
	//登录成后获取信息
	@RequestMapping("/login/get/user")
	@ResponseBody
    public Object getUser() {
        //for debug
		Object user=SecurityContextHolder.getContext().getAuthentication();
		log.info("获取User={}",user);
        return user;
    }
	
	 @RequestMapping(value = "/login/out")
	 @ResponseBody
	 public Object revokeToken(String access_token) {
	        Map<String, Object> result = new HashMap<String, Object>();
	        //退出授权sessoin
	        if(StringUtils.isEmpty(access_token)) {
	        	try {
	        	SecurityContextHolder.getContext().setAuthentication(null);
	 	        SecurityContextHolder.clearContext();
	 	       result.put("msg","注销成功");
	        	}catch (Throwable e) {
					log.error("退出登录异常",e);
				}
	        }else {
	        	//存在token进行清除
		        if (consumerTokenServices.revokeToken(access_token)) {
		            result.put("msg","注销成功");
		        } else {
		            result.put("msg","注销失败");
		        }	
	        }
	        return result;
	    }

}
