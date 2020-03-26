package com.ityb.qugou.vo.collection;

import java.io.Serializable;
import java.util.Date;

public class CollectionProductVo implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年4月7日
	 */
	private static final long serialVersionUID = 6910040034976409089L;
	private String id;
	private String productId;
	private String productTitle;
	private String sellPoint;
	private Date collectionTime;
	private Double price;// 商品单价
	private Double weight;// 库存量
	private String productSpecificationName;// 商品规格名称
	private String productSpecificationId;
	private String productPic;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductPic() {
		return productPic;
	}
	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}
	public Date getCollectionTime() {
		return collectionTime;
	}
	public void setCollectionTime(Date collectionTime) {
		this.collectionTime = collectionTime;
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
	public String getProductSpecificationName() {
		return productSpecificationName;
	}
	public void setProductSpecificationName(String productSpecificationName) {
		this.productSpecificationName = productSpecificationName;
	}
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
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

}
