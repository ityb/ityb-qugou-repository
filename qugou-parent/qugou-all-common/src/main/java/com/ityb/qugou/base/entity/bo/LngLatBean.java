package com.ityb.qugou.base.entity.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 经纬度bean
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class LngLatBean implements Serializable {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月21日
	 */
	private static final long serialVersionUID = 4516503200914236958L;
	private BigDecimal lng;//经度
	private BigDecimal lat;//纬度
	public BigDecimal getLng() {
		return lng;
	}
	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}
	public BigDecimal getLat() {
		return lat;
	}
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}
}
