package com.ityb.qugou.base.enums;

import java.io.Serializable;

public class FilterConnectionEnum extends BaseEnum implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final FilterConnectionEnum LEFT_JOIN = new FilterConnectionEnum("left");
	public static final FilterConnectionEnum RIGHT_JOIN = new FilterConnectionEnum("right");
	public static final FilterConnectionEnum INNER_JOIN = new FilterConnectionEnum("inner");
	public static final FilterConnectionEnum FULL_JOIN = new FilterConnectionEnum("full");

	private FilterConnectionEnum(String name) {
		super(name);
	}

	public static FilterConnectionEnum[] values() {
		return new FilterConnectionEnum[] { LEFT_JOIN, RIGHT_JOIN, INNER_JOIN, FULL_JOIN };
	}
}
