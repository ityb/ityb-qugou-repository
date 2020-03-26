package com.ityb.qugou.base.cache.impl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.cache.CacheService;

/**
 * 缓存service实现类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class CacheServiceImpl implements CacheService{
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	@Autowired
	protected HashOperations<String, String, Object> hashOperations;
	@Autowired
	ValueOperations<String, Object> valueOperations;

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
	@Override
	public void hashPut(String hashKey, String key, Object value, long expire) {
		hashOperations.put(hashKey, key, value);
		if (expire >= 0) {
			redisTemplate.expire(hashKey, expire, TimeUnit.SECONDS);
		} else {
			throw new RuntimeException();
		}
	}
	
	@Override
	public void hashPut(String hashKey, String key, Object value) {
		hashOperations.put(hashKey, key, key);
	}

	/**
	 * 删除
	 *
	 * @param key
	 *            传入key的名称
	 */
	@Override
	public void hashRemove(String hashKey, String key) {
		hashOperations.delete(hashKey, key);
	}

	/**
	 * 查询
	 *
	 * @param key
	 *            查询的key
	 * @return
	 */
	@Override
	public Object hashGet(String hashKey, String key) {
		return hashOperations.get(hashKey, key);
	}

	/**
	 * 获取当前redis库下所有对象
	 *
	 * @return
	 */
	@Override
	public List<Object> hashGetAll(String hashKey) {
		return hashOperations.values(hashKey);
	}

	/**
	 * 查询查询当前redis库下所有key
	 *
	 * @return
	 */
	public Set<String> hashGetKeys(String hashKey) {
		return hashOperations.keys(hashKey);
	}

	/**
	 * 判断key是否存在redis中
	 *
	 * @param key
	 *            传入key的名称
	 * @return
	 */
	@Override
	public boolean hashIsKeyExists(String hashKey, String key) {
		return hashOperations.hasKey(hashKey, key);
	}

	/**
	 * 查询当前key下缓存数量
	 *
	 * @return
	 */
	@Override
	public long hashCount(String hashKey) {
		return hashOperations.size(hashKey);
	}

	/**
	 * 清空redis
	 */
	@Override
	public void hashEmpty(String hashKey) {
		Set<String> set = hashOperations.keys(hashKey);
		for (String key : set) {
			hashOperations.delete(hashKey, key);
		}
	}

	/**
	 * 向缓存中放入值与对象
	 */
	@Override
	public void put(String key, Object value) {
		valueOperations.set(key, value);
	}

	/**
	 * 设置key的有效期
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 *            秒
	 */
	@Override
	public void put(String key, Object value, long expire) {
		valueOperations.set(key, value, expire, TimeUnit.SECONDS);
	}

	/**
	 * 获取一个值，通过key
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public Object get(String key) {
		Object object = valueOperations.get(key);
		return object;
	}
	
	@Override
	public void remove(String key) {
		redisTemplate.delete(key);

	}
	
	@Override
	public boolean isKeyExists(String key) {
		return redisTemplate.hasKey(key);
	}
	
	/**
	 * 设置key的过期时间
	 * @author yangbin
	 * @date 2018年1月18日
	 * @param key
	 * @param expire
	 */
	@Override
	public  void expire(String key,long expire){
		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}
}
