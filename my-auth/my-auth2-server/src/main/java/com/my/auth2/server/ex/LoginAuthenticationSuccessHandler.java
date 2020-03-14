package com.my.auth2.server.ex;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.my.auth2.server.utils.WebUtil;
import com.my.nacos.base.vo.ResultResVo;

/**
 *     登录成功后，重新对象
 * @author minkeWei
 *
 */
@Component
public class LoginAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		WebUtil.setOriginHeader(response);
		if(WebUtil.isAjaxRequest(request)) {
			ResultResVo<?> resultResVo=ResultResVo.success(null).setMsg("登录成功!");
			WebUtil.writeJson(response, resultResVo);
		}else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
}
