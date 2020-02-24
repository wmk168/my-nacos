/**
 * 
 */
package com.my.nacos.geteway.web.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 动态规则业务
 * @author minkeWei
 *
 */
@Service
@Slf4j
public class DynamicRouteServiceImpl  implements ApplicationEventPublisherAware {

	@Autowired
	private RouteDefinitionWriter routeDefinitionWriter;
	private ApplicationEventPublisher publisher;
	
	//@Autowired
	//无法用 RouteDefinitionRouteLocator definitionRouteLocator;

	/*
	 * @Autowired RouteLocator routeLocator;
	 */
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher=applicationEventPublisher;
	}

	//增加路由
    public String add(RouteDefinition definition) {

        routeDefinitionWriter.save(Mono.just(definition)).subscribe()
        ;
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
       
        return "success";
    }

    //更新路由
    public boolean update(RouteDefinition definition) {
        try {
        	this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        	log.info("delete routeId={},definition={}",definition.getId(),definition);
        } catch (Exception e) {
        	String str="update fail,not find route  routeId: "+definition.getId();
        	log.error(str,e);
            return false;
        }
        try {
             add(definition);
        } catch (Exception e) {
            String str="update route  fail";
            log.error(str,e);
            return false;
        }
        return true;
    }
    
  //删除路由
    public Mono<ResponseEntity<Object>> delete(String id) {
        return this.routeDefinitionWriter.delete(Mono.just(id))
                .then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
                .onErrorResume(t -> t instanceof NotFoundException, t -> Mono.just(ResponseEntity.notFound().build()));
    }

}
