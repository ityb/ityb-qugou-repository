package com.ityb.qugou.product.service;

import java.util.List;

import com.ityb.qugou.bo.product.ProductBean;
import com.ityb.qugou.bo.product.ProductDetailDescVo;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.po.product.Product;
import com.ityb.qugou.po.product.ProductDesc;
import com.ityb.qugou.po.product.ProductDetail;
import com.ityb.qugou.vo.product.ProductVo;
import com.ityb.qugou.vo.search.ProductSearchVo;

/**
 * 商品服务service
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface ProductService {


	/**
	 * 得到一个商品
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	Product getProduct(Product product);

	/**
	 * 获取商品列表
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
	 * 添加商品信息
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productBean
	 */
	void addProductDeatil(ProductBean productBean);

	/**
	 * 添加商品描述
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productBean
	 */
	void addProductDesc(ProductBean productBean);

	/**
	 * 查询商品细节
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productDetail
	 * @return
	 */
	ProductDetail getProductDetil(ProductDetail productDetail);

	/**
	 * 获取商品描述根据商品Id
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productDesc
	 * @return
	 */
	ProductDesc getProductDesc(ProductDesc productDesc);

	/**
	 * 修改商品详细
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productBean
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
	void updateProductState(Product product, int state);

	/**
	 * 更新商品状态
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 * @return
	 */
	void updateStateByProductIds(List<String> productIdList, Integer state);

	/**
	 * 得到热销商品
	 * @author yangbin
	 * @date 2018年5月2日
	 * @param productDto
	 * @return
	 */
	List<ProductSearchVo> queryHotProduct(ProductDto productDto);
}
