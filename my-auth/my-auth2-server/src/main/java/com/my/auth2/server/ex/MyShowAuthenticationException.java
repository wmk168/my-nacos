package com.my.auth2.server.ex;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义显示异常提示信息
 * @author minkeWei
 *
 */
@SuppressWarnings("serial")
public class MyShowAuthenticationException extends AuthenticationException{

	public MyShowAuthenticationException(String msg) {
		super(msg);
	}

}
