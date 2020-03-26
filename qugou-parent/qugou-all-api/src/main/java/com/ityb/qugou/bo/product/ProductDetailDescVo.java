package com.ityb.qugou.bo.product;

import java.io.Serializable;
import java.util.List;

public class ProductDetailDescVo implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年2月11日
	 */
	private static final long serialVersionUID = 3660855939868550297L;
	private String productId;
	private String productNumber;
	private String productName;
	private String productState;
	private String merchantCategoryName;
	private String shopCategoryName;
	private String title;
	private String sellPoint;
	private String productDesc;
	private String picUrl;
	private List<SpecificationBean> specificationList;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductState() {
		return productState;
	}
	public void setProductState(String productState) {
		this.productState = productState;
	}
	public String getMerchantCategoryName() {
		return merchantCategoryName;
	}
	public void setMerchantCategoryName(String merchantCategoryName) {
		this.merchantCategoryName = merchantCategoryName;
	}
	public String getShopCategoryName() {
		return shopCategoryName;
	}
	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public List<SpecificationBean> getSpecificationList() {
		return specificationList;
	}
	public void setSpecificationList(List<SpecificationBean> specificationList) {
		this.specificationList = specificationList;
	}
}
