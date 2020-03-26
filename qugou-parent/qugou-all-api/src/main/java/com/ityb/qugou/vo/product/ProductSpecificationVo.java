package com.ityb.qugou.vo.product;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 商品规格显示数据
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class ProductSpecificationVo implements Serializable {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月21日
	 */
	private static final long serialVersionUID = 4169926745363400875L;

	private String productId;// 商品唯一标识
	private String productName;// 商品名称
	private String productNumber;//商品编号
	private Double price;// 商品单价
	private Integer stock;// 库存量
	private String productSpecificationId;// 商品规格ID
	private String productSpecificationName;// 商品规格名称
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date productAddDate;// 商品添加时间
	private Double weight;// 商品净重

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}


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

	public String getProductSpecificationId() {
		return productSpecificationId;
	}

	public void setProductSpecificationId(String productSpecificationId) {
		this.productSpecificationId = productSpecificationId;
	}

	public String getProductSpecificationName() {
		return productSpecificationName;
	}

	public void setProductSpecificationName(String productSpecificationName) {
		this.productSpecificationName = productSpecificationName;
	}

	public Date getProductAddDate() {
		return productAddDate;
	}

	public void setProductAddDate(Date productAddDate) {
		this.productAddDate = productAddDate;
	}
}
