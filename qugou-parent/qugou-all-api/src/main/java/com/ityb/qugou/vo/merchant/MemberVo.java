package com.ityb.qugou.vo.merchant;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MemberVo implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年4月12日
	 */
	private static final long serialVersionUID = -758287852023409508L;
	
	private String userName;
	private String userId;
	private Long collectionProductNumber;
	@JsonFormat(pattern="yyy-MM-dd",timezone="GMT+8")
	private Date collectionDate;
	private String email;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getCollectionProductNumber() {
		return collectionProductNumber;
	}
	public void setCollectionProductNumber(Long collectionProductNumber) {
		this.collectionProductNumber = collectionProductNumber;
	}
	public Date getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

}
