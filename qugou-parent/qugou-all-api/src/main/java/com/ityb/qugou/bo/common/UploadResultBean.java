package com.ityb.qugou.bo.common;

import java.io.Serializable;

/**
 * 上传文件结果bean
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class UploadResultBean implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年2月9日
	 */
	private static final long serialVersionUID = 5791066333550418685L;
	private Integer code; //上传结果，0表示成功
	private String msg; //上传之后的信息
	private Object data;//上传之后的数据返回
	private String src;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
