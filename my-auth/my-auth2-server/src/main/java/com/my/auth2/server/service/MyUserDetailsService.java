package com.my.auth2.server.service;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.my.nacos.base.vo.ResultResVo;
import com.my.nacos.user.api.UserApi;
import com.my.nacos.user.po.UserPo;

import lombok.extern.slf4j.Slf4j;
/**
 * auth 用户详情业务类
 * @author minkeWei
 *http://127.0.0.1:5070/oauth/token?grant_type=password&username=小明4&password=333333
 */
@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserApi userApi;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//这个授权
		//测试查询用户
		//ResultResVo<UserPo>  resultResVo=userApi.getById(Long.parseLong(username));
		UserPo userPo=new UserPo();//resultResVo.getData();
		userPo.setUserName(username);
		userPo.setAge(333333);
		if(userPo==null) {
			throw new UsernameNotFoundException("找不到该用户，用户名：" + username);
		}
		//权限列表，也可以是角色，Uri等等，用户自定义
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        // 可用性 :true:可用 false:不可用
        boolean enabled = true;
        // 过期性 :true:没过期 false:过期
        boolean accountNonExpired = true;
        // 有效性 :true:凭证有效 false:凭证无效
        boolean credentialsNonExpired = true;
        // 锁定性 :true:未锁定 false:已锁定
        boolean accountNonLocked = true;
        //从数据库读取
        //测试创建一个自定义get权限，配合@PreAuthorize使用，必须在Application启用@EnableGlobalMethodSecurity(prePostEnabled = true)
        GrantedAuthority grantedAuthorityTest = new SimpleGrantedAuthority("test/user/get");
        grantedAuthorities.add(grantedAuthorityTest);
        /*
        for (Role role : member.getRoles()) {
            //角色必须是ROLE_开头，可以在数据库中设置
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
            grantedAuthorities.add(grantedAuthority);
            //获取权限
            for (Permission permission : role.getPermissions()) {
                GrantedAuthority authority = new SimpleGrantedAuthority(permission.getUri());
                grantedAuthorities.add(authority);
            }
        }
        */
        
        User user = new User(userPo.getUserName(), userPo.getAge()+"",
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
        //User对象返回后，password必须跟请求进来的密码一样
        log.info("==========>user=====>{}",user);
        return user;

	}

	public String getName() {
		return "小明测试";
	}
}
