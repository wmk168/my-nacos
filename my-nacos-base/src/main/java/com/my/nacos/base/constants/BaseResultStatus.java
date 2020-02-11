package com.my.nacos.base.constants;

import com.my.nacos.base.vo.CodeMsgResVo;

public interface BaseResultStatus {
	/**
	 *  调用成功
	 */
	public CodeMsgResVo SUCCESS=new CodeMsgResVo("0000","成功");
	/**
	 * 失败
	 */
	public CodeMsgResVo FAIL=new CodeMsgResVo("9999","失败");
	
	/**
	 * 服务内部异常
	 */
	public CodeMsgResVo EX_FAIL=new CodeMsgResVo("9990","服务内部异常");
	
	/**
	 * 断容状态-调用远程服务失败
	 */
	public CodeMsgResVo FALLBACK_FAIL=new CodeMsgResVo("9980","调用远程服务失败");
	/**
	 * 校验参数错误
	 */
	public CodeMsgResVo VALID_ERROR=new CodeMsgResVo("9970","校验参数错误");
	/**
	 * 流量控制错误
	 */
	public CodeMsgResVo FLOW_ERROR=new CodeMsgResVo("9960","流量控制错误");
	
}
