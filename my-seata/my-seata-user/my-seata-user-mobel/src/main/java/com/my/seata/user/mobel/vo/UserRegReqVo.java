
package com.my.seata.user.mobel.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

//用户注册
@Data
public class UserRegReqVo {
	@NotBlank(message = "用户名不能为空")
	String userName;//如果大小号就自动分割成了user_name
	@NotNull
	Integer age;//自动对应
}
