package com.ityb.qugou.base.cache;

import java.util.List;
import java.util.Set;

/**
 * 缓存service接口
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface CacheService {

	/**
	 * 添加
	 *
	 * @param key
	 *            key
	 * @param doamin
	 *            对象
	 * @param expire
	 *            过期时间(单位:秒),传入 -1 时表示不设置过期时间
	 */
	public void hashPut(String hashKey, String key, Object value, long expire);

	public void hashPut(String hashKey, String key, Object value);

	/**
	 * 删除
	 *
	 * @param key
	 *            传入key的名称
	 */
	public void hashRemove(String hashKey, String key);

	/**
	 * 查询
	 *
	 * @param key
	 *            查询的key
	 * @return
	 */
	public Object hashGet(String hashKey, String key);

	/**
	 * 获取当前redis库下所有对象
	 *
	 * @return
	 */
	public List<Object> hashGetAll(String hashKey);

	/**
	 * 查询查询当前redis库下所有key
	 *
	 * @return
	 */
	public Set<String> hashGetKeys(String hashKey);

	/**
	 * 判断key是否存在redis中
	 *
	 * @param key
	 *            传入key的名称
	 * @return
	 */
	public boolean hashIsKeyExists(String hashKey, String key);

	/**
	 * 查询当前key下缓存数量
	 *
	 * @return
	 */
	public long hashCount(String hashKey);

	/**
	 * 清空redis
	 */
	public void hashEmpty(String hashKey);

	/**
	 * 向缓存中放入值与对象
	 */
	public void put(String key, Object value);

	/**
	 * 设置key的有效期
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 *            秒
	 */
	public void put(String key, Object value, long expire);

	/**
	 * 获取一个值，通过key
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key);

	public void remove(String key);

	public boolean isKeyExists(String key);

	/**
	 * 设置key的过期时间
	 * 
	 * @author yangbin
	 * @date 2018年1月18日
	 * @param key
	 * @param expire
	 */
	public void expire(String key, long expire);
}
