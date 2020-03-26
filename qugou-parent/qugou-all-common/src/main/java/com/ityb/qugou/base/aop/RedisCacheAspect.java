package com.ityb.qugou.base.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ityb.qugou.base.annotation.RedisCache;
import com.ityb.qugou.base.cache.CacheService;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.exception.code.impl.ParamExceptionEnum;
import com.ityb.qugou.base.utils.AnnotationUtils;
import com.ityb.qugou.base.utils.StringUtils;

@Aspect
@Component
public class RedisCacheAspect {

	@Autowired
	private CacheService cacheService;
	private final static Logger LOGGER = Logger.getLogger(RedisCacheAspect.class);
	// 定义切点，只要是被该注解注入的方法走改切点
	@Pointcut("@annotation(com.ityb.qugou.base.annotation.RedisCache)")
	public void setJoinPoint() {
	}

	// 环绕通知:可以获取返回值
	@Around(value = "setJoinPoint()")
	public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) {
		Object result = null;
		try {
			// 前置通知
			//result = proceedingJoinPoint.proceed();
			// 得到的是切点切入的是哪个方法的名称
			String methodName = proceedingJoinPoint.getSignature().getName();
			// 得到的是改注解是在哪个类中
			Class<?> tagerClazz = proceedingJoinPoint.getTarget().getClass();
			String cacheType = null;
			Object cacheTypeObj = AnnotationUtils.getMethodAnnotationValue(tagerClazz, methodName, RedisCache.class,
					"cacheType");
			if (cacheTypeObj != null) {
				cacheType = cacheTypeObj.toString();
			} else {
				throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(), "缓存类型不能为空");
			}
			String keyName = null;
			Object keyNameObj = AnnotationUtils.getMethodAnnotationValue(tagerClazz, methodName, RedisCache.class,
					"keyName");
			if (keyNameObj != null) {
				keyName = keyNameObj.toString();
			}
			String fieldName = null;
			Object fieldNameObj = AnnotationUtils.getMethodAnnotationValue(tagerClazz, methodName, RedisCache.class,
					"fieldName");
			if (fieldNameObj != null) {
				fieldName = fieldNameObj.toString();
			}
			int expireSeconds = -1;
			Object expireSecondsObj = AnnotationUtils.getMethodAnnotationValue(tagerClazz, methodName, RedisCache.class,
					"expireSeconds");
			if (expireSecondsObj != null) {
				expireSeconds = (int) expireSecondsObj;
			}
			Object[] args = proceedingJoinPoint.getArgs();
			keyName = CacheAspectUtils.analysisDescription(keyName, args);
			switch (cacheType) {
			case "KeyValue":
				if (this.cacheService.isKeyExists(keyName)) {// 判断是否存在
					result = this.cacheService.get(keyName);
				} else {// 缓存不存在，以事务方式来执行切面
					result = proceedingJoinPoint.proceed(args);
					cacheService.put(keyName, result);
					cacheService.expire(keyName, expireSeconds);
				}
				break;
			case "KeyFieldValue":
				if (StringUtils.isBlank(fieldName)) {
					throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(), "hash的域不能为空");
				}
				if (this.cacheService.hashIsKeyExists(fieldName, keyName)) {// 判断是否存在
					result = this.cacheService.hashGet(fieldName, keyName);
				} else {// 缓存不存在，以事务方式来执行切面
					result = proceedingJoinPoint.proceed(args);
					cacheService.hashPut(fieldName, keyName, result, expireSeconds);
				}
			default:
				throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(), "缓存类型错误");
			}

		} catch (Throwable e) {
			// 异常通知
			LOGGER.error(e);
		}
		return result;
	}
}
