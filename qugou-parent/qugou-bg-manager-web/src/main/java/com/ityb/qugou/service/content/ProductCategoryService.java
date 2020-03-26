package com.ityb.qugou.service.content;

import java.util.List;

import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.vo.manager.ProductCategoryVo;

/**
 * 商品分类service
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface ProductCategoryService extends BaseService<ProductCategory>{

	/**
	 * 获取商品分类列表
	 * @author yangbin
	 * @date 2017年12月30日
	 * @return
	 */
	List<ProductCategoryVo> queryProductCatgeory();

	/**
	 * 分类的数量
	 * @author yangbin
	 * @date 2017年12月30日
	 * @return
	 */
	Integer countProductCategroy(String categoryId, Integer type);

}
