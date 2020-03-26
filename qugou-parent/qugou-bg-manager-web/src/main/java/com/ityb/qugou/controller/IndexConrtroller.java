package com.ityb.qugou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ityb.qugou.base.cache.CacheService;
import com.ityb.qugou.base.constant.RedisConstantKey;
import com.ityb.qugou.base.utils.CookieUtils;
import com.ityb.qugou.po.manager.User;

@Controller
@RequestMapping(value = "/manager")
public class IndexConrtroller {

	@Autowired
	private CacheService cacheService;

	/**
	 * 跳转到首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(Model model, HttpServletRequest request,HttpServletResponse response) {
		String token = CookieUtils.getCookieValue(request,RedisConstantKey.ADMIN_SESSION_ID);
		// 从session中取出数据
		User user = (User) cacheService.get(RedisConstantKey.ADMIN_SESSION_ID + "_" + token);
		model.addAttribute("user", user);
		return "index"; 
	}

	@RequestMapping(value = "/welcome")
	public String welcome(HttpServletRequest request) {
		
		return "welcome";
	}
}
