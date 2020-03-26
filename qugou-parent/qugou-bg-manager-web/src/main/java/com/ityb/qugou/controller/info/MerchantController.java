package com.ityb.qugou.controller.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ityb.qugou.base.controller.BaseController;
import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.po.merchant.Merchant;
import com.ityb.qugou.service.info.MerchantService;

/**
 * 客户控制器
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/manager/merchant")
public class MerchantController extends BaseController<Merchant>{

	@Autowired
	private MerchantService merchantService;
	
	@Override
	protected String getListPage() {
		return "merchant-list";
	}

	@Override
	protected BaseService<Merchant> getService() {
		return merchantService;
	}

	@Override
	public String add() throws Exception {
		return "merchant-add";
	}
	
}
