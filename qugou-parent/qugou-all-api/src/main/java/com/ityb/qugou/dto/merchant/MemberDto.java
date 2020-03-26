package com.ityb.qugou.dto.merchant;

import java.io.Serializable;
import java.util.Date;

import com.ityb.qugou.base.dto.PageDto;

public class MemberDto extends PageDto implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年4月12日
	 */
	private static final long serialVersionUID = 2028320439804361030L;
	private String userName;
	private Date collectionDate;
	private String merchantId;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
}
