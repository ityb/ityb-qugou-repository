
package com.ityb.qugou.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.ConcurrentReferenceHashMap;

public class ReflectionUtils extends org.springframework.util.ReflectionUtils {

	private static final Map<Class<?>, Field[]> declaredFieldNamesCache = new ConcurrentReferenceHashMap<Class<?>, Field[]>(
			256);

	public static Object invokeMethod(Class<?> clazz, String methodName, Object target) {
		return invokeMethod(clazz, methodName, target, new Object[0]);
	}

	/**
	 * 用于一个方法的赋值
	 * 
	 * @param clazz
	 *            class对象
	 * @param methodName
	 *            方法名称
	 * @param target
	 *            目标对象，即对那个对象进行调用
	 * @param args
	 *            可变参数
	 * @return
	 */
	public static Object invokeMethod(Class<?> clazz, String methodName, Object target, Object... args) {
		Method[] methods = getAllDeclaredMethods(clazz);
		Method method = null;
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				if (m.getParameterTypes().length == args.length) {
					boolean flag = true;
					for (int i = 0; i < m.getParameterCount(); i++) {
						if (!isAssignableFrom(m.getParameterTypes()[i], args[i].getClass())) {
							flag = false;
							break;
						}
					}
					if (flag) {
						method = m;
						break;
					}
				}
			}
		}

		if (method == null) {
			return null;
		}

		return invokeMethod(method, target, args);
	}

	public static boolean isAssignableFrom(Class<?> clazz1, Class<?> clazz2) {
		boolean result = clazz1.isAssignableFrom(clazz2);

		if (clazz1.equals(int.class) && clazz2.equals(Integer.class)) {
			result = true;
		} else if (clazz1.equals(boolean.class) && clazz2.equals(Boolean.class)) {
			result = true;
		} else if (clazz1.equals(long.class) && clazz2.equals(Long.class)) {
			result = true;
		} else if (clazz1.equals(double.class) && clazz2.equals(Double.class)) {
			result = true;
		} else if (clazz1.equals(byte.class) && clazz2.equals(Byte.class)) {
			result = true;
		}
		return result;
	}

	/**
	 * 得到本类及其父类，祖先类的所有属性
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field[] getAllDeclaredFields(Class<?> clazz) {
		Field[] superFields = null;
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != Object.class) {
			superFields = getAllDeclaredFields(superClazz);
		}

		Field[] result = getDeclaredFields(clazz);
		if (superFields != null) {
			Field[] fields = result;
			result = new Field[fields.length + superFields.length];
			System.arraycopy(superFields, 0, result, 0, superFields.length);
			System.arraycopy(fields, 0, result, superFields.length, fields.length);
		}

		return result;
	}

	/**
	 * 得到本类的所有属性（不包括父类及其祖先类）
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field[] getDeclaredFields(Class<?> clazz) {
		Field[] result = declaredFieldNamesCache.get(clazz);
		if (result == null) {
			result = clazz.getDeclaredFields();
			declaredFieldNamesCache.put(clazz, result);
		}
		return result;
	}

	/**
	 * 设置属性名称
	 * 
	 * @param clazz
	 * @param fieldName
	 * @param target
	 * @param value
	 */
	public static void setFieldValue(Class<?> clazz, String fieldName, Object target, Object value) {
		Field[] fields = getAllDeclaredFields(clazz);
		for (Field f : fields) {
			if (f.getName().equals(fieldName)) {
				makeAccessible(f);
				setField(f, target, value);
			}
		}
	}

	/**
	 * 根据属性名称获取对应的属性值
	 * 
	 * @param target
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Object getFieldValue(Object target, String fieldName) throws Exception {
		Field field = getDeclaredField(target.getClass(), fieldName);
		makeAccessible(field);
		Object result = getField(field, target);
		return result;
	}

	/**
	 * 根据属性名称得到属性的值
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return 一个field对象
	 */
	public static Field getDeclaredField(Class<?> clazz, String fieldName) {
		Field[] fields = getAllDeclaredFields(clazz);

		Field result = null;
		for (Field f : fields) {
			if (f.getName().equals(fieldName)) {
				result = f;
				break;
			}
		}
		return result;
	}

	public static Method getMethodByName(Class<?> clazz, String MethodName) {
		if (StringUtils.isBlank(MethodName)) {
			return null;
		}
		Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
		if (methods == null || methods.length <= 0) {
			return null;
		}
		for (Method method : methods) {
			if (method.getName().equalsIgnoreCase(MethodName)) {
				return method;
			}
		}
		return null;
	}

	/**
	 * 判断一个是否含有此属性
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static boolean isContainField(Class<?> clazz, String fieldName) {
		Field[] fields = getAllDeclaredFields(clazz);
		for (Field f : fields) {
			if (f.getName().equals(fieldName)) {
				return true;
			}
		}
		return false;
	}

	public static List<Field> getAllNotBlanFields(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = getAllDeclaredFields(clazz);
		Object object = null;
		try {
			object = clazz.newInstance();
			for (Field field : fields) {
				Method method = getMethodByName(clazz, "get" + StringUtils.toPreUpperCase(field.getName()));
				object = method.invoke(object);
				if (object != null) {
					list.add(field);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
