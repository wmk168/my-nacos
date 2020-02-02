package com.my.nacos.base.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.my.nacos.base.constants.BaseResultStatus;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(value=Include.NON_NULL)//返回null字段不显示
public class ResultResVo<T>{

    boolean success=false;
	String code;
	String msg;
	T data;
	Object error;//之前定义成T导致自定义状态码无法返回feign客户端，坑爹，找了很久
	
	public static <T> ResultResVo<T> setResult(CodeMsgResVo codeMsg){
		return setResult(codeMsg, null);
	}
	public static <T> ResultResVo<T> setResult(CodeMsgResVo codeMsg,T data){
		return new ResultResVo<T>().setCode(codeMsg.code)
				.setMsg(codeMsg.msg).setData(data);
	}
	
	public static <T> ResultResVo<T> fail(T data){
		return setResult(BaseResultStatus.FAIL, data);
	}
	
	public static <T> ResultResVo<T> success(T data){
		return setResult(BaseResultStatus.SUCCESS, data).setSuccess(true);
	}
	
	public static <T> ResultResVo<T> fallbackFail(){
		return setResult(BaseResultStatus.FALLBACK_FAIL, null);
	}
	
	public static <T> ResultResVo<T> validError(String msg){
		return validError(msg,null);
	}
	
	public static <T> ResultResVo<T> validError(String msg,Object error){
		CodeMsgResVo codeMsgResVo=BaseResultStatus.VALID_ERROR;
		return new ResultResVo<T>().setCode(codeMsgResVo.code)
				.setMsg(msg==null?codeMsgResVo.msg:msg).setData(null).setError(error);
	}

	public ResultResVo<T> setError(Object error) {
		this.error = error;
		return this;
	}

	/**
	 * @return the success
	 */
	public Boolean isSuccess() {
		return success;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}
	/**
	 * @param success the success to set
	 */
	public ResultResVo<T> setSuccess(boolean success) {
		this.success = success;
		return this;
	}
	/**
	 * @param code the code to set
	 */
	public ResultResVo<T> setCode(String code) {
		this.code = code;
		return this;
	}
	/**
	 * @param msg the msg to set
	 */
	public ResultResVo<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	/**
	 * @param data the data to set
	 */
	public ResultResVo<T> setData(T data) {
		this.data = data;
		return this;
	}
	
}
