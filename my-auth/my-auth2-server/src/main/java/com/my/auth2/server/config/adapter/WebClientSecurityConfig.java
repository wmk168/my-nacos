package com.my.auth2.server.config.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.my.auth2.server.auth.provide.MyDaoAuthenticationProvider;
import com.my.auth2.server.encoder.NoEncryptPasswordEncoder;
import com.my.auth2.server.ex.LoginAuthenticationFailureHandler;
import com.my.auth2.server.ex.LoginAuthenticationSuccessHandler;
import com.my.auth2.server.filter.MyLoginUsernamePasswordAuthenticationFilter;
import com.my.auth2.server.service.MyUserDetailsService;

import lombok.extern.slf4j.Slf4j;

/**
 * Web客户端安全适配器。
   *  比如授权码链接申请后，会跳转入改页面，登录成功后就可以直接返回页面
 * 
 * @author minkeWei
 *
 */
@Slf4j
@Configuration
@EnableWebSecurity
@Order(1)//去掉这个排序后，默认授权登录页面就无法访问了，坑人，因为启动排序问题，加入这个后，页面权限就可以在这里面写了	
public class WebClientSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;
	
	@Autowired
	private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
	
	@Autowired
	private MyDaoAuthenticationProvider myAuthenticationProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//密码编码
	@Bean
    public PasswordEncoder passwordEncoder() {
		//不使用密码编码
        return new NoEncryptPasswordEncoder();//等价于NoOpPasswordEncoder.getInstance()
        //NoOpPasswordEncoder.getInstance();//用这个即可，注册后数据库存MD5,客户端也存储Md5，进行等价校验即可
        //new BCryptPasswordEncoder(); 使用这个后，用户密码以及 secret必须是要这个编码
        //$2a$10$NC/AEUNUrZki5LS9K2h6QOjX6RhoTXYyGrNTzJDoVbaVTAyzl93DK
        //BCrypt 与Md5，BCrypt每次生成的密码不一样，但是效率很慢 都是不可逆的，现在客户端可能支持不是很好
    }
	
	//自定义授权支持类
	@Bean
	public MyDaoAuthenticationProvider getMyDaoAuthenticationProvider() {
		MyDaoAuthenticationProvider authenticationProvider=new MyDaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	//自定义重新过滤器
	@Bean
    public MyLoginUsernamePasswordAuthenticationFilter getMyLoginUsernamePasswordAuthenticationFilter() {
    	MyLoginUsernamePasswordAuthenticationFilter my=new MyLoginUsernamePasswordAuthenticationFilter();
    	//注入application,测试通过
    	my.setApplicationContext(applicationContext);
    	my.setAuthenticationManager(authenticationManager);
    	//重写过滤器后，要使用这个注入才生效
    	my.setAuthenticationFailureHandler(loginAuthenticationFailureHandler);//登录失败
    	my.setAuthenticationSuccessHandler(loginAuthenticationSuccessHandler);//登录成功
    	return my;
    }
  
	
	//============================================================================
	
	//不定义，那无法做密码授权password grant_type
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  //必须要配置
		  //客户端账号,页面登录
		  //auth.userDetailsService(userDetailsService)//登录授权触发，账号登录
		  	  //.passwordEncoder(passwordEncoder()) 
		  ;
		  //自定义鉴权类
		  auth.authenticationProvider(myAuthenticationProvider);
	}
	

	/**
	 * 此方法可以参考，除非是客户端应用
	 * {@link ResourceServerConfig}
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * 默认页面
		 http    // 配置登陆页/login并允许访问
         .formLogin().permitAll()
         // 登出页
         .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
         // 其余所有请求全部需要鉴权认证
         .and().authorizeRequests().anyRequest().authenticated()
         // 由于使用的是JWT，我们这里不需要csrf
         .and().csrf().disable(); 
		 */
		
		 //自定义登录授权页面
        http
         .authorizeRequests().antMatchers("/static/**").permitAll().and()
         //在某个过滤器之前,这里配置无效
         //.addFilterBefore(myLoginAuthenticationProcessingFilter,UsernamePasswordAuthenticationFilter.class)
         //覆盖掉原来过滤器，这里配置无效，要在ResourceServerConfig配置
         .addFilterAt(getMyLoginUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
   		 .formLogin()
   		 .loginPage("/mylogin")//指定自定义登录页面
   		 .loginProcessingUrl("/login")//登录提交，这个路径都可以随意定义，不用Controller实现
   		 .failureHandler(loginAuthenticationFailureHandler)//登录授权失败异常
   		 .successHandler(loginAuthenticationSuccessHandler)//登录成功
   		 .permitAll()
   		 //.defaultSuccessUrl("/login_success") //成功登陆后跳转页面
   		 .and()
         .authorizeRequests().anyRequest().authenticated()
         .and()
         .csrf().disable()
         .httpBasic().disable()//不启用，不然页面会弹出一个输入框
         
          ;
          
		  super.configure(http);
	}

}
