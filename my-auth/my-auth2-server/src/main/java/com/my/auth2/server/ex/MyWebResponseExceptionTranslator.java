package com.my.auth2.server.ex;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

import lombok.extern.slf4j.Slf4j;

/**
 *  验证端点异常，比如访问 oauth/token登录密码错误
 * @author minkeWei
 *
 */
@Slf4j
public class MyWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator{
	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		log.error("异常：MyWebResponseExceptionTranslator===>",e);
		OAuth2Exception oauth2Exception=new OAuth2Exception(e.getMessage());
		oauth2Exception.addAdditionalInformation("code", "5000");
		oauth2Exception.addAdditionalInformation("msg", e.getMessage());
		return ResponseEntity.status(200).body(oauth2Exception);
		//return super.translate(e);
	}
}
