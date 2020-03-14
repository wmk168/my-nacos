package com.my.simple.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//注意:坑爹xxx 启动这个后，在配置文件里面配置static文件目录是无效，必须要使用MyWebMvcConfigurationSupport对象代码控制
//@EnableWebMvc
public class SimpleWebApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(SimpleWebApplication.class, args);
    }
}
