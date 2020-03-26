package com.ityb.qugou.cart.controller.api;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.cart.service.CartService;
import com.ityb.qugou.po.cart.Cart;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.vo.cart.CartOrderCheckShowVo;
import com.ityb.qugou.vo.cart.CartVo;

/**
 * 购物车相关服务
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	private final static Logger LOGGER = Logger.getLogger(CartController.class);
	
	/**
	 * 添加到购物车
	 * @author yangbin
	 * @date 2018年3月22日
	 * @param cart
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Boolean> add(@RequestBody Cart cart){
		try {
			this.cartService.add(cart);
			return JsonResultData.success("添加数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("add....添加购物车失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取购物车列表
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	@RequestMapping(value = "/queryCartByCart", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<List<CartVo>> queryCartByCart(@RequestBody Cart cart){
		try {
			List<CartVo> list=this.cartService.queryCartByCart(cart);
			return JsonResultData.success("添加数据成功", list);
		} catch (ServiceException e) {
			LOGGER.error("queryCartByCart....获取购物车数据失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 从购物车中删除商品 
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	@RequestMapping(value = "/deleteProductFromCart", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Boolean> deleteProductFromCart(@RequestBody Cart cart){
		try {
			this.cartService.deleteProductFromCart(cart);
			return JsonResultData.success("添加数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("deleteProductFromCart....在购物车中删除商品失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 更新购物车
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	@RequestMapping(value = "/updateCartByEntity", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Boolean> updateCartByEntity(@RequestBody Cart cart){
		try {
			this.cartService.updateCartByEntity(cart);
			return JsonResultData.success("添加数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("updateCartByEntity....更新购物车失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取购物车订单信息
	 * @author yangbin
	 * @date 2018年3月27日
	 * @param cartId
	 * @return
	 */
	@RequestMapping(value = "/queryCartOrderByCartId", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<CartOrderCheckShowVo> queryCartOrderByCartId(@RequestParam("cartId") String cartId){
		try {
			CartOrderCheckShowVo  cartOrderCheckShowVo=this.cartService.queryCartOrderByCartId(cartId);
			return JsonResultData.success("获取数据成功", cartOrderCheckShowVo);
		} catch (ServiceException e) {
			LOGGER.error("queryCartOrderByCartId....获取购物车订单信息失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 删除购物车中的商品
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param productSpecification
	 * @return
	 */
	@RequestMapping(value = "/deleteProductByProductSpecificationId", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Boolean> deleteProductByProductSpecificationId(@RequestBody ProductSpecification productSpecification){
		try {
			this.cartService.deleteProductByProductSpecificationId(productSpecification);
			return JsonResultData.success("获取数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("deleteProductByProductSpecificationId....删除购物车订单信息失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
}
