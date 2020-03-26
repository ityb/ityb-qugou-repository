package com.ityb.qugou.bo.evaluation;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class EvaluationBean implements Serializable{
	
	/**
	 * serialVersionUID
	 * @date 2018年4月6日
	 */
	private static final long serialVersionUID = -5467836766444387550L;
	private String evaluationUser;
	private String productSpecificationName;
	private String evaluationUserLogo;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date evaluationTime;
	private String evaluationContent;
	private String evaluationPic;
	private String evaluationReplyContent;
	private String evaluationReplyer;
	private Float fraction;
	public String getEvaluationUser() {
		return evaluationUser;
	}
	public void setEvaluationUser(String evaluationUser) {
		this.evaluationUser = evaluationUser;
	}
	public String getProductSpecificationName() {
		return productSpecificationName;
	}
	public void setProductSpecificationName(String productSpecificationName) {
		this.productSpecificationName = productSpecificationName;
	}
	public String getEvaluationUserLogo() {
		return evaluationUserLogo;
	}
	public void setEvaluationUserLogo(String evaluationUserLogo) {
		this.evaluationUserLogo = evaluationUserLogo;
	}
	public Date getEvaluationTime() {
		return evaluationTime;
	}
	public void setEvaluationTime(Date evaluationTime) {
		this.evaluationTime = evaluationTime;
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
	public String getEvaluationReplyer() {
		return evaluationReplyer;
	}
	public void setEvaluationReplyer(String evaluationReplyer) {
		this.evaluationReplyer = evaluationReplyer;
	}
	public Float getFraction() {
		return fraction;
	}
	public void setFraction(Float fraction) {
		this.fraction = fraction;
	}
}
