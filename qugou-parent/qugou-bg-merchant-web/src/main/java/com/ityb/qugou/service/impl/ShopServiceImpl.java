package com.ityb.qugou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.dao.ShopDao;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService{

	@Autowired
	private ShopDao shopDao;
	
	/**
	 * 获取商店信息
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param shop
	 * @return
	 */
	@Override
	public Shop getShopInfo(Shop shop) {
		Assert.hasText(shop.getUserId(),"商店所属人不能为空");
		return shopDao.getShop(shop);
	}

	/**
	 * 更新商店信息
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param shop
	 */
	@Override
	public void update(Shop shop) {
		Assert.hasLength(shop.getId(), "商店ID不能为空");
		this.shopDao.update(shop);
	}

}
