package com.ityb.qugou.bo.manager;

import java.io.Serializable;

/**
 * 统计年实体
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class StatisticsYearBean implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年5月11日
	 */
	private static final long serialVersionUID = 4200904890435835062L;
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
