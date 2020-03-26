package com.ityb.qugou.base.builder;

import java.io.Serializable;

public class FilterRelation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Class<?> foreignClazz;
	private Class<?> primaryClazz;
	private String connectionType;
	private String alias;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public FilterRelation() {

	}

	public Class<?> getPrimaryClazz() {
		return primaryClazz;
	}

	public void setPrimaryClazz(Class<?> primaryClazz) {
		this.primaryClazz = primaryClazz;
	}

	public Class<?> getForeignClazz() {
		return foreignClazz;
	}

	public void setForeignClazz(Class<?> foreignClazz) {
		this.foreignClazz = foreignClazz;
	}

	public FilterRelation(Class<?> foreignClazz, Class<?> primaryClazz, String connectionType) {
		this.foreignClazz = foreignClazz;
		this.primaryClazz = primaryClazz;
		this.connectionType = connectionType;
	}

	public FilterRelation(Class<?> foreignClazz, Class<?> primaryClazz, String connectionType, String alias) {
		super();
		this.foreignClazz = foreignClazz;
		this.primaryClazz = primaryClazz;
		this.connectionType = connectionType;
		this.alias = alias;
	}

}
