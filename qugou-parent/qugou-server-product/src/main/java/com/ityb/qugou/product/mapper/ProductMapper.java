package com.ityb.qugou.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.bo.product.ProductBean;
import com.ityb.qugou.bo.product.ProductDetailDescVo;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.po.product.Product;
import com.ityb.qugou.po.product.ProductDesc;
import com.ityb.qugou.po.product.ProductDetail;
import com.ityb.qugou.vo.product.ProductVo;
import com.ityb.qugou.vo.search.ProductSearchVo;

/**
 * 商品表
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Repository
public interface ProductMapper {

	/**
	 * 添加商品
	 * @author yangbin
	 * @date 2018年1月27日
	 * @param product
	 */
	void insertProduct(Product product);

	/**
	 * 得到一个商品
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 Product getProduct(Product product);
	*/

	/**
	 * 获取商品列表
	 * 
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	List<Product> queryProduct(Product product);

	/**
	 * 根据dto获取对应的商品信息
	 * @author yangbin
	 * @date 2018年2月8日
	 * @param productDto
	 * @return
	 */
	List<ProductVo> queryProductByProductDto(ProductDto productDto);

	/**
	 * 根据dto 查询商品列表的数量
	 * @author yangbin
	 * @date 2018年2月8日
	 * @param productDto
	 * @return
	 */
	Integer countProductByProductDto(ProductDto productDto);

	/**
	 * 添加商品细节
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productBean
	 */
	void insertProductDeatil(ProductBean productBean);

	/**
	 * 添加商品详细
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productBean
	 */
	void insertProductDesc(ProductBean productBean);

	/**
	 * 查询商品细节
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productDetail
	 * @return
	 */
	ProductDetail getProductDetail(ProductDetail productDetail);

	/**
	 * 获取商品描述根据商品Id
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productDesc
	 * @return
	 */
	ProductDesc getProductDesc(ProductDesc productDesc);

	/**
	 * 更新商品细节
	 * @author yangbin
	 * @date 2018年2月9日
	 * @param request
	 * @param model
	 * @param product
	 * @return
	 */
	void updateProductDeatil(ProductBean productBean);

	/**
	 * 修改商品描述
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productBean
	 */
	void updateProductDesc(ProductBean productBean);

	/**
	 * 获取商品详细描述等信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 * @return
	 */
	ProductDetailDescVo getProductDetailDesc(Product product);

	/**
	 * 删除商品
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 */
	void deleteProduct(Product product);

	/**
	 * 删除商品详细
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 */
	void deleteProductDetail(Product product);

	/**
	 * 删除商品描述
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 */
	void deleteProductDesc(Product product);

	/**
	 * 更新商品状态
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 * @param i
	 */
	void updateProductState(@Param("product")Product product, @Param("state")int state);

	/**
	 * 更新商品状态
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 * @return
	 */
	void updateStateByProductIds(@Param("productIdList")List<String> productIdList, @Param("state")Integer state);

	/**
	 * 得到热销商品
	 * @author yangbin
	 * @date 2018年5月2日
	 * @param productDto
	 * @return
	 */
	List<ProductSearchVo> queryHotProduct(ProductDto productDto);
}
