/**
 * 
 */
package com.my.auth2.server.config.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.my.auth2.server.ex.LoginAuthenticationFailureHandler;
import com.my.auth2.server.ex.LoginAuthenticationSuccessHandler;
import com.my.auth2.server.ex.MyAccessDeniedHandler;
import com.my.auth2.server.ex.MyAuthenticationEntryPoint;

/**
 * 拦截资源服务配置，路径配置
 * 
 * ResourceServerConfig 用于保护oauth相关的endpoints，同时主要作用于用户的登录(form login,Basic auth)
 * 	
 * @author minkeWei
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	//@Autowired
	//private MyLoginAuthenticationProcessingFilter myLoginAuthenticationProcessingFilter;
	/*@Autowired
	private MyLoginUsernamePasswordAuthenticationFilter myLoginUsernamePasswordAuthenticationFilter;
	*/
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ApplicationContext applicationContext;
	
    @Autowired
	private LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;
    
    @Autowired
	private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
	
    @Override
    public void configure(HttpSecurity http) throws Exception {
    	        
    		//一定要设置权限路径，不然启动会报错
            http
        	   //禁用csrf
               .csrf().disable()
               //禁用httpBasic
               .httpBasic().disable()
               
               //统一异常处理
               .exceptionHandling()
               .accessDeniedHandler(new MyAccessDeniedHandler())
               .authenticationEntryPoint(new MyAuthenticationEntryPoint())
               //.authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
              
               //验证地址：访问路径加上access_token=xxx才能访问
               .and().authorizeRequests().antMatchers("/api/**","/order/**").authenticated()
               //不用验证
			   //.and().authorizeRequests().antMatchers("/oauth/**").permitAll()     
               //.and().anonymous()//对于没有配置权限的其他请求允许匿名访问
               //所有必须都是要授权
               //.and().authorizeRequests().anyRequest().authenticated()
               
               /*
               .and().formLogin().permitAll()
               // 登出页
               .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
               // 其余所有请求全部需要鉴权认证
               .and().authorizeRequests().anyRequest().authenticated()
               // 由于使用的是JWT，我们这里不需要csrf
               .and().csrf().disable();
               /*
               /*
                //自定义登录页面
               .and()
                //在某个过滤器之前
                //.addFilterBefore(myLoginAuthenticationProcessingFilter,UsernamePasswordAuthenticationFilter.class)
                //覆盖掉原来过滤器
                //.addFilterAt(getMyLoginUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
          		.loginPage("/mylogin")//指定自定义登录页面
          		.loginProcessingUrl("/login")//登录提交，这个路径都可以随意定义，不用Controller实现
          		//如果重写了UsernamePasswordAuthenticationFilter类，那么这个代码是无效的，要在重写对象类里面注入
          		//.failureHandler(new LoginAuthenticationFailureHandler())//登录授权失败异常
          		.permitAll()
          		//.defaultSuccessUrl("/login_success") //成功登陆后跳转页面
          		 * 
          		 */
                 ;
    }

    
}
