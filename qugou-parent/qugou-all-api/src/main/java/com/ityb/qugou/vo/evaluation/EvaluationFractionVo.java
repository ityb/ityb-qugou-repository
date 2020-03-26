package com.ityb.qugou.vo.evaluation;

import java.io.Serializable;

/**
 * 评论分数vo
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class EvaluationFractionVo implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年4月6日
	 */
	private static final long serialVersionUID = 7210831289190011332L;
	private Float complexFraction;
	private Float qualityFraction;
	private Float descFraction;
	private Float deliveryFraction;
	public Float getComplexFraction() {
		return complexFraction;
	}
	public void setComplexFraction(Float complexFraction) {
		this.complexFraction = complexFraction;
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
