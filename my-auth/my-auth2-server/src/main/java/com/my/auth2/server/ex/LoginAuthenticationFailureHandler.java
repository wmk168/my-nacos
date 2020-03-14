/**
 * 
 */
package com.my.auth2.server.ex;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.my.auth2.server.utils.WebUtil;
import com.my.nacos.base.vo.ResultResVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录失败授权异常，继承{@link AuthenticationFailureHandler}有效<br>
 * 但是比如密码为空，密码check，返回401错误，是因为进入了 {@link SimpleUrlAuthenticationFailureHandler} <br>
 * 因此，改成继承该类
 * @author minkeWei
 *
 */
@Slf4j
@Component
public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		Throwable ex=exception.getCause();
		if(ex!=null) {
			log.error("登录授权失败,异常==>",exception);
		}else {
			log.error("登录授权失败==>{}",exception.getMessage());
		}
		WebUtil.setOriginHeader(response);
		
		if(WebUtil.isAjaxRequest(request)) {
			ResultResVo<?> resultResVo=ResultResVo.fail(null).setMsg(exception.getMessage());
			WebUtil.writeJson(response, resultResVo);
		}else {
			request.getSession().setAttribute("errorMsg", exception.getMessage());
			log.info("登录授权失败，设置errorMsg=======================>{}",exception.getMessage());
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
