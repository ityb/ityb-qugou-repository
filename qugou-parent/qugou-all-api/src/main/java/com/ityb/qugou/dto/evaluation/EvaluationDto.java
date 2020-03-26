package com.ityb.qugou.dto.evaluation;

import java.util.Date;

import com.ityb.qugou.base.dto.PageDto;

public class EvaluationDto extends PageDto{

	/**
	 * serialVersionUID
	 * @date 2018年4月4日
	 */
	private static final long serialVersionUID = -6678653385367417454L;
	
	private String creater;
	private String productId;
	private String evaluationId;
	private String merchantId;
	private Date evaluationDate;
	private String userName;
	private Integer type;
	public String getUserName() {
		return userName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getEvaluationDate() {
		return evaluationDate;
	}
	public void setEvaluationDate(Date evaluationDate) {
		this.evaluationDate = evaluationDate;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getEvaluationId() {
		return evaluationId;
	}
	public void setEvaluationId(String evaluationId) {
		this.evaluationId = evaluationId;
	}

}
