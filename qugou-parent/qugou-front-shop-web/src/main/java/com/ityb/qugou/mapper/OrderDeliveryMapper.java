package com.ityb.qugou.mapper;

import com.ityb.qugou.po.order.OrderDelivery;

/**
 * 订单配送mapper,对应表为t_p_order_delivery
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface OrderDeliveryMapper {

	/**
	 * 获取订单详细信息
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param orderDelivery
	 * @return
	 */
	OrderDelivery getOrderDeliveryInfoByEntity(OrderDelivery orderDelivery);

}
