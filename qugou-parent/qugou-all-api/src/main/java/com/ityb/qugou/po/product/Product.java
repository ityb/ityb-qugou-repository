package com.ityb.qugou.po.product;

import com.ityb.qugou.base.entity.BaseEntity;

/**
 * 商品实体类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class Product extends BaseEntity {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月21日
	 */
	private static final long serialVersionUID = -1343295144242086584L;

	// 商品名称
	private String name;
	// 商品编号
	private String number;
	//商品状态0表示未上架，1表示已上架
	private Integer state;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	// 商品标题
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String getTableName() {
		return "t_p_product";
	}

}
