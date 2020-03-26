package com.ityb.qugou.base.exception;

/**
 * 自定义异常类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class ServiceException extends RuntimeException {
	/**
	 * 异常编码
	 */
	private String errorCode;
	/**
	 * 异常信息
	 */
	private String errorMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月13日
	 */
	private static final long serialVersionUID = -3625424499338092640L;

	public ServiceException() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param message
	 */
	public ServiceException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public ServiceException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ServiceException(String errorMessage, Throwable e) {
		super(errorMessage, e);
		this.errorMessage = errorMessage;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
}
