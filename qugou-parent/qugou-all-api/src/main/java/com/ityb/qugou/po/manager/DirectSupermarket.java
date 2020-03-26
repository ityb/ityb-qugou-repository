package com.ityb.qugou.po.manager;

import com.ityb.qugou.base.entity.BaseEntity;

public class DirectSupermarket extends BaseEntity{

	/**
	 * serialVersionUID
	 * @date 2018年4月15日
	 */
	private static final long serialVersionUID = -7132804426517560084L;
	private String city;
	private String shopId;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	@Override
	public String getTableName() {
		return "t_p_direct_supermarket";
	}
	

}
