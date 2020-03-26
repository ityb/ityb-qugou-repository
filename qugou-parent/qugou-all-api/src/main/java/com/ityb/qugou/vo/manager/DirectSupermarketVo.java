package com.ityb.qugou.vo.manager;

import java.io.Serializable;
import java.util.Date;

public class DirectSupermarketVo implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年4月15日
	 */
	private static final long serialVersionUID = 65250565331970712L;
	private String directSupermarketId;
	private String shopId;
	private String shopName;
	private String shopAddress;
	private Date addTime;
	private String shopLogo;
	public String getShopLogo() {
		return shopLogo;
	}
	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getDirectSupermarketId() {
		return directSupermarketId;
	}
	public void setDirectSupermarketId(String directSupermarketId) {
		this.directSupermarketId = directSupermarketId;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

}
