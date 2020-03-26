package com.ityb.qugou.dao.content;

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

/**
 * 商品分类dao
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Repository
public class ProductCategoryDao extends BaseDao<ProductCategory>{
	@Autowired
	private BaseMapper baseMapper;

	/**
	 * 获取商品分类列表
	 * 
	 * @author yangbin
	 * @date 2017年12月30日
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProductCategoryVo> queryProductCatgeory() {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSelectItem("id");
		sqlCondition.addSelectItem("parentId", "pid");
		sqlCondition.addSelectItem("name");
		sqlCondition.addFilterEqualItem("type", 1);
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
	public List<ProductCategory> queryProductCategory(ProductCategory productCategory) {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSelectItem("id");
		sqlCondition.addSelectItem("parentId");
		sqlCondition.addSelectItem("name");
		sqlCondition.addSelectItem("level");
		if(StringUtils.isNotBlank(productCategory.getId())){
			sqlCondition.addFilterEqualItem("id", productCategory.getId());
		}
		if(StringUtils.isNotBlank(productCategory.getParentId())){
			sqlCondition.addFilterEqualItem("parentId", productCategory.getParentId());
		}
		if(productCategory.getType()!=null){
			sqlCondition.addFilterEqualItem("type", productCategory.getType());
		}
		List<ProductCategory> list = (List<ProductCategory>) SqlUtils.executeFind(sqlCondition, baseMapper, ProductCategory.class);
		return list;
	}
}
