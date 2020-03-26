package com.ityb.qugou.bo.manager;

import java.io.Serializable;

public class UserBean implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年3月3日
	 */
	private static final long serialVersionUID = -2933885227016029057L;
	private String phone;
	private Integer UserType;
	private String realName;
	private String userName;
	private String email;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getUserType() {
		return UserType;
	}
	public void setUserType(Integer userType) {
		UserType = userType;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
