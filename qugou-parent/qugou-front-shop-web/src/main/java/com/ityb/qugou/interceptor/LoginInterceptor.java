package com.ityb.qugou.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.ityb.qugou.base.cache.CacheService;
import com.ityb.qugou.base.constant.RedisConstantKey;
import com.ityb.qugou.base.utils.CookieUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.configuration.PropertiesConfig;

/**
 * 登录拦截器
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private PropertiesConfig propertiesConfig;
	@Autowired
	private CacheService cacheService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception modelAndView) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestUri = request.getRequestURI();
		if(requestUri.endsWith("/shop")||requestUri.endsWith("/shop#")||requestUri.contains("/index")
				||requestUri.startsWith("/shop/search")||
				requestUri.startsWith("/shop/product")||requestUri.startsWith("/shop/error")||
				requestUri.startsWith("/shop/store")||requestUri.startsWith("/shop/evaluation/queryEvaluation")
				||requestUri.startsWith("/shop/about")){
			return true;
		}
		String token=CookieUtils.getCookieValue(request, RedisConstantKey.SHOP_SESSION_ID);
		if(StringUtils.isBlank(token)){
			response.sendRedirect("http://www.qugou.com/sso/shop/login");
			return false;
		}
		String key = RedisConstantKey.SHOP_SESSION_ID + "_" + token;
		if (!cacheService.isKeyExists(key)) {
			response.sendRedirect("http://www.qugou.com/sso/shop/login");
			return false;
		}else{
			cacheService.expire(key, propertiesConfig.getSessionExpireTime());
		}
		return true;
	}

}
