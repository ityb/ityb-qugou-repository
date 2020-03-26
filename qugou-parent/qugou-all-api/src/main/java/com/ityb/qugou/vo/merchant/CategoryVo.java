package com.ityb.qugou.vo.merchant;

import java.io.Serializable;
import java.util.List;

public class CategoryVo implements Serializable {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年2月8日
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	List<CategoryVo> categoryList;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CategoryVo> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryVo> categoryList) {
		this.categoryList = categoryList;
	}
}
