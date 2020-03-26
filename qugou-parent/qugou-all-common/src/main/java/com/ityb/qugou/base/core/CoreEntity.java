package com.ityb.qugou.base.core;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CoreEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@JsonIgnore
	public abstract String getTableName();

}