package com.my.so.es.model.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "product", type = "docs", shards = 1, replicas = 0)
@Data
@Builder//有这个了，必须有初始化方法
@NoArgsConstructor//去掉构造
@AllArgsConstructor
public class ProductDoc {
	
    @Id//不设定主键，系统报错
    //@Field(type = FieldType.Auto)
    private String id;//主键
 
    //ik分词器
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer ="ik_smart" )
    private String productName;

}
