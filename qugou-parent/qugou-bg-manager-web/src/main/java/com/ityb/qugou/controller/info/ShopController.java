package com.ityb.qugou.controller.info;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ityb.qugou.base.controller.BaseController;
import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.po.merchant.Shop;

@Controller
@RequestMapping("/manager/shop")
public class ShopController extends BaseController<Shop>{

	@Override
	protected String getListPage() {
		return null;
	}

	@Override
	protected BaseService<Shop> getService() {
		return null;
	}

	@Override
	public String add() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
