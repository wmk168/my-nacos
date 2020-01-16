package com.my.nacos.base.api;

import java.io.Serializable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.nacos.base.vo.ResultResVo;

/**
 * 基本控制层API类
 * @author wmk
 *
 * @param <T>
 */

public interface BaseControllerApi<T> {
	
	/**
	 * 插入返回结果
	 */
	@PostMapping("add")
	public ResultResVo<Integer> add(@RequestBody T t);//@RequestBody记得加上，不然feign对象无法注入值
	
	/**
	 * 插入成功，返回对象，主键值
	 */
	@PostMapping("addRetKey")
	public ResultResVo<T> addRetKey(@RequestBody T t);
	
	//SpringMvc不支持接口形式，这个是不是版本的问题，需要研究，研究后发现，2.0.9不支持Object直接映射，2.1.0这个版本后就可以
	//支持，因此如果要支持，实现类重写即可,@RequestParam("id")
	@RequestMapping("getById")
	public ResultResVo<T> getById(@RequestParam("id") Serializable id);
}
