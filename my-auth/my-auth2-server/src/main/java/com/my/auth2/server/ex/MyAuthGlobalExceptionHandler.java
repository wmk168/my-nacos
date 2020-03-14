package com.my.auth2.server.ex;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@ResponseBody
@Slf4j
public class MyAuthGlobalExceptionHandler {
	 /**
     * 统一异常处理
     * AuthenticationException
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({AuthenticationException.class
    		,OAuth2Exception.class, InvalidTokenException.class
    		,Exception.class
    	 })
    public static Object authenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
    	log.error("访问Controller异常通知：====>",ex);
		Map<String, Object> codemap=new HashMap<String, Object>();
		codemap.put("code", "4000");
		codemap.put("msg", ex.getMessage());
		//codemap.put("error", ex);
		return codemap;
    }

}
