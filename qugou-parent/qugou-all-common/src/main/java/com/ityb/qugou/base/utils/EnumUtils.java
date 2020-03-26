package com.ityb.qugou.base.utils;

import com.ityb.qugou.base.enums.FilterConnectionEnum;

public class EnumUtils {
	public static boolean isConnectionEnum(String type) {
		if (type == null) {
			return false;
		}
		if ("".equals(type)) {
			return false;
		}
		FilterConnectionEnum[] values = FilterConnectionEnum.values();
		for (FilterConnectionEnum value : values) {
			if (value.getName().equalsIgnoreCase(type)) {
				return true;
			}
		}
		return false;
	}
}
