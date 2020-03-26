package com.ityb.qugou.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.SqlUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.po.order.OrderDelivery;

@Repository
public class OrderDeliveryDao extends BaseDao<OrderDelivery> {

	@Autowired
	private BaseMapper baseMapper;

	@Override
	protected BaseMapper getMapper() {
		return baseMapper;
	}

	@Override
	protected Class<?> getModelClass() {
		return OrderDelivery.class;
	}

	/**
	 * 获取一个片配送信息
	 * 
	 * @author yangbin
	 * @date 2018年3月30日
	 * @param orderDelivery
	 * @return
	 */
	public OrderDelivery getOrderDeliveryByEntity(OrderDelivery orderDelivery) {
		SqlCondition sqlCondition = new SqlCondition();
		if (StringUtils.isNotBlank(orderDelivery.getOrderId())) {
			sqlCondition.addFilterEqualItem("orderId", orderDelivery.getOrderId());
		}
		if (StringUtils.isNotBlank(orderDelivery.getId())) {
			sqlCondition.addFilterEqualItem("id", orderDelivery.getId());
		}
		@SuppressWarnings("unchecked")
		List<OrderDelivery> list = (List<OrderDelivery>) SqlUtils.executeFind(sqlCondition, baseMapper,
				OrderDelivery.class);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

}
