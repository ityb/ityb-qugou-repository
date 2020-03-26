package com.ityb.qugou.po.order;

import com.ityb.qugou.base.entity.BaseEntity;

public class DeliveryAddress extends BaseEntity{

	/**
	 * serialVersionUID
	 * @date 2018年3月27日
	 */
	private static final long serialVersionUID = 8654892736161651059L;

	private Integer type;
	private String customerName;
	private String customerPhone;
	private String address;
	private String province;
	private String city;
	private String town;
	private String area;
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	@Override
	public String getTableName() {
		return "t_p_delivery_address";
	}

}
