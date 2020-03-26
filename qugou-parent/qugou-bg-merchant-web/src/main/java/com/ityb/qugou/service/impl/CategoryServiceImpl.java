package com.ityb.qugou.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.dao.CategoryDao;
import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.service.CategoryService;
import com.ityb.qugou.vo.manager.ProductCategoryVo;
import com.ityb.qugou.vo.merchant.CategoryVo;

/**
 * 商品分类service
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<ProductCategory> implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;

	/**
	 * 获取商品分类列表
	 * 
	 * @author yangbin
	 * @date 2017年12月30日
	 * @return
	 */
	@Override
	public List<ProductCategoryVo> queryProductCatgeory(ProductCategory productCategory) {
		return this.categoryDao.queryProductCatgeory(productCategory);
	}

	@Override
	public List<ProductCategory> getListData() {
		return null;
	}

	@Override
	protected BaseDao<ProductCategory> getDao() {
		return categoryDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return ProductCategory.class;
	}

	/**
	 * 分类的数量
	 * 
	 * @author yangbin
	 * @date 2017年12月30日
	 * @return
	 */
	@Override
	public Integer countProductCategroy(String categoryId, Integer type) {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setParentId(categoryId);
		productCategory.setType(type);
		List<ProductCategory> list = this.categoryDao.queryProductCategory(productCategory);
		if (!CollectionUtils.isEmpty(list)) {
			return list.size();
		}
		return null;
	}

	/**
	 * 查询分类列表
	 * 
	 * @author yangbin
	 * @date 2018年2月8日
	 * @param productCategory
	 * @return
	 */
	@Override
	public List<CategoryVo> queryCategoryForOption(ProductCategory productCategory) {
		List<ProductCategoryVo> list = this.categoryDao.queryProductCatgeory(productCategory);
		List<CategoryVo> resultList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			CategoryVo categoryVo = null;
			List<CategoryVo> categoryVoList = null;
			Boolean flag=false;
			for (ProductCategoryVo parentProductCategoryVo : list) {
				if ("0".equals(parentProductCategoryVo.getpId())) {// 表示父节点
					categoryVoList = new ArrayList<>();
					categoryVo = new CategoryVo();
					categoryVo.setId(parentProductCategoryVo.getId());
					categoryVo.setName(parentProductCategoryVo.getName());
					for (ProductCategoryVo productCategoryVo : list) {
						if (productCategoryVo.getpId().equals(parentProductCategoryVo.getId())) {
							if (2 == productCategory.getType()) {
								CategoryVo categoryVo2 = new CategoryVo();
								categoryVo2.setId(productCategoryVo.getId());
								categoryVo2.setName(productCategoryVo.getName());
								categoryVoList.add(categoryVo2);
								flag=true;
							}
							if (1 == productCategory.getType()) {
								categoryVoList = new ArrayList<>();
								categoryVo = new CategoryVo();
								categoryVo.setId(productCategoryVo.getId());
								categoryVo.setName(productCategoryVo.getName());
								for (ProductCategoryVo productCategoryVo2 : list) {
									if (productCategoryVo2.getpId().equals(productCategoryVo.getId())) {
										CategoryVo categoryVo1= new CategoryVo();
										categoryVo1.setId(productCategoryVo2.getId());
										categoryVo1.setName(productCategoryVo2.getName());
										categoryVoList.add(categoryVo1);
									}
								}
								categoryVo.setCategoryList(categoryVoList);
								resultList.add(categoryVo);
							}
						}
					}
					if(flag){
						categoryVo.setCategoryList(categoryVoList);
						resultList.add(categoryVo);
					}
				}
			}
		}
		return resultList;
	}
}
