package com.my.seata.client.scloud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.client.RestTemplate;


public class NacosConfigTest {

	public static void main(String[] args) {
		String filePath="E:\\Java\\Spring-Cloud-Alibaba\\seata\\seata\\script\\config-center\\config.txt";
		Map<String, String> configMap;
		try {
			configMap = getConfig(filePath);
			System.out.println(configMap);
			
			publicConfig(configMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static Map<String, String> getConfig(String filePath) throws Exception{
		Map<String, String> configMap=new HashMap<String, String>();
		FileReader fileReader=new FileReader(new File(filePath));
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		String line=null;
		while((line=bufferedReader.readLine())!=null) {
			String[] kv=line.split("=");
			configMap.put(kv[0], kv[1]);
		}
	
		return configMap;
	}
	
	public static void publicConfig(Map<String, String> configMap) {
		Map<String, String> requestEntity = new HashMap();
        //requestEntity.add("clientFlag", clientFlag);
		RestTemplate restTemplate=new RestTemplate();
		Iterator<Entry<String, String>> iterator=configMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, String> entry=iterator.next();
			String rt=restTemplate.postForObject("http://127.0.0.1:8848/nacos/v1/cs/configs?dataId="+entry.getKey()+"&group=SEATA_GROUP&content="+entry.getValue()+"&tenant=", requestEntity, String.class);
			System.out.println(rt);
		}
	}

}
