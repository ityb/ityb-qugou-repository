package com.ityb.qugou.vo.order;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单统计显示类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class OrderStatisticsVo implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年5月15日
	 */
	private static final long serialVersionUID = -4830269248566545208L;
	private Integer orderCount;
	private Double tradeMoney;
	private Integer tradeCount;
	private Date statisticsTime;
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public Double getTradeMoney() {
		return tradeMoney;
	}
	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}
	public Date getStatisticsTime() {
		return statisticsTime;
	}
	public void setStatisticsTime(Date statisticsTime) {
		this.statisticsTime = statisticsTime;
	}
	public Integer getTradeCount() {
		return tradeCount;
	}
	public void setTradeCount(Integer tradeCount) {
		this.tradeCount = tradeCount;
	}
}
