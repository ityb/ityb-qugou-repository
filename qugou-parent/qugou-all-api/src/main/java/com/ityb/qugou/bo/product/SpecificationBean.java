package com.ityb.qugou.bo.product;

import java.io.Serializable;
public class SpecificationBean implements Serializable{

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月27日
	 */
	private static final long serialVersionUID = -2573916519060668687L;
	private String productSpecificationName;
	private Double price;
	private Double weight;
	private Integer stock;
	private String productId;
	private String productSpecificationId;

	public String getProductSpecificationId() {
		return productSpecificationId;
	}

	public void setProductSpecificationId(String productSpecificationId) {
		this.productSpecificationId = productSpecificationId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductSpecificationName() {
		return productSpecificationName;
	}

	public void setProductSpecificationName(String productSpecificationName) {
		this.productSpecificationName = productSpecificationName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
}
