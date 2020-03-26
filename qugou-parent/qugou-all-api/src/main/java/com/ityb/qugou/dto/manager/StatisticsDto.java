package com.ityb.qugou.dto.manager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StatisticsDto implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年5月11日
	 */
	private static final long serialVersionUID = 6427866454560409214L;
	private Integer year;
	private String merchantId;
	private Integer exportType;
	private Integer month;
	private Integer season;
	private Date day;
	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	private List<Integer> monthList;

	public List<Integer> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<Integer> monthList) {
		this.monthList = monthList;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getExportType() {
		return exportType;
	}

	public void setExportType(Integer exportType) {
		this.exportType = exportType;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}
