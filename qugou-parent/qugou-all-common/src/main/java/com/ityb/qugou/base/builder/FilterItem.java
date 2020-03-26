package com.ityb.qugou.base.builder;

import java.io.Serializable;

public class FilterItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String item;
	private String symbol;
	private Object value;

	public FilterItem() {

	}

	public FilterItem(String item, String symbol, Object value) {
		this.item = item;
		this.symbol = symbol;
		this.value = value;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
