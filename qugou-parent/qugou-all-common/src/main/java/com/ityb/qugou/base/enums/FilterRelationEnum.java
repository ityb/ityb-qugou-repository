package com.ityb.qugou.base.enums;

import java.io.Serializable;

public class FilterRelationEnum extends BaseEnum implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final FilterRelationEnum AND = new FilterRelationEnum("and");
	public static final FilterRelationEnum OR = new FilterRelationEnum("or");

	private FilterRelationEnum(String name) {
		super(name);
	}

	public static FilterRelationEnum[] values() {
		return new FilterRelationEnum[] { AND, OR };
	}
}
