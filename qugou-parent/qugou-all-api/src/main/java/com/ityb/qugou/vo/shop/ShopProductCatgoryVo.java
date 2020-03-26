package com.ityb.qugou.vo.shop;

import java.io.Serializable;
import java.util.List;

import com.ityb.qugou.po.manager.ProductCategory;

/**
 * 商城分类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class ShopProductCatgoryVo implements Serializable {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年2月14日
	 */
	private static final long serialVersionUID = 2082241053767723369L;
	ProductCategory productCategory;
	private List<ProductCategoryVo> categoryList;

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<ProductCategoryVo> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<ProductCategoryVo> categoryList) {
		this.categoryList = categoryList;
	}
}
