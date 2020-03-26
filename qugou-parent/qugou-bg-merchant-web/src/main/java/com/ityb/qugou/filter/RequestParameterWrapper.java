package com.ityb.qugou.filter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.collections.MapUtils;
/**
 * 重写类，满足支持从pojo中添加额外的参数
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class RequestParameterWrapper extends HttpServletRequestWrapper {
	private Map<String, String[]> params = new ConcurrentHashMap<String, String[]>();

	public RequestParameterWrapper(HttpServletRequest request) {
		super(request);
		this.params.putAll(request.getParameterMap());
	}

	public RequestParameterWrapper(HttpServletRequest request, Map<String, Object> extraParams) {
		super(request);
		addParameters(extraParams);
	}

	@Override
	public String getParameter(String name) {
		String[] values = params.get(name);
		if (values == null || values.length == 0) {
			return null;
		}
		return values[0];
	}

	/**
	 * 该类主要是用于将额外的参数封装到实体类中
	 * 如果不重写该方法，则只能讲实体类中的属性，分类写在参数列表中
	 */
	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(params.keySet());
	}

	@Override
	public String[] getParameterValues(String name) {
		return params.get(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return params;
	}

	public void addParameters(Map<String, Object> extraParams) {
		if (MapUtils.isNotEmpty(extraParams)) {
			extraParams.forEach((key, value) -> {
				addParameter(key, value);
			});
		}
	}

	public void addParameter(String key, Object value) {
		if (value != null) {
			if (value instanceof String[]) {
				params.put(key, (String[]) value);
			} else if (value instanceof String) {
				params.put(key, new String[] { (String) value });
			} else {
				params.put(key, new String[] { String.valueOf(value) });
			}
		}
	}
}
