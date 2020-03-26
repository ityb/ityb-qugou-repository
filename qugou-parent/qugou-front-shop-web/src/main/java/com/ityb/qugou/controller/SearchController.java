package com.ityb.qugou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.AddressUtils;
import com.ityb.qugou.base.utils.CookieUtils;
import com.ityb.qugou.common.ShopBaseController;
import com.ityb.qugou.constant.CookieConstants;
import com.ityb.qugou.constant.ShopConstants;
import com.ityb.qugou.dto.search.ProductSearchDto;
import com.ityb.qugou.dto.shop.ShopDto;
import com.ityb.qugou.remoting.SearchServiceRemoting;
import com.ityb.qugou.service.ShopService;
import com.ityb.qugou.vo.search.ProductSearchVo;
import com.ityb.qugou.vo.shop.ShopVo;

/**
 * 商品控制器
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/shop/search")
public class SearchController extends ShopBaseController{
	@Autowired
	private SearchServiceRemoting searchServiceRemoting;
	@Autowired
	private ShopService shopService;
	private final static Logger LOGGER = Logger.getLogger(SearchController.class);
	/**
	 * 根据搜索条件进行搜索
	 * 
	 * @author yangbin
	 * @date 2018年3月18日
	 * @param model
	 * @param productSearchDto
	 * @return
	 */
	@RequestMapping(value = "/product", method = { RequestMethod.GET, RequestMethod.POST })
	public String search(HttpServletRequest request,HttpServletResponse response,Model model,ProductSearchDto productSearchDto) {
		setSearchHeader(request, response, model);
		/*
		 * 进行商品搜索
		 */
		try {
			//补全搜索属性
			productSearchDto.setAddress(CookieUtils.getCookieValue(request, CookieConstants.CUSTOMER_ADDRESS_KEY,true));
			JsonResultData<List<ProductSearchVo>> jsonResultData = searchServiceRemoting.search(productSearchDto);
			model.addAttribute("productList",jsonResultData.getData());
			JsonResultData<Integer> jsonresultDataCount=searchServiceRemoting.countSearch(productSearchDto);
			model.addAttribute("totalRecord",jsonresultDataCount.getData());
		} catch (Exception e) {
			LOGGER.error("search....搜索商品失败",e);
		}
		//将查询的条件放在model中
		model.addAttribute("searchCondition",productSearchDto);
		//搜索类型
		model.addAttribute("searchCategorytype",ShopConstants.SEARCH_CATEGORY_TYPE_PRODUCT);
		return "search/product-search";
	}
	
	/**
	 * 跳转到周边商城
	 * @author yangbin
	 * @date 2018年3月31日
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/shop",method={RequestMethod.POST,RequestMethod.GET})
	public String around(ShopDto shopDto,HttpServletRequest request,HttpServletResponse response,Model model){
		setSearchHeader(request, response, model);
		//精确到市
		shopDto.setAddress(AddressUtils.addressBeanToStr(getAddress(request), false, false, false));
		String x = CookieUtils.getCookieValue(request,  CookieConstants.CUSTOMER_ADDRESS_POINT_X_KEY);
		shopDto.setX(Double.parseDouble(x));
		String y=CookieUtils.getCookieValue(request,  CookieConstants.CUSTOMER_ADDRESS_POINT_Y_KEY);
		shopDto.setY(Double.parseDouble(y));
		shopDto.setDistance(ShopConstants.DELIVERY_DISTANCE);
		List<ShopVo> shopList=shopService.queryShopByDto(shopDto);
		model.addAttribute("shopList",shopList);
		Integer totalRecord=this.shopService.countShopByDto(shopDto);
		model.addAttribute("totalRecord",totalRecord);
		model.addAttribute("searchCategorytype",ShopConstants.SEARCH_CATEGORY_TYPE_SHOP);
		model.addAttribute("shopSearch",shopDto);
		return "/shop/shop-list";
	}
}
