package com.ityb.qugou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.AddressUtils;
import com.ityb.qugou.base.utils.NumberUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.common.ShopBaseController;
import com.ityb.qugou.po.cart.Cart;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.remoting.CartServiceRemoting;
import com.ityb.qugou.remoting.ProductServiceRemoting;
import com.ityb.qugou.vo.cart.CartVo;

/**
 * 购物车控制器
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/shop/cart")
public class CartController extends ShopBaseController{

	@Autowired
	private CartServiceRemoting cartServiceRemoting;
	@Autowired
	private ProductServiceRemoting productServiceRemoting;
	/**
	 * 跳转到购物车页面
	 * @author yangbin
	 * @date 2018年3月20日
	 * @return
	 */
	@RequestMapping(value="",method={RequestMethod.GET,RequestMethod.POST})
	public String cart(HttpServletRequest request,HttpServletResponse response,Model model,Cart cart){
		 setSearchHeader(request, response, model);
		 //获取全部的商品规格
		 if(!NumberUtils.isNotNull(cart.getCartType())){
			 cart.setCartType(1);
		 }
		 cart.setCreater(getCurrentUserId(request));
		 cart.setAddress(AddressUtils.addressBeanToStr(getAddress(request), false, false, false));
		 JsonResultData<List<CartVo>> jsonResultList=cartServiceRemoting.queryCartByCart(cart);
		 model.addAttribute("cartList",jsonResultList.getData());
		 model.addAttribute("cart",cart);
		return "/cart/cart-list";
	}
	
	/**
	 * 添加到购物车
	 * @author yangbin
	 * @date 2018年3月22日
	 * @param cart
	 * @return
	 */
	@RequestMapping(value="/add",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> add(HttpServletRequest request,Cart cart){
		cart.setCreater(getCurrentUserId(request));
		if(!NumberUtils.isNotNull(cart.getCartType())){
			return JsonResultData.error("购物车的类型不能为空");
		}
		if(StringUtils.isBlank(cart.getSpecificationId())){
			return JsonResultData.error("商品规格不能为空");
		}
		if(!NumberUtils.isNotNull(cart.getBuyNumber())){
			return JsonResultData.error("购买数量不能为空");
		}
		ProductSpecification specification=new ProductSpecification();
		specification.setId(cart.getSpecificationId());
		JsonResultData<ProductSpecification>  specificationJsonResult= this.productServiceRemoting.getProductSpecification(specification);
		ProductSpecification productSpecification = specificationJsonResult.getData();
		if(productSpecification!=null&&cart.getBuyNumber()>productSpecification.getStock()){
			cart.setBuyNumber(productSpecification.getStock());
		}
		JsonResultData<Boolean> jsonResult=cartServiceRemoting.add(cart);
		return jsonResult;
	}
	
	/**
	 * 删除商品从购物车中
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param request
	 * @param cart {商品规格ID 为字符串拼接起来中间中逗号隔开}
	 * @return
	 */
	@RequestMapping(value="/deleteProduct",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> deleteProduct(HttpServletRequest request,Cart cart){
		cart.setCreater(getCurrentUserId(request));
		if(StringUtils.isBlank(cart.getId())){
			return JsonResultData.error("购物车对应的ID不能为空");
		}
		JsonResultData<Boolean> jsonResult=cartServiceRemoting.deleteProductFromCart(cart);
		return jsonResult;
	}
	
	/**
	 * 更新购买数量
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param request
	 * @param cart
	 * @return
	 */
	@RequestMapping(value="/updateBuyNumber",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> updateBuyNumber(HttpServletRequest request,Cart cart){
		cart.setCreater(getCurrentUserId(request));
		if(StringUtils.isBlank(cart.getId())){
			return JsonResultData.error("购物车对应的ID不能为空");
		}
		if(NumberUtils.isNull(cart.getBuyNumber())){
			return JsonResultData.error("购买数量不能为空");
		}
		JsonResultData<Boolean> jsonResult=cartServiceRemoting.updateCartByEntity(cart);
		return jsonResult;
	}
}
