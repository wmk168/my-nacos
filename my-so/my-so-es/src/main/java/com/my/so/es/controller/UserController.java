package com.my.so.es.controller;

import java.util.Date;
import java.util.List;

import org.elasticsearch.action.get.MultiGetRequest.Item;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.UnmappedTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.my.so.es.model.es.UserDoc;
import com.my.so.es.repository.UserESRepository;

import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * user
 * @author minkeWei
 *
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
	
	@Autowired
	private UserESRepository userESRepository;
	
	@RequestMapping("add")
	public String add(UserDoc userDoc) {
		userDoc.setId(System.currentTimeMillis());
		userDoc.setImgUrl("http://"+userDoc.getId()+".jpg");
		if(userDoc.getCreateTime()==null) {
			userDoc.setCreateTime(new Date());
		}
		userDoc=userESRepository.save(userDoc);
		log.info("新增用户",userDoc);
		return "ok";
	}
	
	public static void main(String[] args) {
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
	    // 不查询任何结果,比如返回字段
	    queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
	    // 1、添加一个新的聚合，聚合类型为terms，聚合名称为ageAgg，聚合字段为age
	    queryBuilder.addAggregation(
	        AggregationBuilders.terms("cate").field("cate"));
	    System.out.println(JSONObject.toJSONString(queryBuilder.build()));
	}
	
	@RequestMapping("likeName")
	public Page<UserDoc> likeName(String likeName) {
		String searchs="";
		 // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("userName", likeName));
        // 搜索，获取结果
        Page<UserDoc> items = userESRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        searchs += "总共数据数：" + total + "\n";
        items.forEach(userES -> {
           // searchs += userES.toString() + "\n";
        });

		return items;
	}
	
	@GetMapping("/listAll")
    public Iterable<UserDoc> listAll() {
        Iterable<UserDoc> userES = userESRepository.findAll();
        return userES;
    }
	
	@GetMapping("/agg")
    public String cate() {
		 NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		    // 不查询任何结果,比如返回字段
		    queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
		    // 1、添加一个新的聚合，聚合类型为terms，聚合名称为ageAgg，聚合字段为age
		    queryBuilder.addAggregation(
		        AggregationBuilders.terms("cate").field("cate"));
		    // 2、查询,需要把结果强转为AggregatedPage类型
		    AggregatedPage<UserDoc> aggPage = (AggregatedPage<UserDoc>) userESRepository.search(queryBuilder.build());
		    // 3、解析
		    // 3.1、从结果中取出名为brands的那个聚合，
		    // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
		   // Aggregation aggregation=aggPage.getAggregation("cate");
		    
		    // 3、解析
		    // 3.1、从结果中取出名为brands的那个聚合，
		    // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
		    StringTerms agg = (StringTerms) aggPage.getAggregation("cate");
		    // 3.2、获取桶
		    List<StringTerms.Bucket> buckets = agg.getBuckets();
		    // 3.3、遍历
		    for (StringTerms.Bucket bucket : buckets) {
		        // 3.4、获取桶中的key，即品牌名称
		        System.out.println(bucket.getKeyAsString());
		        // 3.5、获取桶中的文档数量
		        System.out.println(bucket.getDocCount());
		    }
		    return "ok";
    }
	
	@GetMapping("/sex")
    public String age() {
		 NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		    // 不查询任何结果
		    queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
		    // 1、添加一个新的聚合，聚合类型为terms，聚合名称为ageAgg，聚合字段为age
		    queryBuilder.addAggregation(
		        AggregationBuilders.terms("sex").field("sex"));
		    // 2、查询,需要把结果强转为AggregatedPage类型
		    AggregatedPage<UserDoc> aggPage = (AggregatedPage<UserDoc>) userESRepository.search(queryBuilder.build());
		    // 3、解析
		    // 3.1、从结果中取出名为brands的那个聚合，
		    // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
		   // Aggregation aggregation=aggPage.getAggregation("cate");
		    
		    // 3、解析
		    // 3.1、从结果中取出名为brands的那个聚合，
		    // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
		    StringTerms agg = (StringTerms) aggPage.getAggregation("sex");
		    // 3.2、获取桶
		    List<StringTerms.Bucket> buckets = agg.getBuckets();
		    // 3.3、遍历
		    for (StringTerms.Bucket bucket : buckets) {
		        // 3.4、获取桶中的key，即品牌名称
		        System.out.println(bucket.getKeyAsString());
		        // 3.5、获取桶中的文档数量
		        System.out.println(bucket.getDocCount());
		    }
		    return "ok";
    }
}
