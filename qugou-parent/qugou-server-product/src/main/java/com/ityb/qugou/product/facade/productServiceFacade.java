package com.ityb.qugou.product.facade;

import com.ityb.qugou.bo.product.ProductBean;
import com.ityb.qugou.bo.product.ProductDetailDescVo;
import com.ityb.qugou.po.product.Product;

public interface productServiceFacade {

	/**
	 * 添加商品信息
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productBean
	 * @return
	 */
	void addProduct(ProductBean productBean);

	/**
	 * 修改商品信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productBean
	 * @return
	 */
	void updateProduct(ProductBean productBean);

	/**
	 * 获取商品详细描述等信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 * @return
	 */
	ProductDetailDescVo getProductDetailDesc(Product product);

	/**
	 * 删除一个商品信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 * @return
	 */
	void deleteProduct(Product product);
}
