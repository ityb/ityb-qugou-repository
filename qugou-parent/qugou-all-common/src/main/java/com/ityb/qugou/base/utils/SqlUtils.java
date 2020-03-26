package com.ityb.qugou.base.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ityb.qugou.base.builder.MapParams;
import com.ityb.qugou.base.builder.SqlBuilder;
import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.mapper.BaseMapper;

public class SqlUtils {

	/**
	 * 执行查询操作
	 * 
	 * @param sqlCondition
	 * @param baseMapper
	 * @param clazz
	 * @return
	 */
	public static List<?> executeFind(SqlCondition sqlCondition, BaseMapper baseMapper, Class<?>... clazzs) {
		MapParams params = null;
		List<?> results = new CopyOnWriteArrayList<Object>();
		Class<?> clazz = null;
		Class<?> paramClazz = null;
		if (clazzs.length == 1) {
			clazz = clazzs[0];
			paramClazz = clazz;
		} else if (clazzs.length == 2) {
			clazz = clazzs[0];
			paramClazz = clazzs[1];
		} else {
			throw new RuntimeException("参数个数不匹配");
		}
		try {
			params = SqlBuilder.buildFindSQL(clazz, sqlCondition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> list = baseMapper.find(params);
		try {
			results = (List<?>) MapUtils.map2PojoList(list, paramClazz);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return results;
	}

	public static List<Map<String, Object>> executeFind2Map(SqlCondition sqlCondition, BaseMapper baseMapper,
			Class<?> clazz) {
		MapParams params = null;
		try {
			params = SqlBuilder.buildFindSQL(clazz, sqlCondition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> list = baseMapper.find(params);
		List<Map<String, Object>> results = new CopyOnWriteArrayList<Map<String, Object>>();
		if (!CollectionUtils.isEmpty(list)) {
			for (Map<String, Object> map : list) {
				Map<String, Object> temp = new java.util.concurrent.ConcurrentHashMap<String, Object>();
				Set<String> keySet = map.keySet();
				for (String key : keySet) {
					Object value = map.get(key);
					if (key.contains(".")) {
						key = key.substring(key.lastIndexOf(".") + 1, key.length());
					}
					temp.put(key, value);
				}
				results.add(temp);
			}
		}
		return results;
	}

	/**
	 * 执行插入操作
	 * 
	 * @param baseMapper
	 * @param obj
	 * @return
	 */
	public static int executeInsert(BaseMapper baseMapper, Object obj) {
		int count = 0;
		try {
			MapParams params = SqlBuilder.buildInsertSQL(obj);
			count = baseMapper.insert(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 执行更新操作
	 * 
	 * @param baseMapper
	 * @param obj
	 * @return
	 */
	public static int executeUpdateById(BaseMapper baseMapper, Object obj) {
		int count = 0;
		try {
			MapParams params = SqlBuilder.buildUpdateByIdSQL(obj);
			count = baseMapper.updateById(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public static int executeUpdateById(SqlCondition sqlCondition, BaseMapper baseMapper, Class<?> clazz) {
		int count = 0;
		try {
			MapParams params = SqlBuilder.buildUpdateByIdSQL(clazz, sqlCondition);
			count = baseMapper.updateById(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 删除一条记录
	 * 
	 * @param sqlCondition
	 * @param baseMapper
	 * @param clazz
	 * @return
	 */
	public static int executeDeleteById(SqlCondition sqlCondition, BaseMapper baseMapper, Class<?> clazz) {
		int count = 0;
		try {
			MapParams params = SqlBuilder.buildDeleteByIdSQL(clazz, sqlCondition);
			count = baseMapper.deleteById(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 根据条件进行筛选删除
	 * 
	 * @param sqlCondition
	 * @param baseMapper
	 * @param clazz
	 * @return
	 */
	public static int executeDelete(SqlCondition sqlCondition, BaseMapper baseMapper, Class<?> clazz) {
		int count = 0;
		try {
			MapParams params = SqlBuilder.buildDeleteByIdSQL(clazz, sqlCondition);
			count = baseMapper.deleteById(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 执行计算
	 * 
	 * @param sqlCondition
	 * @param mapper
	 * @param clazz
	 * @return
	 */
	public static int executCount(SqlCondition sqlCondition, BaseMapper baseMapper, Class<?> clazz) {
		int count = 0;
		try {
			MapParams params = SqlBuilder.buildCountSQL(clazz, sqlCondition);
			count = baseMapper.count(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
