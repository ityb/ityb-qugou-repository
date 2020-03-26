package com.ityb.qugou.service.content.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.dao.content.ProductCategoryDao;
import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.service.content.ProductCategoryService;
import com.ityb.qugou.vo.manager.ProductCategoryVo;

/**
 * 商品分类service
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory> implements ProductCategoryService{
	@Autowired
	private ProductCategoryDao productCategoryDao;

	/**
	 * 获取商品分类列表
	 * @author yangbin
	 * @date 2017年12月30日
	 * @return
	 */
	@Override
	public List<ProductCategoryVo> queryProductCatgeory() {
		return this.productCategoryDao.queryProductCatgeory();
	}

	@Override
	public List<ProductCategory> getListData() {
		return null;
	}

	@Override
	protected BaseDao<ProductCategory> getDao() {
		return productCategoryDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return ProductCategory.class;
	}

	/**
	 * 分类的数量
	 * @author yangbin
	 * @date 2017年12月30日
	 * @return
	 */
	@Override
	public Integer countProductCategroy(String categoryId, Integer type) {
		ProductCategory productCategory=new ProductCategory();
		productCategory.setParentId(StringUtils.isBlank(categoryId)?"0":categoryId);
		productCategory.setType(type);
		List<ProductCategory> list=this.productCategoryDao.queryProductCategory(productCategory);
		if(!CollectionUtils.isEmpty(list)){
			return list.size();
		}
		return null;
	}
}
