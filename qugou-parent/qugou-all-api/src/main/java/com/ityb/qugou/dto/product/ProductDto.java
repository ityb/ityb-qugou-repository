package com.ityb.qugou.dto.product;

import java.util.Date;
import java.util.List;

import com.ityb.qugou.base.dto.PageDto;
import com.ityb.qugou.po.product.BrowseHistory;

/**
 * 商品查询dto
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class ProductDto extends PageDto{

	/**
	 * serialVersionUID
	 * @date 2018年2月8日
	 */
	private static final long serialVersionUID = -6967280307221531329L;
	private String productName;
	private String categoryId;
	private Date addDate;
	private String creater;
	private Integer state;
	private String shopId;
	private Integer queryCount;//查询结果数量
	private String address;
	private String userId;
	private List<BrowseHistory> browseHistoryList;
	public List<BrowseHistory> getBrowseHistoryList() {
		return browseHistoryList;
	}
	public void setBrowseHistoryList(List<BrowseHistory> browseHistoryList) {
		this.browseHistoryList = browseHistoryList;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getQueryCount() {
		return queryCount;
	}
	public void setQueryCount(Integer queryCount) {
		this.queryCount = queryCount;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
		
}
