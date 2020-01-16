package com.my.nacos.base.api;

import java.io.Serializable;

import com.my.nacos.base.vo.ResultResVo;

/**
 * 断容回调，基础类接口
 *  可以废除该接口
 * //在API定义这个没有多大意义，因为API没有实际调用类功能，只是返回一个公共调用服务异常提示，这个提示完全可以用拦截器去处理
 * /类请看{@link FallbackControllerAdvice.class}
 * @param <T>
 */
@Deprecated
public class BaseFallbackApi<T> implements BaseControllerApi<T>{

	@Override
	public ResultResVo<Integer> add(T t) {
		return ResultResVo.fallbackFail();
	}
	
	@Override
	public ResultResVo<T> getById(Serializable id) {
		return ResultResVo.fallbackFail();
	}

	public ResultResVo<T> addRetKey(T t) {
		return ResultResVo.fallbackFail();
	}
}
