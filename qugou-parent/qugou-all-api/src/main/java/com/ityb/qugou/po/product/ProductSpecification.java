package com.ityb.qugou.po.product;

import com.ityb.qugou.base.entity.BaseEntity;

public class ProductSpecification extends BaseEntity {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月21日
	 */
	private static final long serialVersionUID = -4262028280616109320L;
	// 规格名称
	private String name;
	// 销售价格
	private Double price;
	// 库存量
	private Integer stock;
	// 商品净重
	private Double weight;
	// 商品ip
	private String productId;
	
	private Integer state;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public String getTableName() {
		return "t_p_product_specification";
	}

}
