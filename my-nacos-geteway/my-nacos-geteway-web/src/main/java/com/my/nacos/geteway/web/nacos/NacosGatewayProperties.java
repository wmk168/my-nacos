package com.my.nacos.geteway.web.nacos;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * NacosGetway自定义配置
 * @author minkeWei
 *
 */
@ConfigurationProperties(prefix="spring.cloud.getway.nacos", ignoreUnknownFields = true)
@Configuration
public class NacosGatewayProperties {
	
	private String serverAddr//="127.0.0.1:8848"
			;
	
	private String dataId="gw-route";
	
	private String groupId="GW-ROUTE-GROUP";
	
	private Long timeout=5000l;


	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

}
