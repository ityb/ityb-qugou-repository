package com.ityb.qugou.base.builder;

public class SelectItem {
	private String item;
	private String alias;

	public SelectItem() {

	}

	public SelectItem(String item) {
		this.item = item;
	}

	public SelectItem(String item, String alias) {
		this.item = item;
		this.alias = alias;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
