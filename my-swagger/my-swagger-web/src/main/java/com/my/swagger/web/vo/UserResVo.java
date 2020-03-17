package com.my.swagger.web.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description  = "用户响应对象")
public class UserResVo {
	@ApiModelProperty(value ="用户名",required =true)
	String username;
	@ApiModelProperty(value ="密码")
	String password;
	@ApiModelProperty("年龄")
	Integer age;
	@ApiModelProperty("金额")
	BigDecimal amount;
	@ApiModelProperty("创建时间")
	Date createTime;
	
	@ApiModelProperty("信息列表")
	List<UserInfo> infoList=new ArrayList<UserInfo>();
	@ApiModelProperty("信息对象")
	UserInfo info=new UserInfo();
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUsername() {
		return username;
	}
	
}
