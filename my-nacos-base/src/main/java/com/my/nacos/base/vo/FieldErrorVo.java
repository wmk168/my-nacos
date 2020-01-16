package com.my.nacos.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldErrorVo {
	private String field;
	private String msg;
}
