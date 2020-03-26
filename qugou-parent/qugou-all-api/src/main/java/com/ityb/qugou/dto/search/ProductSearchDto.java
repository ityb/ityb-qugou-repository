package com.ityb.qugou.dto.search;

import java.io.Serializable;
import java.util.List;

public class ProductSearchDto implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年2月11日
	 */
	private static final long serialVersionUID = -1523602483621904934L;
	private String productId;
	private Float priceLeft;//价格的左区间
	private Float priceRight;//价格的右区间
	private Integer priceState;//表示价格的升降，1表示升，2表示降
	private Integer pageStart;//页面的开始
	private Integer pageSize;//页面显示的条数
	private String keyWord;//关键字
	private Integer pageNow;
	private String address;
	private String shopCategoryName;
	private String merchantCategoryName;
	private String shopName;
	private String shopId;
	private List<String> productIdList;	
	private Integer addTimeOrder;//价格排序，1表示升序，2表示降序
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public Integer getAddTimeOrder() {
		return addTimeOrder;
	}
	public void setAddTimeOrder(Integer addTimeOrder) {
		this.addTimeOrder = addTimeOrder;
	}
	public List<String> getProductIdList() {
		return productIdList;
	}
	public void setProductIdList(List<String> productIdList) {
		this.productIdList = productIdList;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopCategoryName() {
		return shopCategoryName;
	}
	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}
	public String getMerchantCategoryName() {
		return merchantCategoryName;
	}
	public void setMerchantCategoryName(String merchantCategoryName) {
		this.merchantCategoryName = merchantCategoryName;
	}
	public Integer getPageNow() {
		return pageNow;
	}
	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}
	public Integer getPageStart() {
		return pageStart;
	}
	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Float getPriceLeft() {
		return priceLeft;
	}
	public void setPriceLeft(Float priceLeft) {
		this.priceLeft = priceLeft;
	}
	public Float getPriceRight() {
		return priceRight;
	}
	public void setPriceRight(Float priceRight) {
		this.priceRight = priceRight;
	}
	public Integer getPriceState() {
		return priceState;
	}
	public void setPriceState(Integer priceState) {
		this.priceState = priceState;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
