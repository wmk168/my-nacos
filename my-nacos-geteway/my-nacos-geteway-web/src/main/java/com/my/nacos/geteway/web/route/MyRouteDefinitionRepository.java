package com.my.nacos.geteway.web.route;

import static java.util.Collections.synchronizedMap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Component//自定义存储资源，计划是修复Unable to find RoutePredicateFactory with name Path2异常，暂时无法实现
//@ConditionalOnMissingBean(RouteDefinitionRepository.class)
public class MyRouteDefinitionRepository implements RouteDefinitionRepository {

	private final Map<String, RouteDefinition> routes = new ConcurrentHashMap<String, RouteDefinition>();

	@Override
	public Mono<Void> save(Mono<RouteDefinition> route) {
		RouteDefinition routeDefinition=route.block();
		routes.put(routeDefinition.getId(), routeDefinition);
		
		//Mono<Void> m=Mono.empty();
		Mono<Void> m= route.flatMap(r -> {
			routes.put(r.getId(), r);
			return Mono.empty();
		});
		System.out.println("save RouteDefinitions==>"+routes+",mono=>"+m);
		getRouteDefinitions();
		
		return m;
	}

	@Override
	public Mono<Void> delete(Mono<String> routeId) {
		
		if (routes.containsKey(routeId.block())) {
			System.out.println("delete my xxxxxxxxxxx routes.remove id==>"+routeId.block());
			routes.remove(routeId.block());
			System.out.println("delete my xxxxxxxxx RouteDefinitions==>"+routes);
			return Mono.empty();
		}
		
		
		Mono<Void> m= routeId.flatMap(id -> {
			System.out.println("delete by init routes.remove id==>"+id);
			if (routes.containsKey(id)) {
				System.out.println("delete routes.remove id==>"+id);
				routes.remove(id);
				return Mono.empty();
			}
			return Mono.defer(() -> Mono.error(
					new NotFoundException("RouteDefinition not found: " + routeId)));
		});
		System.out.println("delete RouteDefinitions==>"+routes);
		return m;
	}

	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		System.out.println("getRouteDefinitions==>"+routes);
		return Flux.fromIterable(routes.values());
	}

}
