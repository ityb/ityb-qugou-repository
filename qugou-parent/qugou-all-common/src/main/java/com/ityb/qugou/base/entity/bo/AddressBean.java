package com.ityb.qugou.base.entity.bo;

import java.io.Serializable;
/**
 * 地址bean
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class AddressBean implements Serializable {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月21日
	 */
	private static final long serialVersionUID = -2439354985962608381L;
	private String province; // 省
	private String city;// 城市
	private String district;// 区县
	private String street;// 街道
	private String streetNumber;// 门牌号
	private PointBean pointBean;

	public PointBean getPointBean() {
		return pointBean;
	}

	public void setPointBean(PointBean pointBean) {
		this.pointBean = pointBean;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String toString() {
		return "AddressBean [province=" + province + ", city=" + city + ", district=" + district + ", street=" + street
				+ ", streetNumber=" + streetNumber + ", pointBean=" + pointBean + "]";
	}
}
