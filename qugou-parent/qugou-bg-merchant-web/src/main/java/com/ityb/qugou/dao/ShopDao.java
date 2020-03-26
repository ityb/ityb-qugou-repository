package com.ityb.qugou.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.SqlUtils;
import com.ityb.qugou.po.merchant.Shop;

@Repository
public class ShopDao {
	
	@Autowired
	private BaseMapper baseMapper;

	/**
	 * 获取商店信息
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param shop
	 * @return
	 */
	public Shop getShop(Shop shop) {
		SqlCondition sqlCondition=new SqlCondition();
		sqlCondition.addSelectItem("id");
		sqlCondition.addSelectItem("shopName");
		sqlCondition.addSelectItem("shopAddress");
		sqlCondition.addSelectItem("shopLogo");
		sqlCondition.addSelectItem("startPrice");
		sqlCondition.addSelectItem("shopDesc");
		sqlCondition.addSelectItem("ctime");
		sqlCondition.addFilterEqualItem("userId", shop.getUserId());
		@SuppressWarnings("unchecked")
		List<Shop> list = (List<Shop>) SqlUtils.executeFind(sqlCondition, baseMapper, Shop.class);
		return list.get(0);
	}

	/**
	 * 更新商店信息
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param shop
	 */
	public void update(Shop shop) {
		SqlUtils.executeUpdateById(baseMapper, shop);
	}
}
