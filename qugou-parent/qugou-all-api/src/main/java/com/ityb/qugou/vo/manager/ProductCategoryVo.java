package com.ityb.qugou.vo.manager;

import java.io.Serializable;

/**
 * 商品分类显示vo
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class ProductCategoryVo implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2017年12月30日
	 */
	private static final long serialVersionUID = -2288154585969193739L;
	private String id;
	private String pId;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public void setName(String name) {
		this.name = name;
	}
}
