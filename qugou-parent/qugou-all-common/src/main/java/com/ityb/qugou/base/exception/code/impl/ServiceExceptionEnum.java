package com.ityb.qugou.base.exception.code.impl;

import com.ityb.qugou.base.exception.code.IErrorEnum;

/**
 * 业务异常编码类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public enum ServiceExceptionEnum implements IErrorEnum {
	QUERY_EXCETION("query_excetion", "查询异常"),
	INSERT_EXCETION("insert_excetion", "添加异常"),
	UPDATE_EXCETION("update_excetion", "更新异常"),
	DELETE_EXCETION("delete_excetion", "删除异常"),
	SEND_EXCETION("send_excetion", "发送异常");

	private String errorMessage;
	private String errorCode;
	
	ServiceExceptionEnum(String errorCode, String errorMessage) {
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
