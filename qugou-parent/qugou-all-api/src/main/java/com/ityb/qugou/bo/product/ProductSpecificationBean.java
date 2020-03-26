package com.ityb.qugou.bo.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品规格列表
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class ProductSpecificationBean implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年1月27日
	 */
	private static final long serialVersionUID = 8243787559798308298L;
	private String productName;
	private String productNumber;
	private String creater;
	private Date ctime;
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	private List<SpecificationBean> specificationList;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public List<SpecificationBean> getSpecificationList() {
		return specificationList;
	}
	public void setSpecificationList(List<SpecificationBean> specificationList) {
		this.specificationList = specificationList;
	}
}
