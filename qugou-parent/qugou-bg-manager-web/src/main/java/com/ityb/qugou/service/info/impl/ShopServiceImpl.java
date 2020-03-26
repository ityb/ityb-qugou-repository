package com.ityb.qugou.service.info.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.dao.info.ShopDao;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.service.info.ShopService;

@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements ShopService {

	@Autowired
	private ShopDao shopDao;

	@Override
	public List<Shop> getListData() {
		return null;
	}

	@Override
	protected BaseDao<Shop> getDao() {
		return shopDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return Shop.class;
	}

	@Override
	public List<Shop> queryShopByCity(String city) {
		Assert.hasText(city, "查询的区域不能为空");
		return this.shopDao.queryShopByCity(city);
	}

}
