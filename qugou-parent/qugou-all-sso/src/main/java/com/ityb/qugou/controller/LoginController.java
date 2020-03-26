package com.ityb.qugou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.cache.CacheService;
import com.ityb.qugou.base.constant.RedisConstantKey;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.CookieUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.configuration.PropertiesConfig;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.service.LoginService;

/**
 * 进行认证中心的登录控制器
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping(value = "/sso")
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private PropertiesConfig propertiesConfig;

	private final static Logger LOGGER = Logger.getLogger(LoginController.class);
	/**
	 * 跳转到管理员登录界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/login", method = { RequestMethod.GET })
	public String adminLogin() {
		return "admin/login";
	}

	/**
	 * 跳转到商家平台登录界面
	 * @author yangbin
	 * @date 2018年3月1日
	 * @return
	 */
	@RequestMapping(value = "/merchant/login", method = { RequestMethod.GET })
	public String merchantLogin() {
		return "merchant/login";
	}
	
	/**
	 * 跳转到商城系统登录页面
	 * @author yangbin
	 * @date 2018年3月1日
	 * @return
	 */
	@RequestMapping(value = "/shop/login", method = { RequestMethod.GET })
	public String shopLogin() {
		return "shop/login";
	}

	/**
	 * 管理员端的登录
	 * 
	 * @author yangbin
	 * @date 2017年12月31日
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/login", method = { RequestMethod.POST })
	public String adminDoLogin(User user, Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			User userDB = loginService.doAdminLogin(user);
			// 获得有个设置token
			String token = StringUtils.getRandomStr();
			// 设置缓存
			cacheService.put(RedisConstantKey.ADMIN_SESSION_ID + "_" + token, userDB,propertiesConfig.getSessionExpireTime());
			// 设置cookie
			CookieUtils.setCookie(response, RedisConstantKey.ADMIN_SESSION_ID, token);
			return "redirect:" + propertiesConfig.getAdminMainUrl();
		} catch (Exception e) {
			LOGGER.error("adminDoLogin...登录失败");
			model.addAttribute("message", e.getMessage());
			return "admin/login";
		}
	}

	/**
	 * 进行登录商家用户的登录
	 * 
	 * @author yangbin
	 * @date 2017年12月31日
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/merchant/login", method = { RequestMethod.POST })
	public String merchantDoLogin(User user, Model model, HttpServletResponse response) {
		try {
			User userDB = loginService.doMerchantLogin(user);
			// 获得有个设置token
			String token = StringUtils.getRandomStr();
			// 设置缓存
			cacheService.put(RedisConstantKey.MERCHANT_SESSION_ID + "_" + token, userDB,propertiesConfig.getSessionExpireTime());
			// 设置cookie
			CookieUtils.setCookie(response, RedisConstantKey.MERCHANT_SESSION_ID, token);
			return "redirect:" + propertiesConfig.getMerchantMainUrl();
		} catch (Exception e) {
			LOGGER.error("merchantDoLogin...登录失败");
			model.addAttribute("message", e.getMessage());
			return "merchant/login";
		}
	}
	
	/**
	 * 商城登录，通过ajax进行跳转
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param loginId
	 * @param password
	 * @return 得到的是登录凭证
	 */
	@RequestMapping(value = "/shop/login", method = { RequestMethod.POST })
	@ResponseBody
	public JsonResultData<User> shopDoLogin(HttpServletResponse response,User user){
		try {
			User userEntity=loginService.doShopLogin(user);
			// 获得有个设置token
			String token = StringUtils.getRandomStr();
			// 设置缓存
			cacheService.put(RedisConstantKey.SHOP_SESSION_ID + "_" + token, userEntity,
					propertiesConfig.getSessionExpireTime());
			// 设置cookie
			CookieUtils.setCookie(response, RedisConstantKey.SHOP_SESSION_ID, token);
			return JsonResultData.success(userEntity);
		} catch (Exception e) {
			LOGGER.error("shopDoLogin...登录失败");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 商城退出登录
	 * @author yangbin
	 * @date 2018年3月7日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/shop/loginOut", method = { RequestMethod.GET })
	public String shopLoginOut(HttpServletRequest request,HttpServletResponse response){
		try {
			String token = CookieUtils.getCookieValue(request, RedisConstantKey.SHOP_SESSION_ID);
			cacheService.remove(RedisConstantKey.SHOP_SESSION_ID + "_" + token);
			CookieUtils.deleteCookie(request, response, RedisConstantKey.SHOP_SESSION_ID);
		} catch (Exception e) {
			LOGGER.error("shopLoginOut...退出登录失败");
		}
		return "redirect:" + propertiesConfig.getShopIndexUrl();
	}
	
	/**
	 * 获取token
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/shop/getToken", method = { RequestMethod.GET ,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<String> getToken(String token){
		try {
			if(StringUtils.isBlank(token)){
				return JsonResultData.error("您未进行登录");
			}
			//到redis中取获取
			if(!cacheService.isKeyExists(RedisConstantKey.SHOP_SESSION_ID + "_" + token)){
				return JsonResultData.error("登录过期");
			}
			return JsonResultData.success("已经登录");
		} catch (Exception e) {
			LOGGER.error("getToken...获取token");
			return JsonResultData.error("登录过期");
		}
	}
}
