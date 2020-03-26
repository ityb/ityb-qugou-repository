package com.ityb.qugou.po.merchant;

import com.ityb.qugou.base.entity.BaseEntity;

/**
 * 商家信息表
 * @author Administrator
 *
 */
public class Merchant extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5120550148423092351L;
	private String realName;
	private String phone;
	private String email;
	private String identityCardNumber;
	private String identityCardPic;
	private String userId;
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String getTableName() {
		return "t_sys_merchant";
	}
	public String getIdentityCardNumber() {
		return identityCardNumber;
	}
	public void setIdentityCardNumber(String identityCardNumber) {
		this.identityCardNumber = identityCardNumber;
	}
	public String getIdentityCardPic() {
		return identityCardPic;
	}
	public void setIdentityCardPic(String identityCardPic) {
		this.identityCardPic = identityCardPic;
	}
}
