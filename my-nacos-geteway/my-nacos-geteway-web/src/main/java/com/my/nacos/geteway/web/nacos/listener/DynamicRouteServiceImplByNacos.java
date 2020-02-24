package com.my.nacos.geteway.web.nacos.listener;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.my.nacos.geteway.web.nacos.NacosGatewayProperties;
import com.my.nacos.geteway.web.route.DynamicRouteServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态规则，从Nacos配置中心
 * 格式：
 * [
    {
    "description": "用户服务路由",
    "id": "user-provider",
    "uri": "lb://user-provider",
    "order": 0,
    "filters": [
        {
            "name": "StripPrefix",
            "args": {
                "_genkey_0": "1"//_genkey_0必须是这个
            }
        }
    ],
    "predicates": [
        {
            "name": "Path",
            "args": {
                "_genkey_0": "/api/**"//_genkey_0必须是这个有效，/**代表下面所有路径
            }
        },{
            "name": "Path2",//name关键词不能写错，配置写错了就只能重启无法才能生效，
            "args": {
                "_genkey_0": "/api/**"
            }
        }
    ]
    ]
   }
]
 * 
 * 
 */
@Component
@Slf4j
public class DynamicRouteServiceImplByNacos implements CommandLineRunner{
	
	 /**
     * Single-thread pool. Once the thread pool is blocked, we throw up the old task.
     */
    private final ExecutorService pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(1), new NamedThreadFactory("getway-nacos-update"),
        new ThreadPoolExecutor.DiscardOldestPolicy());

	@Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

	@Autowired
    private NacosGatewayProperties nacosGatewayProperties;
	
	@Override
	public void run(String... args) throws Exception {
		dynamicRouteByNacosListener ();
	}
	
	//
	public void dynamicRouteByNacosListener (){
		ConfigService configService=null;
        try {
        	if(StringUtils.isBlank(nacosGatewayProperties.getServerAddr())) {
        		log.debug("spring.cloud.getway.nacos.serverAddr no set value");
        		return ;
        	}
        	log.info("Getway Nacos ServerAddr={}",nacosGatewayProperties.getServerAddr());
            configService=NacosFactory.createConfigService(nacosGatewayProperties.getServerAddr());
            String content = configService.getConfig(nacosGatewayProperties.getDataId(), nacosGatewayProperties.getGroupId(), nacosGatewayProperties.getTimeout());
            log.info("Getway Nacos initi Get content={}",content);
            updateRouteByJson(content);
        } catch (NacosException e) {
           e.printStackTrace();
           log.error("Getway Nacos Server link error,ip={}",nacosGatewayProperties,e);
        }
        
        //新增监听器
        try {
	        if(configService!=null) {
	        	 configService.addListener(nacosGatewayProperties.getDataId(), nacosGatewayProperties.getGroupId(), new Listener()  {
	                 @Override
	                 public void receiveConfigInfo(String configInfo) {
	                 	log.info("Getway Nacos Listener update content={}",configInfo);
	                 	updateRouteByJson(configInfo);
	                 }
	                 @Override
	                 public Executor getExecutor() {
	                     return pool;//一定要加上这个，不然无法监听
	                 }
	             });
	        }
		} catch (NacosException e) {
	        e.printStackTrace();
	        log.error("Getway Nacos Server link listener error,ip={}",nacosGatewayProperties,e);
	     }
    }
	
	public void updateRouteByJson(String jsonStr) {
		List<RouteDefinition> list = JSONObject.parseArray(jsonStr, RouteDefinition.class);
    	list.forEach(definition->{
    		dynamicRouteService.update(definition);
    	});
	}


	class NamedThreadFactory implements ThreadFactory {

	    private final ThreadGroup group;
	    private final AtomicInteger threadNumber = new AtomicInteger(1);

	    private final String namePrefix;
	    private final boolean daemon;

	    public NamedThreadFactory(String namePrefix, boolean daemon) {
	        this.daemon = daemon;
	        SecurityManager s = System.getSecurityManager();
	        group = (s != null) ? s.getThreadGroup() :
	            Thread.currentThread().getThreadGroup();
	        this.namePrefix = namePrefix;
	    }

	    public NamedThreadFactory(String namePrefix) {
	        this(namePrefix, false);
	    }

	    @Override
	    public Thread newThread(Runnable r) {
	        Thread t = new Thread(group, r, namePrefix + "-thread-" + threadNumber.getAndIncrement(), 0);
	        t.setDaemon(daemon);
	        return t;
	    }
	}
	
}
