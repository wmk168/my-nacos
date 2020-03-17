package com.my.swagger.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponses;

@ApiModel(description  = "公共响应数据")
public class ResponseVo<T> {
	@ApiModelProperty(value = "响应编码")
	private String code="200";
	@ApiModelProperty("响应信息")
	private String msg;
	@ApiModelProperty("响应数据")
	private T data;
	@ApiModelProperty("是否成功,true成功,false失败")
	private boolean success=true;
	
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
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
