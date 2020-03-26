package com.ityb.qugou.vo.order;

import java.io.Serializable;
import java.util.List;

import com.ityb.qugou.bo.order.OrderBean;
import com.ityb.qugou.bo.order.OrderItemBean;

public class OrderShowVo implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年3月29日
	 */
	private static final long serialVersionUID = 4999105461725086415L;
	private OrderBean orderBean;
	private List<OrderItemBean> orderItemBeanList;
	public OrderBean getOrderBean() {
		return orderBean;
	}
	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}
	public List<OrderItemBean> getOrderItemBeanList() {
		return orderItemBeanList;
	}
	public void setOrderItemBeanList(List<OrderItemBean> orderItemBeanList) {
		this.orderItemBeanList = orderItemBeanList;
	}
}
