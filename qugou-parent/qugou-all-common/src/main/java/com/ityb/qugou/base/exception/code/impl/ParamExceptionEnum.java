package com.ityb.qugou.base.exception.code.impl;

import com.ityb.qugou.base.exception.code.IErrorEnum;

/**
 * 业务异常编码类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public enum ParamExceptionEnum implements IErrorEnum {
	EMPTY_EXCETION("empty_excetion", "参数不能为空"),
	LENGTH_EXCETION("length_excetion", "参数长度异常"),
	TYPEOF_EXCETION("typeof_excetion", "类型异常"),
	DATE_EXCETION("date_excetion", "日期格式异常");

	private String errorMessage;
	private String errorCode;
	
	ParamExceptionEnum(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	@Override
	public String getErrorMessge() {
		return errorMessage;
	}

	@Override
	public String getErrorCode() {
		return errorCode;
	}
}
