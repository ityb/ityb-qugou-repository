package com.ityb.qugou.common;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.ityb.qugou.base.cache.CacheService;
import com.ityb.qugou.base.constant.RedisConstantKey;
import com.ityb.qugou.base.utils.CookieUtils;
import com.ityb.qugou.po.manager.User;

@Controller
public class MerchantBaseController {
	@Autowired
	private CacheService cacheService;

	/**
	 * 得到用户的信息
	 * 
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @return
	 */
	public User getUser(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, RedisConstantKey.MERCHANT_SESSION_ID);
		User user = (User) cacheService.get(RedisConstantKey.MERCHANT_SESSION_ID+"_"+cookieValue);
		return user;
	}

	/**
	 * 得到当前操作的用户的id
	 * 
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @return
	 */
	public String getCurrentUserId(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, RedisConstantKey.MERCHANT_SESSION_ID);
		User user = (User) cacheService.get(RedisConstantKey.MERCHANT_SESSION_ID+"_"+cookieValue);
		return user == null ? null : user.getId();
	}
}
