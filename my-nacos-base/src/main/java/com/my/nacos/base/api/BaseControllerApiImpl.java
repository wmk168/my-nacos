package com.my.nacos.base.api;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.nacos.base.execution.ServiceException;
import com.my.nacos.base.service.BaseService;
import com.my.nacos.base.vo.ResultResVo;

/**
 * api 基础控制器 实现类
 * @author wmk
 *
 */
public class BaseControllerApiImpl<T> implements BaseControllerApi<T>{
	
	@Autowired
	protected BaseService<T> baseService;
	
	@Override
	public ResultResVo<Integer> add(@RequestBody T t) {
		return ResultResVo.success(baseService.add(t));
	}
	
	/**
	 * @param t
	 * @return
	 */
	@Override
	public ResultResVo<T> addRetKey(@RequestBody T t) {
		int rt=baseService.add(t);
		if(rt<=0) {
			return ResultResVo.fail(t);
		}
		return ResultResVo.success(t);
	}
	
	@Override
	public ResultResVo<T> getById(@RequestParam("id") Serializable id) {
		try {
			return ResultResVo.success(baseService.getById(id));
		} catch (Throwable e) {
			//自定义异常
			throw new ServiceException(ResultResVo.fail(null),e);
		}
		
	}
}
