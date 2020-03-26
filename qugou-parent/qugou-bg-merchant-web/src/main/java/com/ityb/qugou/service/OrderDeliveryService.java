package com.ityb.qugou.service;

import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.po.order.OrderDelivery;

public interface OrderDeliveryService extends BaseService<OrderDelivery>{

	/**
	 * 添加配送信息
	 * @author yangbin
	 * @date 2018年3月30日
	 * @param orderDelivery
	 */
	void addOrderDelivery(OrderDelivery orderDelivery);

	/**
	 * 获取一个订单配送信息
	 * @author yangbin
	 * @date 2018年3月30日
	 * @param orderDelivery
	 * @return
	 */
	OrderDelivery getOrderDeliveryByEntity(OrderDelivery orderDelivery);
	
	/**
	 * 更新订单配送信息
	 * @author yangbin
	 * @date 2018年3月30日
	 * @param orderDelivery
	 */
	void updateOrderDeliveryByEntity(OrderDelivery orderDelivery);

}
