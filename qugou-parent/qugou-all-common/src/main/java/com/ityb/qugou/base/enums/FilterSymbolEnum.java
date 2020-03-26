package com.ityb.qugou.base.enums;

import java.io.Serializable;

public class FilterSymbolEnum extends BaseEnum implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final FilterSymbolEnum EQUAL = new FilterSymbolEnum("=");
	public static final FilterSymbolEnum NOT_EQUAL = new FilterSymbolEnum("<>");
	public static final FilterSymbolEnum GREATER = new FilterSymbolEnum(">");
	public static final FilterSymbolEnum LESS = new FilterSymbolEnum("<");
	public static final FilterSymbolEnum GREATER_EQUAL = new FilterSymbolEnum(">=");
	public static final FilterSymbolEnum LESS_EQUAL = new FilterSymbolEnum("<=");
	public static final FilterSymbolEnum LIKE = new FilterSymbolEnum("%like%");
	public static final FilterSymbolEnum LEFT_LIKE = new FilterSymbolEnum("%like");
	public static final FilterSymbolEnum RIGHT_LIKE = new FilterSymbolEnum("like%");
	public static final FilterSymbolEnum NOT_LEFT_LIKE = new FilterSymbolEnum("not %like");
	public static final FilterSymbolEnum NOT_RIGHT_LIKE = new FilterSymbolEnum("not like%");
	public static final FilterSymbolEnum NOT_LIKE = new FilterSymbolEnum("%not like%");
	public static final FilterSymbolEnum IN = new FilterSymbolEnum("in");
	public static final FilterSymbolEnum NOT_IN = new FilterSymbolEnum("not in");
	public static final FilterSymbolEnum IS_NULL = new FilterSymbolEnum(" is null ");
	public static final FilterSymbolEnum IS_NOT_NULL = new FilterSymbolEnum(" is not null ");

	private FilterSymbolEnum(String name) {
		super(name);
	}

	public static FilterSymbolEnum[] values() {
		return new FilterSymbolEnum[] { EQUAL, NOT_EQUAL, GREATER, LESS, GREATER_EQUAL, LESS_EQUAL, LIKE, NOT_LIKE,
				LEFT_LIKE, NOT_LEFT_LIKE, RIGHT_LIKE, NOT_RIGHT_LIKE, IN, NOT_IN, IS_NULL, IS_NOT_NULL };
	}
}