package com.ityb.qugou.dto.product;

import java.util.Date;

import com.ityb.qugou.base.dto.PageDto;

public class ProductSpecificationDto extends PageDto {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月21日
	 */
	private static final long serialVersionUID = -1469386926949885763L;
	private String productSpecificationId;
	private String number;// 商品编号
	private String name;// 商品名称
	private Date addDate;// 添加时间
	private String creater;//创建者

	public String getProductSpecificationId() {
		return productSpecificationId;
	}

	public void setProductSpecificationId(String productSpecificationId) {
		this.productSpecificationId = productSpecificationId;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
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

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

}
