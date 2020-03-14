package com.my.auth2.server.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.my.auth2.server.service.MyUserDetailsService;
import com.my.auth2.server.utils.WebUtil;
import com.my.nacos.base.vo.ResultResVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 重新 UsernamePasswordAuthenticationFilter 账号密码拦截器
 * 
 * 但是无法注入对象
 * 
 * @author minkeWei
 *
 */
@Slf4j
public class MyLoginUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements ApplicationContextAware{
	
	ApplicationContext applicationContext;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		//无法注入，可以使用静态工具类获取或者类注入方式
		MyUserDetailsService myUserDetailService=applicationContext.getBean(MyUserDetailsService.class);
		log.info("UsernamePassword测试获取name={}",myUserDetailService.getName());
		
		String username = obtainUsername(request);
		if(StringUtils.isEmpty(username)) {
			//不能用异常抛出，不然就回到上层异常控制，出现401错误
			//throw new SessionAuthenticationException("用户名不能为空！");
			if(WebUtil.isAjaxRequest(request)) {
			WebUtil.writeJson(response, ResultResVo.validError("用户名不能为空！"));
			return null;
			}
		}
		String password = obtainPassword(request);
		if(StringUtils.isEmpty(password)) {
			if(WebUtil.isAjaxRequest(request)) {
			WebUtil.writeJson(response, ResultResVo.validError("密码不能为空！"));
			return null;
			}
		}
		Authentication authentication=super.attemptAuthentication(request, response);
		return authentication;
	}
	
	@Override
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

}
