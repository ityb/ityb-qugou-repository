package com.ityb.qugou.bo.product;

import java.io.Serializable;
import java.util.Date;

public class ProductBean implements Serializable {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年2月10日
	 */
	private static final long serialVersionUID = -2079224724872848330L;
	// 商品标题
	private String title;
	// 卖点
	private String sellPoint;
	// 指向商家商品分类
	private String merchantCategoryId;
	// 指向商城商品分类
	private String shopCategoryId;
	// 商品图片Url集合中间用逗号隔开
	private String picUrl;
	// 商品Id
	private String productId;
	//商品描述
	private String productDesc;
	//商品规格列表
	private String specifications;//中间用逗号分隔
	private Date addDate;
	private String creater;
	private Date modifyDate;
	private String updater;
	private String id;
	
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
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
	public String getMerchantCategoryId() {
		return merchantCategoryId;
	}
	public void setMerchantCategoryId(String merchantCategoryId) {
		this.merchantCategoryId = merchantCategoryId;
	}
	public String getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(String shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
}
