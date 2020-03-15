package com.my.auth2.server.config.adapter;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.my.auth2.server.ex.MyWebResponseExceptionTranslator;
import com.my.auth2.server.service.MyUserDetailsService;

import lombok.extern.slf4j.Slf4j;

/**
 * auth2 认证服务配置
 * @author minkeWei
 *
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	private TokenStore tokenStore;
	
	//auth token
    @Bean
    public TokenStore tokenStore() {
    	//存储redis，如果在没有刷新token情况下，再次登录刷新就不会刷新token
        //return new RedisTokenStore(redisConnectionFactory);
    	//jwt存储，每次登录都会刷新，jwt也是都是都要更新操作，如果是使用jwt生成的，那么最好使用这个
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    
    @Autowired
    private DataSource dataSource;
    
    //authorization_code 授权码模式
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
    	//授权码模式，当前存储于内存，实际应该用jdbc
    	//return new InMemoryAuthorizationCodeServices();
    	//生产授权码后存储在数据库
    	return new JdbcAuthorizationCodeServices(dataSource);
    }
   
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ClientDetailsService clientDetailsService;
    
    @Bean
    public ClientDetailsService clientDetailsService() {
    	//从jdbc读取授权信息
    	JdbcClientDetailsService clientDetailsService= new JdbcClientDetailsService(dataSource);
    	clientDetailsService.setPasswordEncoder(passwordEncoder);
    	return clientDetailsService;
    }
    
    //==========token使用jwt分布式===============
    @Autowired
    private JwtAccessTokenConverter twtAccessTokenConverter;
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
    	JwtAccessTokenConverter accessTokenConverter=new JwtAccessTokenConverter();
    	accessTokenConverter.setSigningKey("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
    	return accessTokenConverter;
    }
    @Autowired
    private TokenEnhancer tokenEnhancer;
    
    //用来配置令牌端点(Token Endpoint)的安全约束
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    	//授权端点开放
    	// 允许表单认证
        security.allowFormAuthenticationForClients();

        // 开启/oauth/token_key验证端口无权限访问
        security.tokenKeyAccess("permitAll()");
        // 开启/oauth/check_token验证端口认证权限访问
        security.checkTokenAccess("isAuthenticated()");
    }
    
    /**
     	* 用来配置客户端详情服务（ClientDetailsService），
     	* 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息；
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	
    	//生产环境一般是jdbc
    	 clients.withClientDetails(clientDetailsService);
    	//内存测试
    	//inMemory(clients);
    	
    }
    //内存配置测试
    private void inMemory(ClientDetailsServiceConfigurer clients) throws Exception {
    	
    	//配置客户端basicAuth+用户名，密码登录=生成token
        clients.inMemory()
        .withClient("web")//客户端basicAuth的username
        .secret("111111")//客户端basicAuth的password
        .scopes("read")//必须，值可以自己定义范围
        
        //.resourceIds("order")
        /*scoes值说明
         * read: Grants read accesss
         * write: Grants write access
         * admin: Grants access to admin operations
         */
        .authorizedGrantTypes("password", "refresh_token")
        //登录用户+密码+code参数=生成code模式: 授权码模式
        .and()
        .withClient("client_code").secret("123456")
        .redirectUris("http://www.baidu.com")//必须要配置，不然报错
        //.resourceIds("order") 无法测试
        .scopes("all")
        .autoApprove(true)//设置这个后，认证通过后就直接跳转uri页面
        .authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token",
                "password", "implicit")
        //.additionalInformation("")
        //client_credentials 直接授权获取access_token
        //http://127.0.0.1:5070/oauth/token?grant_type=client_credentials&username=fdsfds&password=333333
        
       /* .and()
        .withClient("webapp")
        .scopes("read")
        .authorizedGrantTypes("implicit")
        .and()
        .withClient("browser")
        .authorizedGrantTypes("refresh_token", "password")
        .scopes("read")
        */
        ;
    }

    /**
     	* 客户端操作，配置相关信息
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
 
    	endpoints.tokenStore(tokenStore);//token 存储地方，redis等等
    	//如果是jwt存储，那么就使用jwt存储
    	if(tokenStore instanceof JwtTokenStore) {
    		//使用jwt  生成token
    		endpoints.accessTokenConverter(twtAccessTokenConverter);
    	}
    	       
    	endpoints
		         .userDetailsService(userDetailsService)//当刷新token的时候会调用该类信息
		         .authenticationManager(authenticationManager)//配置管理
		    	 //.tokenServices(defaultTokenServices())//token
		    	 
		    	 //客户端支持get和post提交，修复异常："error":"method_not_allowed"
		         .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
		         
		         //设置授权码模式，需要加的
		         .authorizationCodeServices(authorizationCodeServices)
		         
		         //.userApprovalHandler(userApprovalHandler(tokenStore))
		         //.approvalStore(approvalStore(tokenStore))
		    	 //异常绑定
		    	 .exceptionTranslator(new MyWebResponseExceptionTranslator())
		    	 
		    	 // 自定义确认授权页面
		         .pathMapping("/oauth/confirm_access", "/oauth/confirm_access")
		         // 自定义错误页
		         .pathMapping("/oauth/error", "/oauth/error")

		    	 ;
    }
    
    //@Bean
    //@Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        //handler.setRequestFactory(this.g);
        //handler.setClientDetailsService(clientDetailsService);
        return handler;
    }
    
    //@Bean
    //@Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }
    
    
    /**
                * 注意，自定义TokenServices的时候，需要设置@Primary，否则报错
     */
    //@Primary
    //@Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        //tokenServices.setClientDetailsService(clientDetails());
        // token有效期自定义设置，默认12小时
        tokenServices.setAccessTokenValiditySeconds(60*60*12);
        // refresh_token默认30天
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        return tokenServices;
    }

}
