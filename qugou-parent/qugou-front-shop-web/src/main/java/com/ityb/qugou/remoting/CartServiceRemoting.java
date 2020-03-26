package com.ityb.qugou.remoting;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.po.cart.Cart;
import com.ityb.qugou.vo.cart.CartOrderCheckShowVo;
import com.ityb.qugou.vo.cart.CartVo;

@FeignClient(value = "cart-service")
public interface CartServiceRemoting {

	/**
	 * 添加购物车
	 * @author yangbin
	 * @date 2018年3月22日
	 * @param cart
	 * @return
	 */
	@PostMapping(value="/cart/add")
	JsonResultData<Boolean> add(@RequestBody Cart cart);
	
	/**
	 * 获取购物车中的商品列表
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	@PostMapping(value="/cart/queryCartByCart")
	JsonResultData<List<CartVo>> queryCartByCart(@RequestBody Cart cart);

	/**
	 * 删除商品从购物车中
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param request
	 * @param cart {商品规格ID 为字符串拼接起来中间中逗号隔开}
	 * @return
	 */
	@PostMapping(value="/cart/deleteProductFromCart")
	JsonResultData<Boolean> deleteProductFromCart(@RequestBody Cart cart);

	/**
	 * 更新一个购物车
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	@PostMapping(value="/cart/updateCartByEntity")
	JsonResultData<Boolean> updateCartByEntity(@RequestBody Cart cart);
	
	/**
	 * 获取购物车订单信息
	 * @author yangbin
	 * @date 2018年3月27日
	 * @param cartId
	 * @return
	 */
	@PostMapping(value="/cart/queryCartOrderByCartId")
	JsonResultData<CartOrderCheckShowVo> queryCartOrderByCartId(@RequestParam("cartId") String cartId);
	
}
