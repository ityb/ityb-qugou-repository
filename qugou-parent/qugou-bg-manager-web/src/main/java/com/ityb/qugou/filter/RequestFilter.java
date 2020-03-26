package com.ityb.qugou.filter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ityb.qugou.base.cache.CacheService;
import com.ityb.qugou.base.constant.RedisConstantKey;
import com.ityb.qugou.base.utils.CookieUtils;
import com.ityb.qugou.po.manager.User;

/**
 * 请求过滤器（主要用于add，update,delete方法拼添加，删除，修改人）
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Component
public class RequestFilter implements Filter{
	@Autowired
	private CacheService cacheService;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
	}
	
	@Override
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException,ServletException{
		Map<String,Object> extraParams=new ConcurrentHashMap<>();
		HttpServletRequest  httpServletRequest=(HttpServletRequest)request;
		String requestURI = httpServletRequest.getRequestURI();
		String token = CookieUtils.getCookieValue(httpServletRequest, RedisConstantKey.ADMIN_SESSION_ID);
		User user=(User) cacheService.get(RedisConstantKey.ADMIN_SESSION_ID + "_" + token);
		if(requestURI.endsWith("/save")||requestURI.endsWith("/saveDtoFile")){//表示为添加方法,需要拼接字符串
			if(user!=null){
				extraParams.put("creater", user.getId());
			}
		}
		if(requestURI.endsWith("/save-update")||requestURI.endsWith("/save-update-file")){//表示为添加方法,需要拼接字符串
			if(user!=null){
				extraParams.put("updater", user.getId());
			}
		}
		if(requestURI.endsWith("/delete")||requestURI.endsWith("/deleteCycle")){//表示为添加方法,需要拼接字符串
			if(user!=null){
				extraParams.put("deleter", user.getId());
			}
		}
		/**
		 * 这里做业务处理
		 */
		RequestParameterWrapper requestParameterWrapper=new RequestParameterWrapper((HttpServletRequest)request);
		requestParameterWrapper.addParameters(extraParams);
		chain.doFilter(requestParameterWrapper,response);
	}
	@Override
	public void destroy(){
		
	}
}