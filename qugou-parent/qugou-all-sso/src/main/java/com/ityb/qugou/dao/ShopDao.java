package com.ityb.qugou.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.SqlUtils;
import com.ityb.qugou.po.merchant.Shop;

@Repository
public class ShopDao {
	@Autowired
	private BaseMapper baseMapper;

	public void insertShop(Shop shop) {
		SqlUtils.executeInsert(baseMapper, shop);
	}
}
