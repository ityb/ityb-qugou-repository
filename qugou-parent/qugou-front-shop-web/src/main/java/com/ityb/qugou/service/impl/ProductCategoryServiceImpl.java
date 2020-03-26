package com.ityb.qugou.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.annotation.RedisCache;
import com.ityb.qugou.base.enums.CacheType;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.constant.RedisConstants;
import com.ityb.qugou.mapper.ProductCategoryMapper;
import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.service.ProductCategoryService;
import com.ityb.qugou.vo.shop.ProductCategoryVo;
import com.ityb.qugou.vo.shop.ShopProductCatgoryVo;

/**
 * 商品分类service实现类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Autowired
	private ProductCategoryMapper productCategoryMapper;
	/**
	 * 获取商城商品分类列表
	 * @author yangbin
	 * @date 2018年2月14日
	 * @param productCategory
	 * @return
	 */
	@Override
	@RedisCache(cacheType=CacheType.KeyValue,keyName=RedisConstants.PRODUCT_CATEGORY_KEY,expireSeconds=RedisConstants.EXPIRESECONDS)
	public List<ShopProductCatgoryVo> queryShopProductCategroy(ProductCategory productCategory) {
		Assert.isTrue(productCategory.getType()==1,"商城商品分类的类型不正确");
		List<ProductCategory> list=productCategoryMapper.queryProductCategroy(productCategory);
		List<ShopProductCatgoryVo> resultList=new ArrayList<>();
		if(CollectionUtils.isEmpty(list)){
			return resultList;
		}
		list.forEach(firstCategory->{
			ShopProductCatgoryVo shopProductCatgoryVo=new ShopProductCatgoryVo();
			if("0".equals(firstCategory.getParentId())){//表示已经找到一级分类件分类
				shopProductCatgoryVo.setProductCategory(firstCategory);
				List<ProductCategoryVo> secondCategoryList=new ArrayList<>();
				list.forEach(secondCategory->{//遍历找二级分类
					if(firstCategory.getId().equals(secondCategory.getParentId())){
						ProductCategoryVo productCategoryVo=new ProductCategoryVo();
						productCategoryVo.setProductCategory(secondCategory);
						List<ProductCategory> thirdCategoryList=new ArrayList<>();
						list.forEach(thirdCategory->{//遍历找三级分类
							if(secondCategory.getId().equals(thirdCategory.getParentId())){
								thirdCategoryList.add(thirdCategory);
							}
						});
						productCategoryVo.setCategoryList(thirdCategoryList);
						secondCategoryList.add(productCategoryVo);
						shopProductCatgoryVo.setCategoryList(secondCategoryList);
					}
				});
				resultList.add(shopProductCatgoryVo);
			}
		});
		return resultList;
	}
	
	/**
	 * 得到对应的商店商品分类
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param productCategory
	 * @return
	 */
	@Override
	public List<ProductCategoryVo> queryMerchantProductCategory(ProductCategory productCategory) {
		Assert.isTrue(productCategory.getType()==2,"商城商品分类的类型不正确");
		List<ProductCategory> list=productCategoryMapper.queryProductCategroy(productCategory);
		List<ProductCategoryVo> resultList=new ArrayList<>();
		for (ProductCategory merchantCategory : list) {
			if("0".equals(merchantCategory.getParentId())){
				ProductCategoryVo productCategoryVo=new ProductCategoryVo();
				ProductCategory parentCategory=new ProductCategory();
				parentCategory.setId(merchantCategory.getId());
				parentCategory.setName(merchantCategory.getName());
				productCategoryVo.setProductCategory(parentCategory);
				List<ProductCategory> subCategoryList=new ArrayList<>();
				for (ProductCategory merchantCatgory2 : list) {
					if(merchantCatgory2.getParentId().equals(merchantCategory.getId())){
						ProductCategory subCategory=new ProductCategory();
						subCategory.setId(merchantCatgory2.getId());
						subCategory.setName(merchantCatgory2.getName());
						subCategoryList.add(subCategory);
					}
				}
				productCategoryVo.setCategoryList(subCategoryList);
				resultList.add(productCategoryVo);
			}
		}
		return resultList;
	}
}
