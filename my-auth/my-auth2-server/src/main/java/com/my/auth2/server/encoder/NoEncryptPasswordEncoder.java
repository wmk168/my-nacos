package com.my.auth2.server.encoder;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.my.auth2.server.ex.MyShowAuthenticationException;

public class NoEncryptPasswordEncoder implements PasswordEncoder {
 
    @Override
    public String encode(CharSequence charSequence) {
        return (String) charSequence;
    }
 
    @Override
    public boolean matches(CharSequence charSequence, String s) {
    	//用户端输入密码认证
    	//密码对比 密码对 true 反之 false
        //CharSequence 数据库中的密码
        //s 前台传入的密码
    	if(!s.equals((String) charSequence)) {
    		throw new BadCredentialsException("密码错误！");
    	}
        return true;
    }


}
