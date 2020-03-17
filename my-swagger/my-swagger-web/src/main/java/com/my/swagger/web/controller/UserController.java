package com.my.swagger.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.swagger.web.vo.ResponseVo;
import com.my.swagger.web.vo.UserReqVo;
import com.my.swagger.web.vo.UserResVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Controller
@Api(value = "UserController", tags = "用户接口", description = "用户接口描述")
@RequestMapping("user")
public class UserController {
	
	@RequestMapping(path = "reg",method = { RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "用户注册",notes = "用户名，密码必须为空，密码最小长度6位")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "username", value = "用户名",required = true),
		@ApiImplicitParam(name = "password", value = "密码",required = true)
	})
	public ResponseVo<UserResVo> reg(
							@RequestParam(required =true) String username
			                ,@RequestParam(required =true) String password){
		ResponseVo<UserResVo> responseVo=new ResponseVo<UserResVo>();
		UserResVo userResVo=new UserResVo();
		userResVo.setCreateTime(new Date());
		userResVo.setAge(12);
		userResVo.setPassword(password);
		userResVo.setUsername(username);
		return responseVo;
	}
	
	@RequestMapping(path = "add1",method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	@ApiOperation("添加用户的接口1-有参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "username", value = "用户名", defaultValue = "李四"),
		@ApiImplicitParam(name = "address", value = "用户地址", defaultValue = "深圳", required = true)
	})
	public ResponseVo<?> add1(String username,String address){
		ResponseVo<String> responseVo=new ResponseVo<String>();
		responseVo.setData(username);
		return responseVo;
	}
	
	
	@RequestMapping(path = "add2",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation("添加用户的接口2-body")
	public ResponseVo<Long> add2(@RequestBody UserReqVo userReqVo){
		ResponseVo<Long> responseVo=new ResponseVo<Long>();
		responseVo.setData(100l);
		return responseVo;
	}
	
	@RequestMapping(path = "userlist",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation("用户列表")
	public ResponseVo<List<UserResVo>> userlist(@RequestBody UserReqVo userReqVo){
		ResponseVo<List<UserResVo>> responseVo=new ResponseVo<List<UserResVo>>();
		responseVo.setData(new ArrayList<UserResVo>());
		return responseVo;
	}
}
