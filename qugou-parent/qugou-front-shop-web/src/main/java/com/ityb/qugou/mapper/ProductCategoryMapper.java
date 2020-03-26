package com.ityb.qugou.mapper;

import java.util.List;

import com.ityb.qugou.po.manager.ProductCategory;

public interface ProductCategoryMapper {

	/**
	 * 获取商城商品分类列表
	 * @author yangbin
	 * @date 2018年2月14日
	 * @param productCategory
	 * @return
	 */
	List<ProductCategory> queryProductCategroy(ProductCategory productCategory);

}
