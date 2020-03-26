package com.ityb.qugou.base.message;

import com.ityb.qugou.base.constant.CommonConstants;

public class JsonResultData<T> {
	private Integer status;
	private String msg;
	private T data;

	public JsonResultData() {

	}

	public JsonResultData(Integer status, String msg, T data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public JsonResultData(Integer status, T data) {
		this.status = status;
		this.msg = "";
		this.data = data;
	}

	public JsonResultData(Integer status) {
		this.status = status;
		this.msg = "";
		this.data = null;
	}

	public static <T> JsonResultData<T> success(String msg, T data) {
		return new JsonResultData<T>(CommonConstants.STATE_SUCEESS, msg, data);
	}

	public static <T> JsonResultData<T> success(T data) {
		return new JsonResultData<>(CommonConstants.STATE_SUCEESS, "", data);
	}

	public static <T> JsonResultData<T> error(String msg, T data) {
		return new JsonResultData<>(CommonConstants.STATE_FAIL, msg, data);
	}

	public static <T> JsonResultData<T> error(String msg) {
		return new JsonResultData<>(CommonConstants.STATE_FAIL, msg, null);

	}

	public static <T> JsonResultData<T> error(Integer status, String msg, T data) {
		return new JsonResultData<>(status, msg, data);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
}
