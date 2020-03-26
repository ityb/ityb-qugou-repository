package com.ityb.qugou.base.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json工具类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class JsonUtils {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 对象转json
	 * 
	 * @author yangbin
	 * @date 2018年1月29日
	 * @param data
	 * @return
	 */
	public static String objectToJson(Object data) {
		try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 集合转对象
	 * 
	 * @author yangbin
	 * @date 2018年1月29日
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
		try {
			T t = MAPPER.readValue(jsonData, beanType);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json转集合
	 * 
	 * @author yangbin
	 * @date 2018年1月29日
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = MAPPER.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 使用fastJson转为字符串
	 * 
	 * @author yangbin
	 * @date 2018年1月29日
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object parseObjectOrArray(String json, Class<?> clazz) {
		Object o = JSON.parse(json);
		if (o instanceof JSONArray) {
			o = JSONObject.parseArray(json, clazz);
		} else {
			JSONObject.parseObject(json, clazz);
		}
		return o;
	}
}
