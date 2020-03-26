package com.ityb.qugou.dao.info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.enums.FilterSymbolEnum;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.SqlUtils;
import com.ityb.qugou.po.merchant.Shop;

@Repository
public class ShopDao extends BaseDao<Shop> {

	@Autowired
	private BaseMapper baseMapper;

	@Override
	protected BaseMapper getMapper() {
		return baseMapper;
	}

	@Override
	protected Class<?> getModelClass() {
		return Shop.class;
	}

	public List<Shop> queryShopByCity(String city) {
		SqlCondition sqlCondition=new SqlCondition();
		sqlCondition.addFilterItem("shopAddress", FilterSymbolEnum.LIKE, city);
		@SuppressWarnings("unchecked")
		List<Shop> list = (List<Shop>) SqlUtils.executeFind(sqlCondition, baseMapper, Shop.class);
		return list;
	}

}
