package com.ityb.qugou.service.info;

import java.util.List;

import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.po.merchant.Shop;

public interface ShopService extends BaseService<Shop>{

	/**
	 * 根据区域获取对应的商店
	 * @author yangbin
	 * @date 2018年4月15日
	 * @param string
	 * @return
	 */
	List<Shop> queryShopByCity(String string);

}
