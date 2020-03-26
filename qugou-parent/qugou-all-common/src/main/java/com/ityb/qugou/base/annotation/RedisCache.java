package com.ityb.qugou.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ityb.qugou.base.enums.CacheType;

/**
 * 自定义缓存切面
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface RedisCache{
	//缓存类型
	public abstract CacheType cacheType();
	
	//缓存的名称
	public abstract String keyName();
	
	//hash 域的名称
	public abstract String fieldName() default "";
	
	//过期秒数
	public abstract int expireSeconds() default -1;
}
