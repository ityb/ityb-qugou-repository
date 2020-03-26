package com.ityb.qugou.vo.search;

import java.io.Serializable;

/**
 * 搜索服务Vo
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class ProductSearchVo implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年2月4日
	 */
	private static final long serialVersionUID = -1416218969097912776L;
	
	private String productId;//商品ID
	private String title;//标题
	private String sellPoint;//卖点
	private Float price;//价格-该价格是最低价格
	private Float maxPrice;//价格-最高价格
	private String pic;//图片地址
	private String shopCategory;//系统分类
	private String merchantCategory;//商家分类
	private String addDate;//添加时间
	private String productAddress;//商品所属地址
	private String number;//商品编号
	private String name;//商品名称
	private String shopName;
	private String shopId;
	private String specificationId;//最低价格对应的规格
	public String getSpecificationId() {
		return specificationId;
	}
	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}
	public Float getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Float maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}
	public String getMerchantCategory() {
		return merchantCategory;
	}
	public void setMerchantCategory(String merchantCategory) {
		this.merchantCategory = merchantCategory;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getProductAddress() {
		return productAddress;
	}
	public void setProductAddress(String productAddress) {
		this.productAddress = productAddress;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
