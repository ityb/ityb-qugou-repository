package com.ityb.qugou.base.builder;

import java.io.Serializable;

public class FilterOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String item;
	private String orderType;

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public FilterOrder() {

	}

	public FilterOrder(String item, String orderType) {
		this.item = item;
		this.orderType = orderType;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

}
