package com.my.auth2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * auth2：应用
 * 1、登录缓存在redis
 * 2、auth2
 * 3、配置、注册中心nacos
 *
 *
 *https://www.cnblogs.com/fp2952/p/8973613.html jwt非对称token
 *https://blog.csdn.net/wflovejava/article/details/88558149 zuul no pwd密码
 *https://gitee.com/oplus/open-cloud 开源代码
 *https://gitee.com/w_enhao/open-cloud 开源代码,自定义了很多了功能
 *Cloud-Platform https://github.com/wxiaoqi/Spring-Cloud-Admin
 *http://118.126.104.133:81/#/platformManager/menuManager
 *
 *权限：
 *https://www.jianshu.com/p/defa75b65a46
 *
 *模式说明：
 *https://www.jianshu.com/p/1f0fe9685098
 *https://blog.csdn.net/hry2015/article/details/96204602 各个类说明
 *https://www.cnblogs.com/mxmbk/p/9882860.html
 *https://www.jianshu.com/p/ea0a7d89f5f0 （详细介绍各个类作用）
 *
 * 测试流程：
 * basicth客户端+账号模式：
 * 
 * 1、请求授权有用户名，密码的
 *   //请求url：http://127.0.0.1:5070/oauth/token?grant_type=password&username=小明4&password=333333
     //basicAuth的username和password等于AuthorizationServerConfig的withClient的值和secret的值，这个一半是给客户端认证使用
     //上面请求url:参数：username=小明4&password=333333，等价于MyUserDetailService的username,返回对象User的password
 *
 *2、获取授权信息测试：根据token获取
 *http://127.0.0.1:5070/api/test/user?access_token=84789a16-2010-494a-8b38-8931ed5ecf51
 *
 *3、自定义权限：
 *http://127.0.0.1:5070/api/test/user/get?access_token=84789a16-2010-494a-8b38-8931ed5ecf51
 *http://127.0.0.1:5070/api/test/user/add?access_token=84789a16-2010-494a-8b38-8931ed5ecf51
 *@EnableGlobalMethodSecurity
 *
 *
 *授权码模式：一般用于页面授权，如qq授权登录
 *1、服务端加入AuthorizationServerConfig 加入生成 authorizationCodeServices 业务类
 *2、浏览器访问：redirect_uri必须,response_type=code固定
 *	http://127.0.0.1:5070/oauth/authorize?response_type=code&client_id=client_code&redirect_uri=http://www.baidu.com
 *   访问后，回车，然后选择：Approve,点击,就进入跳转页面，获得code
 *   
 *3、根据code获取token=xx信息，只要根据code获取一次token失败后，就会自动清除，
 *   再次请求就出现无法获取了，这个是为了安全作用，只要code核对错误一次就废除，如果要更改策略，可以修改JdbcAuthorizationCodeServices remove代码做控制
 *  获取地址：http://127.0.0.1:5070/oauth/token?grant_type=authorization_code&code=1TuIjd&client_id=client_code&client_secret=123456&redirect_uri=http://www.baidu.com
 
 * {@link TokenEndpoint} TokenEndpoint /oauth/token路径授权
 * {@link AuthorizationEndpoint}AuthorizationEndpoint 页面授权路相关
 *
 */
@SpringBootApplication(//exclude =DataSourceAutoConfiguration.class,
scanBasePackages = "com.my" )
//@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.my.nacos")
//启动定义权限拦截代码
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAuthorizationServer
//@EnableWebMvc //启动这个后，会导致static无效，不然就得实现适配器 MyWebMvcConfigurationSupport extends WebMvcConfigurationSupport
//@EnableRedisHttpSession
public class MyAuth2Application 
{
    public static void main( String[] args )
    {
        SpringApplication.run(MyAuth2Application.class, args);
    }
}
