package com.ityb.qugou.po.evaluation;

import java.util.Date;

import com.ityb.qugou.base.entity.BaseEntity;

public class Evaluation extends BaseEntity{
	private String orderId;
	private String productSpecificationId;
	private String evaluationContent;
	private String evaluationPic;
	private String evaluationReplyContent;
	private Date evaluationReplyTime;
	private Float qualityFraction;
	private Float descFraction;
	private Float deliveryFraction;
	private Integer type;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * serialVersionUID
	 * @date 2018年4月2日
	 */
	private static final long serialVersionUID = -172721258559638181L;

	@Override
	public String getTableName() {
		return "t_p_evaluation";
	}

}
