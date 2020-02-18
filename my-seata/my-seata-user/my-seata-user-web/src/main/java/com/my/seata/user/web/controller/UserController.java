package com.my.seata.user.web.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.nacos.base.api.BaseControllerApiImpl;
import com.my.nacos.base.vo.ResultResVo;
import com.my.seata.user.api.UserApi;
import com.my.seata.user.mobel.po.UserPo;
import com.my.seata.user.mobel.vo.UserRegReqVo;
import com.my.seata.user.mobel.vo.UserRegResVo;

@RestController
@RequestMapping("user")
//@Validated//无效不支持,只能在方法上
public class UserController extends BaseControllerApiImpl<UserPo> implements UserApi {
	
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
}
