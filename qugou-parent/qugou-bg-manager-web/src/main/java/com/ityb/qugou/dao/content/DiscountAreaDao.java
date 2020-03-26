package com.ityb.qugou.dao.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.SqlUtils;
import com.ityb.qugou.po.manager.DiscountArea;

@Repository
public class DiscountAreaDao extends BaseDao<DiscountArea>{

	@Autowired
	private BaseMapper baseMapper;

	public List<DiscountArea> query(DiscountArea discountArea) {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSelectItem("discountArea.id");
		sqlCondition.addSelectItem("discountArea.parentId");
		sqlCondition.addSelectItem("discountArea.name");
		sqlCondition.addSelectItem("discountArea.showPic");
		sqlCondition.addSelectItem("discountArea.linkUrl");
		sqlCondition.addRelationItem(DiscountArea.class, DiscountArea.class,"discountAreaParent");
		@SuppressWarnings("unchecked")
		List<DiscountArea> list = (List<DiscountArea>) SqlUtils.executeFind(sqlCondition, baseMapper,
				DiscountArea.class);
		return list;
	}

	@Override
	protected BaseMapper getMapper() {
		return baseMapper;
	}

	@Override
	protected Class<?> getModelClass() {
		return DiscountArea.class;
	}
	
}
