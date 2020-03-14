package com.my.auth2.server.auth.provide;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.my.auth2.server.ex.MyShowAuthenticationException;
import com.my.auth2.server.utils.WebUtil;
import com.my.nacos.base.vo.ResultResVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义授权校验支持，因为系统默认报错都是英语，对中文支持不是很好，因此要自定义进行校验
 * 可用
 * @author minkeWei
 *
 */
@Slf4j
public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider{
	
	@Autowired
	private HttpServletResponse response;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//过滤非空，还是在过滤器上处理吧，不然老是报异常，影响代码问题
		String userName = authentication.getName();// 这个获取表单输入中返回的用户名;
		if(StringUtils.isEmpty(userName)) {
			//throw new BadCredentialsException("用户名不能为空!");
			WebUtil.writeJson(response, ResultResVo.validError("用户名不能为空！"));
			return null;
		}
        String password = (String) authentication.getCredentials();// 这个是表单中输入的密码；
        if(StringUtils.isEmpty(password)) {
			throw new BadCredentialsException("密码不能为空!");
		}
		UserDetails userDetails=null;
		try {
	        userDetails=getUserDetailsService().loadUserByUsername(userName);
	        //校验密码是否正确
	        if(!getPasswordEncoder().matches(password, userDetails.getPassword())){
	        	log.info("username={},password error",userName);
	        	//不能用自定义，不然老是出现输出事件异常
	        	throw new MyShowAuthenticationException("密码错误！");
	        }
		} catch (Throwable e) {
			throw e;
		}
        //super.authenticate(authentication);
		return createSuccessAuthentication(userDetails, authentication, userDetails);
		//return new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		// 这里直接改成retrun true;表示是支持这个执行
        return true;
	}
}
