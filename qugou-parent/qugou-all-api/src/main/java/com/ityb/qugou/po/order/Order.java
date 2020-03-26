package com.ityb.qugou.po.order;

import com.ityb.qugou.base.entity.BaseEntity;

public class Order extends BaseEntity{

	/**
	 * serialVersionUID
	 * @date 2018年2月13日
	 */
	private static final long serialVersionUID = -1144900730357593930L;
	
	private String number;
	private String merchantId;
	private Double total;
	private Integer state;
	private String serialNumber;
	private Integer tradeWay;
	private Integer orderRemark;
	private String merchantRemark;
	private String customerPhone;
	private String mechantRemark;
	private String deliveryAddress;
	private Integer deliveryWay;
	private String customerName;
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
	public String getMechantRemark() {
		return mechantRemark;
	}
	public void setMechantRemark(String mechantRemark) {
		this.mechantRemark = mechantRemark;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Integer getTradeWay() {
		return tradeWay;
	}
	public void setTradeWay(Integer tradeWay) {
		this.tradeWay = tradeWay;
	}
	public Integer getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(Integer orderRemark) {
		this.orderRemark = orderRemark;
	}
	public String getMerchantRemark() {
		return merchantRemark;
	}
	public void setMerchantRemark(String merchantRemark) {
		this.merchantRemark = merchantRemark;
	}
	@Override
	public String getTableName() {
		return "t_p_order";
	}

}
