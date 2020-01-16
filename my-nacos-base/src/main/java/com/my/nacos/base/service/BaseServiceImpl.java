package com.my.nacos.base.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.my.nacos.base.dao.BaseDao;

/**
 * 基础数据业务接口实现类
 * @author wmk
 *
 */
public class BaseServiceImpl<T> implements BaseService<T>{
	
	@Autowired
	protected BaseDao<T> baseDao;
	
	public Integer add(T t) {
		Integer rt=baseDao.insert(t);
		return rt;
	}
	
	public Integer deleteById(Serializable id) {
		return baseDao.deleteById(id);
	}
	
	public Integer updateById(T t) {
		return baseDao.updateById(t);
	}
	
	public T getById(Serializable id) {
		return baseDao.selectById(id);
	}
	
	public Integer getCount(Wrapper<T> wrapper) {
		return baseDao.selectCount(wrapper);
	}

	public T get(Wrapper<T> wrapper) {
		return baseDao.selectOne(wrapper);
	}
	
	public List<T> getList(Wrapper<T> wrapper) {
		return baseDao.selectList(wrapper);
	}
	
	public IPage<T> getPage(IPage<T> page,Wrapper<T> wrapper) {
		return baseDao.selectPage(page, wrapper);
	}
	
}
