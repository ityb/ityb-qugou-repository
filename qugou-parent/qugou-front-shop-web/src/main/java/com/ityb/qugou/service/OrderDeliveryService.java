package com.ityb.qugou.service;

import com.ityb.qugou.po.order.OrderDelivery;

/**
 * 订单配送接口
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface OrderDeliveryService {

	/**
	 * 获取订单详细信息
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param orderDelivery
	 * @return
	 */
	OrderDelivery getOrderDeliveryInfoByEntity(OrderDelivery orderDelivery);

}
