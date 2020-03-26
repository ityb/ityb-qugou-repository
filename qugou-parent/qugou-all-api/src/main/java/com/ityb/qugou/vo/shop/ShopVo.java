package com.ityb.qugou.vo.shop;

import java.io.Serializable;

public class ShopVo implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年3月31日
	 */
	private static final long serialVersionUID = 9092476838961471806L;
	private String shopId;
	private String shopName;
	private Double startPrice;
	private Double distance;//距离
	private String shopLogo;
	private String shopFraction;
	public String getShopFraction() {
		return shopFraction;
	}
	public void setShopFraction(String shopFraction) {
		this.shopFraction = shopFraction;
	}
	public String getShopLogo() {
		return shopLogo;
	}
	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Double getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
