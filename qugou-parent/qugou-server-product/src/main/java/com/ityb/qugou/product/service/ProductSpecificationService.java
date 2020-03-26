package com.ityb.qugou.product.service;

import java.util.List;
import com.ityb.qugou.bo.product.ProductSpecificationBean;
import com.ityb.qugou.bo.product.SpecificationBean;
import com.ityb.qugou.dto.product.ProductSpecificationDto;
import com.ityb.qugou.po.product.Product;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.vo.product.ProductSpecificationVo;

/**
 * 商品规格service
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface ProductSpecificationService {

	/**
	 * 获取商品规格列表
	 * @author yangbin
	 * @date 2018年1月22日
	 * @param productSpecificationDto
	 * @return
	 */
	List<ProductSpecificationVo> queryProductSpecification(ProductSpecificationDto productSpecificationDto);

	/**
	 * 获取产品规格数量
	 * @author yangbin
	 * @date 2018年1月26日
	 * @param productSpecificationDto
	 * @return
	 */
	int countProductSpecification(ProductSpecificationDto productSpecificationDto);

	/**
	 * 添加商品规格列表
	 * @author yangbin
	 * @date 2018年1月27日
	 * @param productSpecificationBean
	 * @return
	 */
	void addProductSpecification(ProductSpecificationBean productSpecificationBean);

	/**
	 * 获取商品编号下面的商品规格数量
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param product
	 * @return
	 */
	int countSpecification(Product product);

	/**
	 * 得到商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param product
	 * @return
	 */
	ProductSpecification getProductSpecification(ProductSpecification productSpecification);


	/**
	 * 添加一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	void addSpecification(ProductSpecification productSpecification);

	/**
	 * 根据规格ids获取对应的规格信息
	 * @author yangbin
	 * @date 2018年2月4日
	 * @param ids
	 * @return
	 */
	List<ProductSpecificationVo> queryProductByIds(String ids);


	/**
	 * 更新一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	void updateSpecification(ProductSpecification productSpecification);

	/**
	 * 删除一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	void deleteSpecification(ProductSpecification productSpecification);
	
	/**
	 * 查询商品规格列表根据商品po
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param product
	 * @return
	 */
	List<SpecificationBean> querySpecificationByPoduct(Product product);

	/**
	 * 批量更新商品规格状态
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param specificationList
	 * @param i
	 */
	void batchUpdateState(List<String> specificationList, int i);

	/**
	 * 更新商品规格状态通过商品ID
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 * @param state 
	 */
	void updateSpecificationStateByProduct(Product product, int state);

	/**
	 * 获取对应的商品规格信息
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param productSpecification
	 * @return
	 */
	List<ProductSpecification> querySpecification(ProductSpecification productSpecification);

}
