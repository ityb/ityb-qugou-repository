package com.ityb.qugou.vo.evaluation;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EvaluationVo implements Serializable {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年4月4日
	 */
	private static final long serialVersionUID = -326268668680510438L;
	private Double price;// 商品单价
	private Double weight;// 库存量
	private String productSpecificationName;// 商品规格名称
	private String productName;
	private String productId;
	private String productPic;
	private String productTitle;
	private String sellPoint;
	private String productSpecificationId;
	private String evaluationContent;
	private String evaluationPic;
	private String evaluationReplyContent;
	private Date evaluationReplyTime;
	private Float qualityFraction;
	private Float descFraction;
	private Float deliveryFraction;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date evaluationTime;
	private String evaluationId;
	private String orderNumber;
	private String userName;
	private Integer evaluationType;

	public Integer getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(Integer evaluationType) {
		this.evaluationType = evaluationType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(String evaluationId) {
		this.evaluationId = evaluationId;
	}

	public Date getEvaluationTime() {
		return evaluationTime;
	}

	public void setEvaluationTime(Date evaluationTime) {
		this.evaluationTime = evaluationTime;
	}

	public String getProductPic() {
		return productPic;
	}

	public void setProductPic(String productPic) {
		this.productPic = productPic;
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

	public String getProductSpecificationId() {
		return productSpecificationId;
	}

	public void setProductSpecificationId(String productSpecificationId) {
		this.productSpecificationId = productSpecificationId;
	}

	public String getEvaluationContent() {
		return evaluationContent;
	}

	public void setEvaluationContent(String evaluationContent) {
		this.evaluationContent = evaluationContent;
	}

	public String getEvaluationPic() {
		return evaluationPic;
	}

	public void setEvaluationPic(String evaluationPic) {
		this.evaluationPic = evaluationPic;
	}

	public String getEvaluationReplyContent() {
		return evaluationReplyContent;
	}

	public void setEvaluationReplyContent(String evaluationReplyContent) {
		this.evaluationReplyContent = evaluationReplyContent;
	}

	public Date getEvaluationReplyTime() {
		return evaluationReplyTime;
	}

	public void setEvaluationReplyTime(Date evaluationReplyTime) {
		this.evaluationReplyTime = evaluationReplyTime;
	}

	public Float getQualityFraction() {
		return qualityFraction;
	}

	public void setQualityFraction(Float qualityFraction) {
		this.qualityFraction = qualityFraction;
	}

	public Float getDescFraction() {
		return descFraction;
	}

	public void setDescFraction(Float descFraction) {
		this.descFraction = descFraction;
	}

	public Float getDeliveryFraction() {
		return deliveryFraction;
	}

	public void setDeliveryFraction(Float deliveryFraction) {
		this.deliveryFraction = deliveryFraction;
	}

}
