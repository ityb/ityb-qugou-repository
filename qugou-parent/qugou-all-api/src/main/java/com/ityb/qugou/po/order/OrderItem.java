package com.ityb.qugou.po.order;

import com.ityb.qugou.base.entity.BaseEntity;

public class OrderItem extends BaseEntity{

	/**
	 * serialVersionUID
	 * @date 2018年2月13日
	 */
	private static final long serialVersionUID = -3921102830514343398L;
	private String orderId;
	private String productSpecificationId;
	private Double subtotal;
	private Integer buyNum;
	private Integer isEvaluation;
	public Integer getIsEvaluation() {
		return isEvaluation;
	}
	public void setIsEvaluation(Integer isEvaluation) {
		this.isEvaluation = isEvaluation;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductSpecificationId() {
		return productSpecificationId;
	}
	public void setProductSpecificationId(String productSpecificationId) {
		this.productSpecificationId = productSpecificationId;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public Integer getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}
	@Override
	public String getTableName() {
		return "t_p_order_item";
	}

}
