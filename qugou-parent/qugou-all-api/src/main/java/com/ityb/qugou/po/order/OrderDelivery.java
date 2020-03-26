package com.ityb.qugou.po.order;

import com.ityb.qugou.base.entity.BaseEntity;

public class OrderDelivery extends BaseEntity{

	/**
	 * serialVersionUID
	 * @date 2018年3月30日
	 */
	private static final long serialVersionUID = 1L;

	private String orderId;
	private String deliveryName;
	private String deliveryPhone;
	private String deliveryPosition;
	private String deliveryInfo;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}
	public String getDeliveryPosition() {
		return deliveryPosition;
	}
	public void setDeliveryPosition(String deliveryPosition) {
		this.deliveryPosition = deliveryPosition;
	}
	public String getDeliveryInfo() {
		return deliveryInfo;
	}
	public void setDeliveryInfo(String deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}
	@Override
	public String getTableName() {
		return "t_p_order_delivery";
	}

}
