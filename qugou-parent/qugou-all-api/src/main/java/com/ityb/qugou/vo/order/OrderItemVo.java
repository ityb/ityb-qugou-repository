package com.ityb.qugou.vo.order;

import java.io.Serializable;

public class OrderItemVo implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年2月13日
	 */
	private static final long serialVersionUID = 726070974961469110L;
	private String productName;
	private String productNumber;
	private Integer buyNum;
	private String productSpecificationName;
	private String subtotal;
	private String orderId;
	private Double price;//商品单价
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}
	public String getProductSpecificationName() {
		return productSpecificationName;
	}
	public void setProductSpecificationName(String productSpecificationName) {
		this.productSpecificationName = productSpecificationName;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
}
