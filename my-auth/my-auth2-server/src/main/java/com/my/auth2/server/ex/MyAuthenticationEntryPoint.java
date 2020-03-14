package com.my.auth2.server.ex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.my.auth2.server.utils.WebUtil;

import lombok.extern.slf4j.Slf4j;

/**
 *   端点 异常自定义处理，比如访问某个路径必须要授权访问，测试有效
 * @author minkeWei
 *
 */
@Slf4j
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.error("异常：AuthenticationException====>",authException);
		Map<String, Object> codemap=new HashMap<String, Object>();
		codemap.put("code", "3000");
		codemap.put("msg", authException.getMessage());
		//codemap.put("error", authException);
		
		//response.setStatus(200);//都成功
		WebUtil.writeJson(response, codemap);
	}

}
