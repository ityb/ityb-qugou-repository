package com.ityb.qugou.po.merchant;

import java.math.BigDecimal;

import com.ityb.qugou.base.entity.BaseEntity;

public class Shop extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105440652377592337L;

	private String shopAddress;
	private String businessLicence;
	private String shopLogo;
	private String shopName;
	private String shopPhone;
	private String userId;
	private String shopDesc;
	private BigDecimal y;// 平面y轴
	private BigDecimal x;// 平面z轴
	private Float startPrice;//起步价
	public String getShopDesc() {
		return shopDesc;
	}

	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}

	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}

	public Float getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Float startPrice) {
		this.startPrice = startPrice;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}

	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	@Override
	public String getTableName() {
		return "t_sys_shop";
	}

}
