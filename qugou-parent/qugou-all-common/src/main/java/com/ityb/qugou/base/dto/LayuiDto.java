package com.ityb.qugou.base.dto;

import java.io.Serializable;
import java.util.List;

public class LayuiDto<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean rel;
	private String msg;
	private List<T> list;

	public Boolean getRel() {
		return rel;
	}

	public void setRel(Boolean rel) {
		this.rel = rel;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
