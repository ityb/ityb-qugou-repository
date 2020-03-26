package com.ityb.qugou.po.manager;

import com.ityb.qugou.base.entity.BaseEntity;

public class Customer extends BaseEntity{

	/**
	 * serialVersionUID
	 * @date 2018年3月3日
	 */
	private static final long serialVersionUID = 8105016025572081331L;
	private String realName;
	private String phone;
	private Integer sex;
	//private String deliveryAddressId;
	private String userId;
	private String eamil;
	private String userHead;
	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/*public String getDeliveryAddressId() {
		return deliveryAddressId;
	}

	public void setDeliveryAddressId(String deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}*/

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEamil() {
		return eamil;
	}

	public void setEamil(String eamil) {
		this.eamil = eamil;
	}

	@Override
	public String getTableName() {
		return "t_sys_customer";
	}
}
