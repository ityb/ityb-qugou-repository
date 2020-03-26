package com.ityb.qugou.dto.manager;

import java.io.Serializable;

public class UserDto implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年3月3日
	 */
	private static final long serialVersionUID = 5250235435869712238L;
	private String phone;
	private Integer userType;
	private String userName;
	private String password;
	private String verificationCode;
	private String email;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
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
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
