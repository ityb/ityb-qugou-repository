package com.ityb.qugou.controller.info;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ityb.qugou.base.controller.BaseController;
import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.po.merchant.Merchant;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.service.info.MerchantService;
import com.ityb.qugou.service.info.ShopService;
import com.ityb.qugou.service.info.UserService;

/**
 * 客户控制器
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/manager/user")
public class UserController extends BaseController<User> {

	@Autowired
	private UserService userService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private MerchantService merchantService;

	@Override
	protected String getListPage() {
		return "user-list";
	}

	/**
	 * 得到商家信息
	 * @author yangbin
	 * @date 2018年4月14日
	 * @return
	 */
	@RequestMapping(value="/getMerchantInfo",method={RequestMethod.GET,RequestMethod.POST})
	public String getMerchantInfo(Model model, @RequestParam Map<String, Object> params) {
		params.forEach((key,value)->{
			model.addAttribute(key,value);
		});
		params.remove("type");
		List<Merchant> merchantList = merchantService.query(params);
		model.addAttribute("merchant", merchantList.get(0));
		List<Shop> shopList = shopService.query(params);
		model.addAttribute("shop", shopList.get(0));
		return "merchant-info";
	}

	@Override
	protected BaseService<User> getService() {
		return userService;
	}

	@Override
	public String add() throws Exception {
		return "customer-add";
	}

}
