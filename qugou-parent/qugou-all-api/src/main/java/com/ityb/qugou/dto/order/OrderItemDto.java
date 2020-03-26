package com.ityb.qugou.dto.order;

import java.io.Serializable;
import java.util.List;

import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.po.order.OrderItem;

public class OrderItemDto implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年3月28日
	 */
	private static final long serialVersionUID = 1858307123171136715L;
	private Order order;
	private List<OrderItem> orderItemList;
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
}
