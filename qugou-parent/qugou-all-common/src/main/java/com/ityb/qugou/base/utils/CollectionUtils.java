package com.ityb.qugou.base.utils;

import java.util.Collection;

public class CollectionUtils extends org.springframework.util.CollectionUtils {
	
	/**
	 * 不为空的判断
	 * @author yangbin
	 * @date 2018年2月4日
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection){
		return !isEmpty(collection);
	}
}
