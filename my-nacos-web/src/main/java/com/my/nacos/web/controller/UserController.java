package com.my.nacos.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.nacos.base.vo.ResultResVo;
import com.my.nacos.order.api.OrderApi;
import com.my.nacos.order.po.OrderPo;
import com.my.nacos.user.api.UserApi;
import com.my.nacos.user.po.UserPo;
import com.my.nacos.user.vo.UserRegReqVo;
import com.my.nacos.user.vo.UserRegResVo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController{

	@Autowired
	private UserApi userApi;
	@Autowired
	private OrderApi orderApi;
	
	
	@RequestMapping("getById")
	public ResultResVo<UserPo> getById(@RequestParam("id") Integer id) {
		log.info("请求ID={}",id);
		/*try {
			ResultResVo<OrderPo> resultResVo= orderApi.getById(id);
			log.info("order={} ",resultResVo.getCode());
		}catch (Exception e) {
			log.error("order 异常 ==>",e);
		}
		*/
		//支持自定义返回状态码，中间有问题一直是状态码对象类T类型转换错误
		ResultResVo<UserPo> rt=userApi.getById(id);
		log.info("UserPO={}",rt.getCode());
		return rt;
	}
	
	
	@RequestMapping("add")
	public ResultResVo<Integer> add() {
		UserPo userPo=new UserPo();
		userPo.setAge(1111);
		userPo.setUserName("1111xiaoming");
		
		
		ResultResVo<UserPo> rt=userApi.addRetKey(userPo);
		log.info("请求返回ID={}",rt.getData());
		
		return null;
	}
	
	@RequestMapping("reg")
	public ResultResVo<Integer> reg() {
		UserRegReqVo userPo=new UserRegReqVo();
		userPo.setAge(1111);
		userPo.setUserName("1111xiaoming");
		ResultResVo<Integer> rt=userApi.userReg(userPo);
		log.info("请求返回ID={}",rt);
		
		return rt;
	}
	
	@RequestMapping("regRet")
	public ResultResVo<UserRegResVo> regRet() {
		UserRegReqVo userPo=new UserRegReqVo();
		userPo.setAge(1111);
		userPo.setUserName("1111xiaoming");
		ResultResVo<UserRegResVo> rt=userApi.userRegRet(userPo);
		log.info("请求返回ID={}",rt);
		
		return rt;
	}
	
}
