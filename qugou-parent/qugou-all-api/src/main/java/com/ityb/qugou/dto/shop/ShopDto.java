package com.ityb.qugou.dto.shop;

import java.io.Serializable;

import com.ityb.qugou.base.dto.PageDto;

public class ShopDto extends PageDto implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年3月31日
	 */
	private static final long serialVersionUID = -8710734287483049216L;
	private String shopName;
	private Double distance;//距离多远
	private String address;//地址
	private Double x;//对应的x坐标
	private Double y;//对应的y坐标
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
