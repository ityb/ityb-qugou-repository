package com.ityb.qugou.cart.service;

import java.util.List;

import com.ityb.qugou.po.cart.Cart;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.vo.cart.CartOrderCheckShowVo;
import com.ityb.qugou.vo.cart.CartVo;

/**
 * 购物车相关服务接口
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface CartService {

	/**
	 * 
	 * @author yangbin
	 * @date 2018年3月22日
	 * @param cart
	 * @return 
	 * @return
	 */
	void add(Cart cart);

	/**
	 * 获取购物车列表
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	List<CartVo> queryCartByCart(Cart cart);

	/**
	 * 从购物车中删除商品 
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	void deleteProductFromCart(Cart cart);

	/**
	 * 更新购物车
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	void updateCartByEntity(Cart cart);

	/**
	 * 获取购物车订单信息
	 * @author yangbin
	 * @date 2018年3月27日
	 * @param cartId
	 * @return
	 */
	CartOrderCheckShowVo queryCartOrderByCartId(String cartId);

	/**
	 * 删除购物车中的商品
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param productSpecification
	 * @return
	 */
	void deleteProductByProductSpecificationId(ProductSpecification productSpecification);

}
