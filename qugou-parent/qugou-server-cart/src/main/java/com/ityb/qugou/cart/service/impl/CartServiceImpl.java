package com.ityb.qugou.cart.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.NumberUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.cart.mapper.CartMapper;
import com.ityb.qugou.cart.service.CartService;
import com.ityb.qugou.po.cart.Cart;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.vo.cart.CartOrderCheckShowVo;
import com.ityb.qugou.vo.cart.CartOrderCheckVo;
import com.ityb.qugou.vo.cart.CartOrderProductVo;
import com.ityb.qugou.vo.cart.CartVo;

/**
 * cart对应的服务实现类
 * @author yangbin
 * @param <CartOrderProductVo>
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartMapper cartMapper;
	/**
	 * 添加到购物车
	 * @author yangbin
	 * @date 2018年3月22日
	 * @param cart
	 * @return
	 */
	@Override
	public void add(Cart cart) {
		Assert.hasText(cart.getCreater(),"添加人不能为空");
		Assert.hasText(cart.getSpecificationId(),"添加的商品不能为空");
		Assert.NumberIsNotNull(cart.getBuyNumber(), "购买的数量不能为空");
		Assert.NumberIsNotNull(cart.getCartType(), "购物车类型不正确");
		Assert.NumberIsNotNull(cart.getCartType(), "购物车类型不能为空");
		//判断是否已经存在了该商品
		//判断添加的商品是否超过了库存量
		List<Cart> cartList=this.cartMapper.queryCartByEntity(cart);
		if(CollectionUtils.isNotEmpty(cartList)){//如果今日购物车已经存在则修改数量
			this.cartMapper.updateByEntity(cart);
		}else{//进行新添加
			cart.setId(StringUtils.getRandomStr());
			cart.setCtime(new Date());
			cartMapper.insertCart(cart);
		}
	}
	/**
	 * 获取购物车列表
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	@Override
	public List<CartVo> queryCartByCart(Cart cart) {
		Assert.NumberIsNotNull(cart.getCartType(),"购物车类型不能为空");
		Assert.hasText(cart.getCreater(),"用户ID不能为空");
		Assert.hasText(cart.getAddress(),"用户所在的地址不能为空");
		return cartMapper.queryCartByCart(cart);
	}
	
	/**
	 * 从购物车中删除商品 
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	@Override
	public void deleteProductFromCart(Cart cart) {
		Assert.hasText(cart.getId(),"购物车ID不能为空");
		List<String> idList=Arrays.asList(cart.getId().split("\\s*,\\s*"));
		this.cartMapper.deleteProductFromCart(idList);
	}
	
	/**
	 * 更新购物车
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param cart
	 * @return
	 */
	@Override
	public void updateCartByEntity(Cart cart) {
		Assert.hasText(cart.getId(),"购物车ID不能为空");
		Assert.NumberIsNotNull(cart.getBuyNumber(), "购买数量不能为空");
		this.cartMapper.updateCartByEntity(cart);
	}
	
	/**
	 * 获取购物车订单信息
	 * @author yangbin
	 * @date 2018年3月27日
	 * @param cartId
	 * @return
	 */
	@Override
	public CartOrderCheckShowVo queryCartOrderByCartId(String cartId) {
		Assert.hasText(cartId,"购物车ID不能为空");
		CartOrderCheckShowVo cartOrderCheckShowVo=new CartOrderCheckShowVo();
		List<String> cartIdList=Arrays.asList(cartId.split("\\s*,\\s*"));
		List<CartVo> cartVoList=this.cartMapper.queryCartByCartIds(cartIdList);
		Map<String,List<CartVo>> cartVoMap=CartVoListToCartVoMap(cartVoList); 
		List<CartOrderCheckVo> result=new ArrayList<>();
		Integer totalNumber=0;
		Double totalWeight=0D;
		Double totalMoney=0D;
		for (Map.Entry<String, List<CartVo>> entry : cartVoMap.entrySet()) {
			String key=entry.getKey();
			List<CartVo> value=entry.getValue();
			CartOrderCheckVo cartOrderCheckVo=new CartOrderCheckVo();
			cartOrderCheckVo.setShopId(key);
			cartOrderCheckVo.setStartPrice(0D);
			List<CartOrderProductVo> cartOrderProductList=new ArrayList<>();
			Double subTotal=0D;
			Integer subTotalNumber=0;
			Double subTotalWeight=0D;
			for (CartVo cartVo : value) {
				if(StringUtils.isBlank(cartOrderCheckVo.getShopName())){
					cartOrderCheckVo.setShopName(cartVo.getShopName());
				}
				if(NumberUtils.isNull(cartOrderCheckVo.getStartPrice())){
					cartOrderCheckVo.setStartPrice(cartVo.getStartPrice());
				}
				if(StringUtils.isBlank(cartOrderCheckVo.getMerchantId())){
					cartOrderCheckVo.setMerchantId(cartVo.getMerchantId());
				}
				CartOrderProductVo cartOrderProductVo=new CartOrderProductVo();
				cartOrderProductVo.setBuyNumber(cartVo.getBuyNumber());
				cartOrderProductVo.setPrice(cartVo.getPrice());
				cartOrderProductVo.setProductPicUrl(cartVo.getProductPicUrl());
				cartOrderProductVo.setProductSpecificationId(cartVo.getProductSpecificationId());
				cartOrderProductVo.setProductSpecificationName(cartVo.getProductSpecificationName());
				cartOrderProductVo.setProductTitle(cartVo.getProductTitle());
				cartOrderProductVo.setSellPoint(cartVo.getSellPoint());
				cartOrderProductVo.setProductId(cartVo.getProductId());
				cartOrderProductVo.setStock(cartVo.getStock());
				cartOrderProductVo.setWeight(cartVo.getWeight());
				subTotal+=(cartVo.getPrice()*cartVo.getBuyNumber());
				cartOrderProductList.add(cartOrderProductVo);
				subTotalNumber+=cartVo.getBuyNumber();
				subTotalWeight+=cartVo.getWeight()*cartVo.getBuyNumber();
			}
			if(subTotal>=cartOrderCheckVo.getStartPrice()){
				totalNumber+=subTotalNumber;
				totalWeight+=subTotalWeight;
				totalMoney+=subTotal;
				cartOrderCheckVo.setCartOrderType(1);//1.表示满足起步价
			}else{
				cartOrderCheckVo.setCartOrderType(2);//2.表示未满足起步价
			}
			cartOrderCheckVo.setSubTotal(subTotal);
			cartOrderCheckVo.setOrderProductVoList(cartOrderProductList);
			result.add(cartOrderCheckVo);
		}
		cartOrderCheckShowVo.setCartOrderCheckVoList(result);
		cartOrderCheckShowVo.setTotalNumber(totalNumber);
		cartOrderCheckShowVo.setTotalWeight(totalWeight);
		cartOrderCheckShowVo.setTotalMoney(totalMoney);
		return cartOrderCheckShowVo;
	}
	/**
	 * 将对应的list转为map
	 * @author yangbin
	 * @date 2018年3月27日
	 * @param cartVoList
	 * @return
	 */
	private Map<String, List<CartVo>> CartVoListToCartVoMap(List<CartVo> cartVoList) {
		Map<String,List<CartVo>>  map=new TreeMap<>();
		if(CollectionUtils.isEmpty(cartVoList)){
			return map;
		}
		for (CartVo cartVo : cartVoList) {
			if(map.containsKey(cartVo.getShopId())){
				map.get(cartVo.getShopId()).add(cartVo);
			}else{
				List<CartVo> list=new ArrayList<>();
				list.add(cartVo);
				map.put(cartVo.getShopId(), list);
			}
		}
		return map;
	}
	
	/**
	 * 删除购物车中的商品
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param productSpecification
	 * @return
	 */
	@Override
	public void deleteProductByProductSpecificationId(ProductSpecification productSpecification) {
		Assert.hasText(productSpecification.getId(),"商品规格ID不能为空");
		Assert.hasText(productSpecification.getCreater(),"购物车所属人不能为空");
		Cart cart=new Cart();
		cart.setCreater(productSpecification.getCreater());
		cart.setSpecificationId(productSpecification.getId());
		cart.setCartType(1);//删除当天的购物车中的信息
		this.cartMapper.deleteProductByCart(cart);
	}
}
