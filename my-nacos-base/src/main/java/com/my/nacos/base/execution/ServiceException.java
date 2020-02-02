package com.my.nacos.base.execution;

import com.my.nacos.base.vo.ResultResVo;

@SuppressWarnings("serial")
public class ServiceException extends RuntimeException{
	
	private String code; 
	private String msg;
	
	public ServiceException(String code,String msg,Throwable e) {
		super(e);
		this.code=code;
		this.msg=msg;
	}
	
	public ServiceException(ResultResVo<?> resultResVo) {
		this.code=resultResVo.getCode();
		this.msg=resultResVo.getMsg();
	}
	
	public ServiceException(ResultResVo<?> resultResVo,Throwable e) {
		super(e);
		this.code=resultResVo.getCode();
		this.msg=resultResVo.getMsg();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
