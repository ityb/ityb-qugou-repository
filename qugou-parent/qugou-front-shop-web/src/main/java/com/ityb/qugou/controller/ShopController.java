package com.ityb.qugou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.dto.search.ProductSearchDto;
import com.ityb.qugou.dto.search.ShopSearchDto;
import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.remoting.ProductServiceRemoting;
import com.ityb.qugou.remoting.SearchServiceRemoting;
import com.ityb.qugou.service.ProductCategoryService;
import com.ityb.qugou.service.ShopService;
import com.ityb.qugou.vo.search.ProductSearchVo;
import com.ityb.qugou.vo.shop.ProductCategoryVo;

/**
 * 商店服务类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping(value = "/shop/store")
public class ShopController extends ShopBaseController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private SearchServiceRemoting searchServiceRemoting;
	@Autowired
	private ProductServiceRemoting productServiceRemoting;

	/**
	 * 获取商店详细数据
	 * 
	 * @author yangbin
	 * @date 2018年4月1日
	 * @return
	 */
	@RequestMapping(value = "/detail",method={RequestMethod.POST,RequestMethod.GET})
	public String detail(HttpServletRequest request, HttpServletResponse response, Model model, ShopSearchDto shopSearchDto) {
		setSearchHeader(request, response, model);
		/*
		 * 1.根据ID获取对应的商店信息
		 */
		Shop shop=new Shop();
		shop.setId(shopSearchDto.getId());
		Shop shopEntity=this.shopService.getShop(shop);
		model.addAttribute("shop",shopEntity);
		/*
		 * 2.获取商店分类信息
		 */
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCreater(shopEntity.getUserId());
		productCategory.setType(2);
		List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryMerchantProductCategory(productCategory);
		model.addAttribute("productCategoryList", productCategoryVoList);
		
		/*
		 *3.获取商店信息
		 */
		ProductSearchDto productSearchDto=new ProductSearchDto();
		productSearchDto.setShopName(shopEntity.getShopName());
		productSearchDto.setKeyWord(shopSearchDto.getKeyWord());
		productSearchDto.setMerchantCategoryName(shopSearchDto.getMerchantCategoryName());
		productSearchDto.setShopId(shopSearchDto.getId());
		productSearchDto.setAddress(AddressUtils.addressBeanToStr(getAddress(request), false, false, false));
		JsonResultData<List<ProductSearchVo>> jsonResultData = searchServiceRemoting.search(productSearchDto);
		model.addAttribute("productList",jsonResultData.getData());
		JsonResultData<Integer> jsonresultDataCount=searchServiceRemoting.countSearch(productSearchDto);
		model.addAttribute("totalRecord",jsonresultDataCount.getData());
		model.addAttribute("isPage",1);
		/*
		 * 4.将搜索条件传入页面
		 */
		model.addAttribute("shopSearchDto",shopSearchDto);
		return "/shop/shop-detail";
	}
	
	/**
	 * 获取热卖爆款商品
	 * @author yangbin
	 * @date 2018年5月16日
	 * @param request
	 * @param response
	 * @param model
	 * @param shopSearchDto
	 * @return
	 */
	@RequestMapping(value = "/detail/hot",method={RequestMethod.POST,RequestMethod.GET})
	public String hotProduct(HttpServletRequest request, HttpServletResponse response, Model model, ShopSearchDto shopSearchDto) {
		setSearchHeader(request, response, model);
		/*
		 * 1.根据ID获取对应的商店信息
		 */
		Shop shop=new Shop();
		shop.setId(shopSearchDto.getId());
		Shop shopEntity=this.shopService.getShop(shop);
		model.addAttribute("shop",shopEntity);
		/*
		 * 2.获取商店分类信息
		 */
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCreater(shopEntity.getUserId());
		productCategory.setType(2);
		List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryMerchantProductCategory(productCategory);
		model.addAttribute("productCategoryList", productCategoryVoList);
		
		ProductDto productDto=new ProductDto();
		productDto.setAddress(CookieUtils.getCookieValue(request, CookieConstants.CUSTOMER_ADDRESS_KEY,true));
		productDto.setQueryCount(50);
		productDto.setShopId(shopSearchDto.getId());
		JsonResultData<List<ProductSearchVo>> jsonResultData =productServiceRemoting.queryHotProduct(productDto);
		model.addAttribute("productList",jsonResultData.getData());
		model.addAttribute("totalRecord",50);
		/*
		 * 4.将搜索条件传入页面
		 */
		model.addAttribute("shopSearchDto",shopSearchDto);
		return "/shop/shop-detail";
	}
	
	/**
	 * 获取最新商品
	 * @author yangbin
	 * @date 2018年5月16日
	 * @param request
	 * @param response
	 * @param model
	 * @param shopSearchDto
	 * @return
	 */
	@RequestMapping(value = "/detail/recent",method={RequestMethod.POST,RequestMethod.GET})
	public String newProduct(HttpServletRequest request, HttpServletResponse response, Model model, ShopSearchDto shopSearchDto) {
		setSearchHeader(request, response, model);
		/*
		 * 1.根据ID获取对应的商店信息
		 */
		Shop shop=new Shop();
		shop.setId(shopSearchDto.getId());
		Shop shopEntity=this.shopService.getShop(shop);
		model.addAttribute("shop",shopEntity);
		/*
		 * 2.获取商店分类信息
		 */
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCreater(shopEntity.getUserId());
		productCategory.setType(2);
		List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryMerchantProductCategory(productCategory);
		model.addAttribute("productCategoryList", productCategoryVoList);
		
		ProductSearchDto productSearchDto=new ProductSearchDto();
		//补全搜索属性
		productSearchDto.setPageNow(1);
		productSearchDto.setPageSize(50);
		productSearchDto.setAddTimeOrder(2);//表示降序排序
		productSearchDto.setShopId(shopSearchDto.getId());
		productSearchDto.setAddress(CookieUtils.getCookieValue(request, CookieConstants.CUSTOMER_ADDRESS_KEY,true));
		JsonResultData<List<ProductSearchVo>> jsonResultData = searchServiceRemoting.search(productSearchDto);
		model.addAttribute("productList",jsonResultData.getData());
		model.addAttribute("totalRecord",50);
		/*
		 * 4.将搜索条件传入页面
		 */
		model.addAttribute("shopSearchDto",shopSearchDto);
		return "/shop/shop-detail";
	}
}
