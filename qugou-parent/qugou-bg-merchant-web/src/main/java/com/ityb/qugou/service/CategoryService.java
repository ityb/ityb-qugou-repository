package com.ityb.qugou.service;

import java.util.List;

import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.vo.manager.ProductCategoryVo;
import com.ityb.qugou.vo.merchant.CategoryVo;

/**
 * 商品分类service
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface CategoryService extends BaseService<ProductCategory>{

	/**
	 * 获取商品分类列表
	 * @author yangbin
	 * @param productCategory 
	 * @date 2017年12月30日
	 * @return
	 */
	List<ProductCategoryVo> queryProductCatgeory(ProductCategory productCategory);

	/**
	 * 分类的数量
	 * @author yangbin
	 * @date 2017年12月30日
	 * @return
	 */
	Integer countProductCategroy(String categoryId, Integer type);

	/**
	 * 查询分类列表
	 * @author yangbin
	 * @date 2018年2月8日
	 * @param productCategory
	 * @return
	 */
	List<CategoryVo> queryCategoryForOption(ProductCategory productCategory);

}
