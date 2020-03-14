package com.my.auth2.server.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.auth2.server.service.MyUserDetailsService;

/**
 * api需要认证测试
 * 认证通过后就可以操作以下权限
 */
@RestController
public class ApiTestUserController {
 
    @Autowired
    private MyUserDetailsService userDetailService;
 
    @Autowired
    private ConsumerTokenServices consumerTokenServices;
    
    //测试url：http://127.0.0.1:5070/api/test/user?access_token=cae70267-bdb8-4ac2-839d-538a0b6fb0fe
    //access_token是登录授权成功后获取到的
    @RequestMapping("/api/test/user")
    public Principal user(Principal user) {
        return user;
    }
 
    //清楚token,测试通过
    @RequestMapping(value = "/api/test/user/exit")
    public Object revokeToken(String access_token) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (consumerTokenServices.revokeToken(access_token)) {
            result.put("msg","注销成功");
        } else {
            result.put("msg","注销失败");
        }
        return result;
    }
    
    
    @RequestMapping(value = "/api/test/user/get")
    @PreAuthorize("hasAnyAuthority('test/user/get')")//测试是否get权限
    public Object get(String access_token) {
    	return "有get权限，access_token"+access_token;
    }
    
    @RequestMapping(value = "/api/test/user/add")
    @PreAuthorize("hasAnyAuthority('add')")//测试是否add权限
    public Object add(String access_token) {
    	return "有add权限，access_token"+access_token;
    }
    
    @RequestMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        //for debug
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id : " + id+"> name: "+authentication.getName()+" authentication >:"+authentication;
    }
    
    
    @RequestMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        //for debug
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "order id : " + id+" >name: "+authentication.getName()+"authentication >: "+authentication;
    }

    

}
