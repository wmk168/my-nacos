package com.my.auth2.server.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.my.auth2.server.service.MyUserDetailsService;
import com.my.auth2.server.utils.WebUtil;
import com.my.nacos.base.vo.ResultResVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义页面登录拦截器,这个将会拦截所有访问资源路径，包过css，js等
 * 测试有效
 * @author minkeWei
 *
 */
@Slf4j
public class MyLoginAuthenticationProcessingFilter extends GenericFilterBean implements ApplicationContextAware{
	
	AntPathRequestMatcher loginAntPathRequestMatcher=new AntPathRequestMatcher("/login", "POST");
	
	@Autowired
	private MyUserDetailsService myUserDetailService;
	
	ApplicationContext applicationContext;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		log.info("自定义拦截器：uri==>{}",request.getRequestURI());
		
		if(loginAntPathRequestMatcher.matches(request)) {
			//MyUserDetailService myUserDetailService=applicationContext.getBean(MyUserDetailService.class);
			log.info("测试获取name={}",myUserDetailService.getName());
			
			String username = request.getParameter("username");
			if(StringUtils.isEmpty(username)) {
				//不能用异常抛出，不然就回到上层异常控制，出现401错误
				//throw new SessionAuthenticationException("用户名不能为空！");
				WebUtil.writeJson(response, ResultResVo.validError("用户名不能为空！"));
				return ;
			}
			String password = request.getParameter("password");
			if(StringUtils.isEmpty(password)) {
				WebUtil.writeJson(response, ResultResVo.validError("密码不能为空！"));
				return ;
			}
		}
		chain.doFilter(req, res);
	}

	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

}
