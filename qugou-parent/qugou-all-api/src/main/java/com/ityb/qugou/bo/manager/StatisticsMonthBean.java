package com.ityb.qugou.bo.manager;

import java.io.Serializable;

public class StatisticsMonthBean implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年5月12日
	 */
	private static final long serialVersionUID = -3214075875128745502L;
	private  String productName;
	private Double totalMoney;//统计总额
	private Double totalNum;//统计数量
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Double getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}

}
