package com.ityb.qugou.service;

import java.util.List;

import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.vo.shop.ProductCategoryVo;
import com.ityb.qugou.vo.shop.ShopProductCatgoryVo;

/**
 * 商品分类service接口
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface ProductCategoryService {

	/**
	 * 获取商城商品分类列表
	 * @author yangbin
	 * @date 2018年2月14日
	 * @param productCategory
	 * @return
	 */
	List<ShopProductCatgoryVo> queryShopProductCategroy(ProductCategory productCategory);

	/**
	 * 得到对应的商店商品分类
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param productCategory
	 * @return
	 */
	List<ProductCategoryVo> queryMerchantProductCategory(ProductCategory productCategory);

}
