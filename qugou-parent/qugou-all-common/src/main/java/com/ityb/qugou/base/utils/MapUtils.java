package com.ityb.qugou.base.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapUtils{

	public static List<?> map2PojoList(List<Map<String, Object>> mapList, Class<?> clazz)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (CollectionUtils.isEmpty(mapList)) {
			return null;
		}
		List<Object> list = new CopyOnWriteArrayList<Object>();
		if (mapList == null || mapList.size() <= 0) {
			return list;
		}
		for (Map<String, Object> map : mapList) {
			Set<String> keySet = map.keySet();
			if (keySet == null || keySet.size() <= 0) {
				return list;
			}
			Object object = clazz.newInstance();
			for (String key : keySet) {
				if (key.contains(".")) {
					key = key.substring(key.lastIndexOf(".") + 1, key.length());
				}
				Method method = ReflectionUtils.getMethodByName(clazz, "set" + StringUtils.toPreUpperCase(key));
				method.invoke(object, map.get(key));
			}
			list.add(object);

		}
		return list;
	}

	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null) {
			return true;
		}
		if (map.size() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}
}
