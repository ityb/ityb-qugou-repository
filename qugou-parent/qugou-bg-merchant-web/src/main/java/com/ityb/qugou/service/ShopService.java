package com.ityb.qugou.service;

import com.ityb.qugou.po.merchant.Shop;

public interface ShopService {

	/**
	 * 获取商店信息
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param shop
	 * @return
	 */
	Shop getShopInfo(Shop shop);

	/**
	 * 更新商店信息
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param shop
	 */
	void update(Shop shop);

}
