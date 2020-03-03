package com.my.nacos.user.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.nacos.base.api.BaseControllerApiImpl;
import com.my.nacos.base.vo.ResultResVo;
import com.my.nacos.user.api.UserApi;
import com.my.nacos.user.po.UserPo;
import com.my.nacos.user.vo.UserRegReqVo;
import com.my.nacos.user.vo.UserRegResVo;

@RestController
@RequestMapping("user2")
//@Validated//无效不支持,只能在方法上
public class UserController2 extends BaseControllerApiImpl<UserPo> implements UserApi {

	//@RequestBody必须要配置，支持这样才能支持Feign
	//@RequestMapping("userReg")
	public ResultResVo<Integer> userReg(@Validated @RequestBody UserRegReqVo req) {
		UserPo userPo=new UserPo();
		BeanUtils.copyProperties(req, userPo);
		return ResultResVo.success(baseService.add(userPo));
	}

	@Override
	public ResultResVo<UserRegResVo> userRegRet(@Validated @RequestBody UserRegReqVo req) {
		UserRegResVo userRegResVo=new UserRegResVo();
		UserPo userPo=new UserPo();
		BeanUtils.copyProperties(req, userPo);
		baseService.add(userPo);
		userRegResVo.setId(userPo.getId());
		return ResultResVo.success(userRegResVo);
	}

	@Override
	public ResultResVo<Integer> userRegGetAndPost(UserRegReqVo req) {
		// TODO Auto-generated method stub
		return null;
	}
}
