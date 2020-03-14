package com.my.auth2.server.ex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.my.auth2.server.utils.WebUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义访问拒绝
 * @author minkeWei
 *
 */
@Slf4j
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.info("异常：AccessDeniedException====>",accessDeniedException);
		Map<String, Object> codemap=new HashMap<String, Object>();
		codemap.put("code", "2000");
		codemap.put("msg", accessDeniedException.getLocalizedMessage());
		codemap.put("error", accessDeniedException);
		
		//response.setStatus(200);//都成功
		WebUtil.writeJson(response, codemap);
		
	}
	

    

}
