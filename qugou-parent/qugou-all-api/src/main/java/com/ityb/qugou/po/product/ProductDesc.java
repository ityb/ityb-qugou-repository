package com.ityb.qugou.po.product;

import com.ityb.qugou.base.entity.BaseEntity;

/**
 * 商品实体类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class ProductDesc extends BaseEntity {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月21日
	 */
	private static final long serialVersionUID = -1343295144242086584L;
	
	//商品描述
	private String productDesc;
	//商品ID
	private String productId;

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String getTableName() {
		return "t_p_product_desc";
	}
}
