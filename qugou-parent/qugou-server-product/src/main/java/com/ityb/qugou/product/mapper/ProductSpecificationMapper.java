package com.ityb.qugou.product.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.bo.product.SpecificationBean;
import com.ityb.qugou.dto.product.ProductSpecificationDto;
import com.ityb.qugou.po.product.Product;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.vo.product.ProductSpecificationVo;

/**
 * 商品规格中间表
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Repository
public interface ProductSpecificationMapper{
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
	 * 添加商品规格
	 * @author yangbin
	 * @date 2018年1月27日
	 * @param productSpecification
	 */
	void insertProductSpecification(ProductSpecification productSpecification);

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
	 * 获取规格列表
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param product
	 * @return
	 */
	List<ProductSpecification> querySpecification(ProductSpecification productSpecification);

	/**
	 * 根据规格ids获取对应的规格信息
	 * @author yangbin
	 * @date 2018年2月4日
	 * @param idSet
	 * @return
	 */
	List<ProductSpecificationVo> queryProductByIds(@Param("idSet") Set<String> idSet);

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
	 * 根据商品po得到对应的规格列表
	 * @author yangbin
	 * @date 2018年2月9日
	 * @param request
	 * @param model
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
	void batchUpdateState(@Param("specificationList")List<String> specificationList,@Param("state")int state);

	/**
	 * 更新商品规格状态通过商品ID
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 */
	void updateSpecificationStateByProduct(@Param("product")Product product,@Param("state")int state);

}
