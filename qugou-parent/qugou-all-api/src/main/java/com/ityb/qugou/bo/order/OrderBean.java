package com.ityb.qugou.bo.order;

import java.io.Serializable;
import java.util.Date;

public class OrderBean implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年3月29日
	 */
	private static final long serialVersionUID = -1144825080146110248L;
	private String id;
	private String number;//订单号
	private String merchantId;//商家Id
	private Double total;//对应的订单总额
	private Integer state;//订单状态
	private Integer tradeWay;//交易方式
	private Integer deliveryWay;//配送方式
	private String shopName;//商店名称
	private String shopId;//商店ID
	private Date addTime;
	private String customerName;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Integer getTradeWay() {
		return tradeWay;
	}
	public void setTradeWay(Integer tradeWay) {
		this.tradeWay = tradeWay;
	}
	public Integer getDeliveryWay() {
		return deliveryWay;
	}
	public void setDeliveryWay(Integer deliveryWay) {
		this.deliveryWay = deliveryWay;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
