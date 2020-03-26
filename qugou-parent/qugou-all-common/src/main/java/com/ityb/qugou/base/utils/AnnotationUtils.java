package com.ityb.qugou.base.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUtils extends org.springframework.core.annotation.AnnotationUtils {

	/**
	 * 获取该注解上每一个方法的值
	 * @param tagerClazz
	 *            目类
	 * @param methodName
	 *            切入点的方法名称
	 * @param annotationClazz
	 *            改方法上的注解类
	 * @param annotationPropertyName
	 *            改注解的属性值
	 * @return
	 */
	public static Object getMethodAnnotationValue(Class<?> tagerClazz, String methodName,
			Class<? extends Annotation> annotationClazz, String annotationPropertyName) {
		try {
			for (Method m : tagerClazz.getMethods()) {
				if (m.getName().equals(methodName)) {
					for (Annotation annotation : m.getDeclaredAnnotations()) {
						if (annotation.annotationType() == annotationClazz) {

							Method mtd = annotation.getClass().getDeclaredMethod(annotationPropertyName, new Class[0]);
							return mtd.invoke(annotation, new Object[0]);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
