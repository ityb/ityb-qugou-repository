package com.ityb.qugou.vo.product;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductVo implements Serializable {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年2月8日
	 */
	private static final long serialVersionUID = -1761895833636840942L;

	private String productId;
	private String productName;// 商品名称
	private String title;// 标题
	private String sellPoint;// 卖点
	private String categoryName;// 分类名称
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date addDate;// 添加时间
	private String state;// 商品状态

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
