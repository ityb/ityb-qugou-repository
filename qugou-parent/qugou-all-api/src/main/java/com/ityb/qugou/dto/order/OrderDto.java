package com.ityb.qugou.dto.order;

import java.util.Date;

import com.ityb.qugou.base.dto.PageDto;

public class OrderDto extends PageDto{
	/**
	 * serialVersionUID
	 * @date 2018年2月12日
	 */
	private static final long serialVersionUID = 6954927288710684176L;
	private String orderNumber;//订单编号
	private String userName;//客户名称
	private Integer tradeWay;//交易类型
	private Date addDate;
	private String merchantId;
	private Integer state;
	private String orderId;
	private String userId;//对应的用户ID
	private String customerName;
	private String customerPhone;
	private Integer deliveryWay;
	private Integer isEvaluation;//表示是否评价的订单1表示未评价，2表示已评价
	public Integer getIsEvaluation() {
		return isEvaluation;
	}
	public void setIsEvaluation(Integer isEvaluation) {
		this.isEvaluation = isEvaluation;
	}
	public Integer getDeliveryWay() {
		return deliveryWay;
	}
	public void setDeliveryWay(Integer deliveryWay) {
		this.deliveryWay = deliveryWay;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Integer getTradeWay() {
		return tradeWay;
	}
	public void setTradeWay(Integer tradeWay) {
		this.tradeWay = tradeWay;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
