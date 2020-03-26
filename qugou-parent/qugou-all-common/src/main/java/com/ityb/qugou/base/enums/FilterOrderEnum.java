package com.ityb.qugou.base.enums;

import java.io.Serializable;

public class FilterOrderEnum extends BaseEnum implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final FilterOrderEnum DESC = new FilterOrderEnum("desc");
	public static final FilterOrderEnum ASC = new FilterOrderEnum("asc");

	private FilterOrderEnum(String name) {
		super(name);
	}
}
