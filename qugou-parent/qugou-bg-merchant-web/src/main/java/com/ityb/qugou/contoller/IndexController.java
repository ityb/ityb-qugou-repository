package com.ityb.qugou.contoller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ityb.qugou.base.cache.CacheService;
import com.ityb.qugou.base.constant.RedisConstantKey;
import com.ityb.qugou.base.utils.CookieUtils;
import com.ityb.qugou.configuration.PropertiesConfig;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.service.ShopService;

@Controller
@RequestMapping(value = "/merchant")
public class IndexController {
	@Autowired
	private CacheService cacheService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private PropertiesConfig propertiesConfig;

	/**
	 * 跳转到首页
	 */
	@RequestMapping(value = "/index",method={RequestMethod.GET,RequestMethod.POST})
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		String token = CookieUtils.getCookieValue(request, RedisConstantKey.MERCHANT_SESSION_ID);
		// 从session中取出数据
		User user = (User) cacheService.get(RedisConstantKey.MERCHANT_SESSION_ID + "_" + token);
		CookieUtils.setCookie(request, response, RedisConstantKey.MERCHANT_SESSION_ID, token);
		Shop shop=new Shop();
		shop.setUserId(user.getId());
		Shop shopEntity=shopService.getShopInfo(shop);
		model.addAttribute("user", user);
		model.addAttribute("shop", shopEntity);
		return "index";
	}
	
	/**
	 * 注销
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginout",method={RequestMethod.GET,RequestMethod.POST})
	public String loginout(Model model, HttpServletRequest request, HttpServletResponse response) {
		String token = CookieUtils.getCookieValue(request, RedisConstantKey.MERCHANT_SESSION_ID);
		// 从session中取出数据
		cacheService.remove(RedisConstantKey.MERCHANT_SESSION_ID + "_" + token);
		return "redirect:" + propertiesConfig.getMerchantLoginUrl();
	}
	
	/**
	 * 跳转到首页
	 * @author yangbin
	 * @date 2018年5月15日
	 * @return
	 */
	@RequestMapping(value="/main",method={RequestMethod.GET,RequestMethod.POST})
	public String main(){
		return "main";
	}
}
