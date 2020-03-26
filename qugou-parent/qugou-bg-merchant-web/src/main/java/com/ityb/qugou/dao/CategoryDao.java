package com.ityb.qugou.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.SqlUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.vo.manager.ProductCategoryVo;
import com.ityb.qugou.vo.merchant.CategoryVo;

/**
 * 商品分类dao
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Repository
public class CategoryDao extends BaseDao<ProductCategory>{
	@Autowired
	private BaseMapper baseMapper;

	/**
	 * 获取商品分类列表
	 * 
	 * @author yangbin
	 * @param productCategory 
	 * @date 2017年12月30日
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProductCategoryVo> queryProductCatgeory(ProductCategory productCategory) {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSelectItem("id");
		sqlCondition.addSelectItem("parentId", "pid");
		sqlCondition.addSelectItem("name");
		if(productCategory.getType()!=null){
			sqlCondition.addFilterEqualItem("type", productCategory.getType());
		}else{//默认是取商店的分类
			sqlCondition.addFilterEqualItem("type", 2);
		}
		if(StringUtils.isNotBlank(productCategory.getCreater())){
			sqlCondition.addFilterEqualItem("creater", productCategory.getCreater());
		}
		List<ProductCategoryVo> list = (List<ProductCategoryVo>) SqlUtils.executeFind(sqlCondition, baseMapper,
				ProductCategory.class, ProductCategoryVo.class);
		return list;
	}

	@Override
	protected BaseMapper getMapper() {
		return baseMapper;
	}

	@Override
	protected Class<?> getModelClass() {
		return ProductCategory.class;
	}

	/**
	 * 查询一级分类
	 * @author yangbin
	 * @date 2017年12月30日
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProductCategory> queryProductCategory(ProductCategory category) {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSelectItem("id");
		sqlCondition.addSelectItem("parentId");
		sqlCondition.addSelectItem("name");
		sqlCondition.addSelectItem("level");
		if(StringUtils.isNotBlank(category.getId())){
			sqlCondition.addFilterEqualItem("id", category.getId());
		}
		if(StringUtils.isNotBlank(category.getParentId())){
			sqlCondition.addFilterEqualItem("parentId", category.getParentId());
		}
		if(category.getType()!=null){
			sqlCondition.addFilterEqualItem("type", category.getType());
		}
		List<ProductCategory> list = (List<ProductCategory>) SqlUtils.executeFind(sqlCondition, baseMapper, ProductCategory.class);
		return list;
	}

	/**
	 * 查询分类列表
	 * @author yangbin
	 * @date 2018年2月8日
	 * @param productCategory
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CategoryVo> queryCategory(ProductCategory productCategory) {
		SqlCondition sqlCondition = new SqlCondition();
		if(StringUtils.isNoneBlank(productCategory.getCreater())){
			sqlCondition.addFilterEqualItem("creater", productCategory.getCreater());
			sqlCondition.addFilterEqualItem("parent.creater", productCategory.getCreater());
		}
		sqlCondition.addFilterEqualItem("type", 2);
		sqlCondition.addFilterEqualItem("parent.type", 2);
		sqlCondition.addSelectItem("id");
		sqlCondition.addSelectItem("name");
		sqlCondition.addSelectItem("parent.id","parentId");
		sqlCondition.addFilterEqualItem("parent.name","parentName");
		sqlCondition.addRelationItem(ProductCategory.class, ProductCategory.class, "parent");
		List<CategoryVo> list = (List<CategoryVo>) SqlUtils.executeFind(sqlCondition, baseMapper, ProductCategory.class, CategoryVo.class);
		return list;
	}
}
