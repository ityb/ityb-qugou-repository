package com.ityb.qugou.cart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ityb.qugou.po.cart.Cart;
import com.ityb.qugou.vo.cart.CartVo;

/**
 * 对应表t_p_cart
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface CartMapper {

	/**
	 * 插入一条记录到表中
	 * @author yangbin
	 * @date 2018年3月22日
	 * @param cart
	 */
	void insertCart(Cart cart);

	/**
	 * 根据实体查询购物车
	 * @author yangbin
	 * @date 2018年3月22日
	 * @param cart
	 * @return
	 */
	List<Cart> queryCartByEntity(Cart cart);

	/**
	 * 更新购物车
	 * @author yangbin
	 * @date 2018年3月22日
	 * @param cart
	 */
	void updateByEntity(Cart cart);

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
	void deleteProductFromCart(@Param("idList")List<String> idList);

	/**
	 * 更新购物车
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	void updateCartByEntity(Cart cart);

	/**
	 * 根据购物车Id列表查询购物车信息
	 * @author yangbin
	 * @date 2018年3月27日
	 * @param cartIdList
	 * @return
	 */
	List<CartVo> queryCartByCartIds(@Param("cartIdList")List<String> cartIdList);

	/**
	 * 删除商品从购物车中
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param cart
	 */
	void deleteProductByCart(Cart cart);

}
