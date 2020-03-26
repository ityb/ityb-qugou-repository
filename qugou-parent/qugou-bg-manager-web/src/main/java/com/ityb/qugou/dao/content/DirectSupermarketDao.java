package com.ityb.qugou.dao.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.SqlUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.dto.manager.DirectSupermarketDto;
import com.ityb.qugou.po.manager.DirectSupermarket;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.vo.manager.DirectSupermarketVo;

@Repository
public class DirectSupermarketDao extends BaseDao<DirectSupermarket> {

	@Autowired
	private BaseMapper baseMapper;

	@Override
	protected BaseMapper getMapper() {
		return baseMapper;
	}

	@Override
	protected Class<?> getModelClass() {
		return DirectSupermarket.class;
	}

	/**
	 * 获取直通超市列表
	 * @author yangbin
	 * @date 2018年4月15日
	 * @param directSupermarketDto
	 * @return
	 */
	public List<DirectSupermarketVo> queryDirectSupermarket(DirectSupermarketDto directSupermarketDto) {
		SqlCondition sqlCondition=new SqlCondition();
		sqlCondition.addSelectItem("directSupermarket.id","directSupermarketId");
		sqlCondition.addSelectItem("shop.shopName","shopName");
		sqlCondition.addSelectItem("shop.shopAddress", "shopAddress");
		sqlCondition.addSelectItem("shop.id", "shopId");
		sqlCondition.addSelectItem("directSupermarket.ctime", "addTime");
		if(StringUtils.isNotBlank(directSupermarketDto.getCity())){
			sqlCondition.addFilterEqualItem("city", directSupermarketDto.getCity());
		}
		sqlCondition.addRelationItem(DirectSupermarket.class, Shop.class);
		@SuppressWarnings("unchecked")
		List<DirectSupermarketVo> list = (List<DirectSupermarketVo>) SqlUtils.executeFind(sqlCondition, baseMapper, DirectSupermarket.class,DirectSupermarketVo.class);
		return list;
	}

}
