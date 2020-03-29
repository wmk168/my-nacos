package com.my.so.es.model.es;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "user", type = "docs", shards = 1, replicas = 0)
@Data
@Builder//有这个了，必须有初始化方法
@NoArgsConstructor//去掉构造
@AllArgsConstructor
public class UserDoc {
	
	//主键,不自动增长，且同一个文档只有一个
	//如果不设置值，那么es会自动生成一个_id的主键，如果设置了值，那么就以设置的值为标准
    @Id
    @Field(type = FieldType.Auto)
    private Long id;//主键
 
    //ik分词器
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer ="ik_smart" )
    private String userName;
    
    //@Field默认有索引
    private String userPhone;

    //设置不索引，就无法进行搜索，不设定type会报错
    //org.elasticsearch.index.mapper.MapperParsingException: No type specified for field [imgUrl]
    @Field(index = false,type = FieldType.Keyword)
    private String imgUrl; 
    
    @Field(type = FieldType.Keyword)//这个不加聚合失败了，怪事了
    private Integer sex=0;
    
    //@Field(type = FieldType.Keyword)//必须要配置这个才能聚合，类型不能乱改
    private String brand;//聚合测试
    
    @Field(type = FieldType.Keyword)//必须要配置这个才能聚合，类型不能乱改
    private String cate;//聚合测试
    
    /**
     * 创建时间 采用自定义的时间格式
     */
    @Field(type = FieldType.Date, store = true, format = org.springframework.data.elasticsearch.annotations.DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss:SSS||yyyy-MM-dd||epoch_millis||date_optional_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss:SSS", timezone = "GMT+8")
    private Date createTime;

}
