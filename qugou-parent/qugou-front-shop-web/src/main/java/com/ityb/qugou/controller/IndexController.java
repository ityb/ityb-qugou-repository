package com.ityb.qugou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ityb.qugou.base.cache.CacheService;
import com.ityb.qugou.base.entity.bo.AddressBean;
import com.ityb.qugou.base.entity.bo.PointBean;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.AddressUtils;
import com.ityb.qugou.base.utils.CookieUtils;
import com.ityb.qugou.common.ShopBaseController;
import com.ityb.qugou.constant.CookieConstants;
import com.ityb.qugou.constant.RedisConstants;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.po.manager.Customer;
import com.ityb.qugou.po.manager.DirectSupermarket;
import com.ityb.qugou.po.manager.DiscountArea;
import com.ityb.qugou.po.manager.Notice;
import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.po.manager.Slideshow;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.remoting.ProductServiceRemoting;
import com.ityb.qugou.service.DirectSupermarketService;
import com.ityb.qugou.service.DiscountAreaService;
import com.ityb.qugou.service.NoticeService;
import com.ityb.qugou.service.ProductCategoryService;
import com.ityb.qugou.service.SlideshowService;
import com.ityb.qugou.service.UserService;
import com.ityb.qugou.vo.manager.DirectSupermarketVo;
import com.ityb.qugou.vo.manager.DiscountAreaVo;
import com.ityb.qugou.vo.search.ProductSearchVo;
import com.ityb.qugou.vo.shop.ShopProductCatgoryVo;

/**
 * 商城首页控制器
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping(value="/shop")
public class IndexController extends ShopBaseController{

	@Autowired
	private SlideshowService slideshowService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private UserService userService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private ProductServiceRemoting productServiceRemoting;
	@Autowired
	private DirectSupermarketService directSupermarketService;
	@Autowired
	private DiscountAreaService discountAreaService;
	
	/**
	 * 跳转到首页
	 * @author yangbin
	 * @date 2018年2月14日
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="",method={RequestMethod.POST,RequestMethod.GET})
	public String index(Model model,HttpServletRequest request,HttpServletResponse response){
		//设置搜索头信息
		setSearchHeader(request, response, model);
		//获取轮播图列表
		Slideshow  slideshow=new Slideshow();
		List<Slideshow> slideshowList=slideshowService.querySlideshow(slideshow);
		model.addAttribute("slideshowList",slideshowList);
		//获取商城的商品分类列表
		ProductCategory productCategory=new ProductCategory();
		productCategory.setType(1);//表示的是查询的商城分类
		List<ShopProductCatgoryVo> shopProductCatgoryList=productCategoryService.queryShopProductCategroy(productCategory);
		model.addAttribute("shopProductCatgoryList",shopProductCatgoryList);
		//获取公告列表
		Notice notice=new Notice();
		List<Notice> noticeList=noticeService.queryNotice(notice);
		model.addAttribute("noticeList",noticeList);
		//获取是否登录的数据
		User user = getUser(request);
		model.addAttribute("user", user);
		//根据用户去取用户详细信息
		Customer customer=this.userService.getCustomer(user);
		model.addAttribute("customer",customer);
		
		AddressBean addressBean = getAddress(request);
		//获取当前区域销量最高的商品
		ProductDto productDto=new ProductDto();
		productDto.setAddress(AddressUtils.addressBeanToStr(addressBean, false, false, false));
		if(cacheService.isKeyExists(RedisConstants.PRODUCT_HOT+"_"+productDto.getAddress())){
			model.addAttribute("hotProductList",(List<ProductSearchVo>)cacheService.get(RedisConstants.PRODUCT_HOT+"_"+productDto.getAddress()));
		}else{
			productDto.setQueryCount(10);
			JsonResultData<List<ProductSearchVo>> jsonResultData =productServiceRemoting.queryHotProduct(productDto);
			model.addAttribute("hotProductList",cacheService.get(RedisConstants.PRODUCT_HOT+"_"+productDto.getAddress()));
			cacheService.put(RedisConstants.PRODUCT_HOT+"_"+productDto.getAddress(), jsonResultData.getData());
		}
		DirectSupermarket directSupermarket=new DirectSupermarket();
		directSupermarket.setCity(addressBean.getCity());
		List<DirectSupermarketVo> supermarketList=directSupermarketService.queryDirectSupermarket(directSupermarket);
		model.addAttribute("supermarketList",supermarketList);
		//获取优惠专区
		DiscountArea discountArea=new DiscountArea();
		List<DiscountAreaVo> discountAreaList=discountAreaService.queryDiscountArea(discountArea);
		model.addAttribute("discountAreaList",discountAreaList);
		return "/index/index";
	}
	 
	/**
	 * 区域选择
	 * @author yangbin
	 * @date 2018年3月18日 
	 * @param addressBean
	 * @return
	 */
	@RequestMapping(value="/index/areaSelect",method={RequestMethod.POST,RequestMethod.GET})
	public String areaSelect(HttpServletResponse response,AddressBean addressBean,Model model,HttpServletRequest request){
		//修改缓存内容即可
		//根据地址去获取对应的平面坐标
		String address = AddressUtils.addressBeanToStr(addressBean, true, true, true);
		PointBean pointBean = AddressUtils.addressToPoint(address);
		addressBean.setPointBean(pointBean);
		String cookieValue = CookieUtils.getCookieValue(request, CookieConstants.CUSTOMER_PRIMARY_ID_KEY);
		cacheService.put(RedisConstants.CUSTOMER_ADDRESS_KEY+"_"+cookieValue, addressBean,60*60*24);
		//将信息存入cookie中
		CookieUtils.setCookie(response, CookieConstants.CUSTOMER_CITY_KEY, addressBean.getCity(),true);
		//获取经纬度
		CookieUtils.setCookie(response, CookieConstants.CUSTOMER_ADDRESS_KEY, AddressUtils.addressBeanToStr(addressBean, false, false, false),true);
		//设置经坐标
		CookieUtils.setCookie(response, CookieConstants.CUSTOMER_ADDRESS_POINT_X_KEY, addressBean.getPointBean().getX().toEngineeringString());
		
		CookieUtils.setCookie(response, CookieConstants.CUSTOMER_ADDRESS_POINT_Y_KEY, addressBean.getPointBean().getY().toEngineeringString());
		return "forward:/shop";
	}
	
	/**
	 * 跳转到错误页面
	 * @author yangbin
	 * @date 2018年3月20日
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/error",method={RequestMethod.POST,RequestMethod.GET})
	public String error(HttpServletRequest request,HttpServletResponse response,Model model){
		setSearchHeader(request, response, model);
		/*return "/error/shop-error";*/
		return "/error/error";
	}
	
	/**
	 * 跳转到关于我们页面
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/about",method={RequestMethod.POST,RequestMethod.GET})
	public String about(HttpServletRequest request,HttpServletResponse response,Model model){
		setSearchHeader(request, response, model);
		/*return "/error/shop-error";*/
		return "/index/about";
	}
}
