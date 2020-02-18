package com.my.seata.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.nacos.base.api.BaseControllerApi;
import com.my.nacos.base.vo.ResultResVo;
import com.my.seata.user.mobel.po.UserPo;
import com.my.seata.user.mobel.vo.UserRegReqVo;
import com.my.seata.user.mobel.vo.UserRegResVo;

@FeignClient(name = "user-web", path = "user"
//在API定义这个没有多大意义，因为API没有实际调用类功能，只是返回一个公共调用服务异常提示，这个提示完全可以用拦截器去处理
//如新增了FallbackControllerAdvice类，假如需要指定具体方法
//, fallback = UserApiFallback.class
)
public interface UserApi extends BaseControllerApi<UserPo>{
	
	//@RequestBody 非必填配置，支持这样才能支持Feign
	@RequestMapping(path = "userReg")
	public ResultResVo<Integer> userReg(UserRegReqVo req);
	
	@RequestMapping(path = "userRegRet")
	public ResultResVo<UserRegResVo> userRegRet(UserRegReqVo req);
	
}
