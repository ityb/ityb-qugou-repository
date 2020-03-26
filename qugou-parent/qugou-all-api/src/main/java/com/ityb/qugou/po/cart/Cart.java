package com.ityb.qugou.po.cart;

import com.ityb.qugou.base.entity.BaseEntity;

public class Cart extends BaseEntity{

	/**
	 * serialVersionUID
	 * @date 2018年3月21日
	 */
	private static final long serialVersionUID = -7941024310264178307L;
	private String specificationId;
	private Integer buyNumber;
	private Integer cartType;
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSpecificationId() {
		return specificationId;
	}
	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}
	public Integer getBuyNumber() {
		return buyNumber;
	}
	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}
	public Integer getCartType() {
		return cartType;
	}
	public void setCartType(Integer cartType) {
		this.cartType = cartType;
	}
	@Override
	public String getTableName() {
		return "t_p_cart";
	}

}
