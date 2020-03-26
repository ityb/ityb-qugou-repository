package com.ityb.qugou.vo.shop;

import java.io.Serializable;
import java.util.List;

import com.ityb.qugou.po.manager.ProductCategory;

public class ProductCategoryVo implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年2月15日
	 */
	private static final long serialVersionUID = 921486817487176998L;
	private ProductCategory productCategory;
	private List<ProductCategory> categoryList;
	public ProductCategory getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	public List<ProductCategory> getCategoryList() {
		return categoryList;
		
	}
	public void setCategoryList(List<ProductCategory> categoryList) {
		this.categoryList = categoryList;
	}
}
