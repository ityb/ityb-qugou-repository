package com.ityb.qugou.dto.manager;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 商家信息dto
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class MerchantDto implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年3月15日
	 */
	private static final long serialVersionUID = -8628545393453027209L;
	private String phone;
	private String identityCard;
	private String realName;
	private String identityCardPic;
	private String userName;
	private String password;
	private String shopName;
	private String shopAddress;
	private String businessLicencePic;
	private BigDecimal y;// 平面y轴
	private BigDecimal x;// 平面z轴
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdentityCardPic() {
		return identityCardPic;
	}
	public void setIdentityCardPic(String identityCardPic) {
		this.identityCardPic = identityCardPic;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getBusinessLicencePic() {
		return businessLicencePic;
	}
	public void setBusinessLicencePic(String businessLicencePic) {
		this.businessLicencePic = businessLicencePic;
	}

}
