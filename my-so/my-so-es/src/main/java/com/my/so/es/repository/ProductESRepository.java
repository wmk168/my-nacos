package com.my.so.es.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.my.so.es.model.es.ProductDoc;
import com.my.so.es.model.es.UserDoc;

@Repository
public  interface ProductESRepository extends ElasticsearchRepository<ProductDoc, String> {

}
