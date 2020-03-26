package com.ityb.qugou.base.builder;

public class UpdateItem {
	private String updateItem;
	private Object value;

	public String getUpdateItem() {
		return updateItem;
	}

	public void setUpdateItem(String updateItem) {
		this.updateItem = updateItem;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public UpdateItem() {

	}

	public UpdateItem(String updateItem, Object value) {
		this.updateItem = updateItem;
		this.value = value;
	}
}
