package com.my.swagger.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户信息")
public class UserInfo {
	@ApiModelProperty("地区")
	private String area;
}
