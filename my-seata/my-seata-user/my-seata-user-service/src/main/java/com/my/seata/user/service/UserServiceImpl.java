package com.my.seata.user.service;

import org.springframework.stereotype.Service;

import com.my.nacos.base.service.BaseServiceImpl;
import com.my.seata.user.mobel.po.UserPo;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserPo> implements UserService{

}
