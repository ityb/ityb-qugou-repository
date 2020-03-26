package com.ityb.qugou.po.manager;

import com.ityb.qugou.base.entity.BaseEntity;

/**
 * 商品分类实体类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class ProductCategory extends BaseEntity{
	/**
	 * serialVersionUID
	 * 
	 * @date 2017年12月30日
	 */
	private static final long serialVersionUID = 7852347951536909786L;
	private String name;
	private String parentId;
	private Integer level;
	private Integer type;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getTableName() {
		return "t_p_sys_product_category";
	}

}
