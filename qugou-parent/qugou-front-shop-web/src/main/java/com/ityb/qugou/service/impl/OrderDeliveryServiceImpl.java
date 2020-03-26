package com.ityb.qugou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.mapper.OrderDeliveryMapper;
import com.ityb.qugou.po.order.OrderDelivery;
import com.ityb.qugou.service.OrderDeliveryService;

/**
 * 订单配送service实现类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService{

	@Autowired
	private OrderDeliveryMapper orderDeliveryMapper;
	/**
	 * 获取订单详细信息
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param orderDelivery
	 * @return
	 */
	@Override
	public OrderDelivery getOrderDeliveryInfoByEntity(OrderDelivery orderDelivery) {
		Assert.hasText(orderDelivery.getOrderId(),"订单Id不能为空");
		return orderDeliveryMapper.getOrderDeliveryInfoByEntity(orderDelivery);
	}

}
