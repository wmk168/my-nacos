package com.my.so.es.controller;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.so.es.model.es.ProductDoc;
import com.my.so.es.repository.ProductESRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * pro
 * @author minkeWei
 *
 */
@RestController
@RequestMapping("pro")
@Slf4j
public class ProductController {
	
	@Autowired
	private ProductESRepository productESRepository;
	
	@RequestMapping("add")
	public String add(ProductDoc ProductDoc) {
		ProductDoc=productESRepository.save(ProductDoc);
		log.info("新增产品",ProductDoc);
		return "ok";
	}
	
	@RequestMapping("likeName")
	public Page<ProductDoc> likeName(String likeName) {
		String searchs="";
		 // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("userName", likeName));
        // 搜索，获取结果
        Page<ProductDoc> items = productESRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        searchs += "总共数据数：" + total + "\n";
        items.forEach(userES -> {
           // searchs += userES.toString() + "\n";
        });

		return items;
	}
	
	@GetMapping("/listAll")
    public Iterable<ProductDoc> listAll() {
        Iterable<ProductDoc> userES = productESRepository.findAll();
        return userES;
    }
}
