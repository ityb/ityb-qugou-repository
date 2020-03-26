package com.ityb.qugou.vo.cart;

import java.io.Serializable;

/**
 * 订单商品显示vo
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class CartOrderProductVo implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年3月27日
	 */
	private static final long serialVersionUID = -6140565135002137600L;
	private String productTitle;
	private String sellPoint;
	private String productSpecificationName;
	private String productSpecificationId;
	private Double weight;
	private Double price;
	private Integer stock;
	private Integer buyNumber;
	private String productPicUrl;
	private String productId;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public String getProductSpecificationName() {
		return productSpecificationName;
	}
	public void setProductSpecificationName(String productSpecificationName) {
		this.productSpecificationName = productSpecificationName;
	}
	public String getProductSpecificationId() {
		return productSpecificationId;
	}
	public void setProductSpecificationId(String productSpecificationId) {
		this.productSpecificationId = productSpecificationId;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getBuyNumber() {
		return buyNumber;
	}
	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}
	public String getProductPicUrl() {
		return productPicUrl;
	}
	public void setProductPicUrl(String productPicUrl) {
		this.productPicUrl = productPicUrl;
	}
}
