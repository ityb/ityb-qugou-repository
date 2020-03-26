package com.ityb.qugou.vo.cart;

import java.io.Serializable;
import java.util.List;

public class CartOrderCheckShowVo implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年3月27日
	 */
	private static final long serialVersionUID = 5241879582035966971L;
	private List<CartOrderCheckVo>  cartOrderCheckVoList;
	private Integer totalNumber;
	private Double totalWeight;
	private Double totalMoney;
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public List<CartOrderCheckVo> getCartOrderCheckVoList() {
		return cartOrderCheckVoList;
	}
	public void setCartOrderCheckVoList(List<CartOrderCheckVo> cartOrderCheckVoList) {
		this.cartOrderCheckVoList = cartOrderCheckVoList;
	}
	public Integer getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
}
