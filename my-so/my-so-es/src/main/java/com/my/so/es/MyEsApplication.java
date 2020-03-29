package com.my.so.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * es搜索
 *
 */
@SpringBootApplication
@EnableElasticsearchRepositories
public class MyEsApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(MyEsApplication.class, args);
    }
    
}
