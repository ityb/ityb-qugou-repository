package com.ityb.qugou.bo.manager;

import java.io.Serializable;

public class StatisticsSeasonBean implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年5月12日
	 */
	private static final long serialVersionUID = 5585871136875969371L;
	private Integer month;//统计月份
	private Double totalMoney;//统计总额
	private Double totalNum;//统计数量
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
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
