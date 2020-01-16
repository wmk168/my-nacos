package com.my.nacos.base.service;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 基础数据业务接口
 * @author wmk
 *
 */
public interface BaseService<T> {
	
	public Integer add(T t);

	public Integer deleteById(Serializable id);
	
	public Integer updateById(T t);
	
	public T getById(Serializable id);
	
	public Integer getCount(Wrapper<T> wrapper);

	public T get(Wrapper<T> wrapper);
	
	public List<T> getList(Wrapper<T> wrapper);
	
	public IPage<T> getPage(IPage<T> page,Wrapper<T> wrapper);
}
