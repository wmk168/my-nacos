/**
 * 
 */
package com.my.nacos.user.fallback;

import org.springframework.stereotype.Component;

import com.my.nacos.base.api.BaseFallbackApi;
import com.my.nacos.base.vo.ResultResVo;
import com.my.nacos.user.api.UserApi;
import com.my.nacos.user.po.UserPo;
import com.my.nacos.user.vo.UserRegReqVo;
import com.my.nacos.user.vo.UserRegResVo;

/**
 * 断容错回调，可以废除该接口
 * //在API定义这个没有多大意义，因为API没有实际调用类功能，只是返回一个公共调用服务异常提示，这个提示完全可以用拦截器去处理
 * /类请看{@link FallbackControllerAdvice.class}
 * @author wmk
 */
//@Component
@Deprecated
public class UserApiFallback //extends BaseFallbackApi<UserPo> implements UserApi
{

	/**
	 * @param req
	 * @return
	 */
	
	public ResultResVo<Integer> userReg(UserRegReqVo req) {
		return ResultResVo.fallbackFail();
	}

	public ResultResVo<UserRegResVo> userRegRet(UserRegReqVo req) {
		// TODO Auto-generated method stub
		return ResultResVo.fallbackFail();
	}
	
}
