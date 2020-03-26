package com.ityb.qugou.vo.cart;

import java.io.Serializable;
import java.util.List;

public class CartOrderCheckVo implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年3月27日
	 */
	private static final long serialVersionUID = -7120948275276007716L;
	private String shopName; //商定名称
	private String shopId;//商定ID
	private String merchantId;
	private Double subTotal;//小计
	private Double startPrice;//起步价
	private Integer totalNumber;
	private Double totalWeight;
	private Integer cartOrderType;//表示是否满足起步价
	private List<CartOrderProductVo> orderProductVoList; //商品列表
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getCartOrderType() {
		return cartOrderType;
	}
	public void setCartOrderType(Integer cartOrderType) {
		this.cartOrderType = cartOrderType;
	}
	
	public Integer getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
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
	public Double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	public Double getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}
	public List<CartOrderProductVo> getOrderProductVoList() {
		return orderProductVoList;
	}
	public void setOrderProductVoList(List<CartOrderProductVo> orderProductVoList) {
		this.orderProductVoList = orderProductVoList;
	}
}
