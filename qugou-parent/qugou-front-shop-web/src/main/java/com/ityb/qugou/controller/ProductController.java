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
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.common.ShopBaseController;
import com.ityb.qugou.constant.CookieConstants;
import com.ityb.qugou.dto.evaluation.EvaluationDto;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.dto.search.ProductSearchDto;
import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.po.product.BrowseHistory;
import com.ityb.qugou.po.product.ProductDesc;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.remoting.EvaluationServiceRemoting;
import com.ityb.qugou.remoting.ProductServiceRemoting;
import com.ityb.qugou.remoting.SearchServiceRemoting;
import com.ityb.qugou.service.BrowseHistoryService;
import com.ityb.qugou.service.ProductCategoryService;
import com.ityb.qugou.service.ShopService;
import com.ityb.qugou.vo.evaluation.EvaluationFractionVo;
import com.ityb.qugou.vo.search.ProductSearchVo;
import com.ityb.qugou.vo.shop.ProductCategoryVo;

/**
 * 商品控制器
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/shop/product")
public class ProductController extends ShopBaseController {
	@Autowired
	private ProductServiceRemoting productServiceRemoting;
	@Autowired
	private SearchServiceRemoting searchServiceRemoting;
	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private BrowseHistoryService browseHistoryService;
	@Autowired
	private EvaluationServiceRemoting evaluationServiceRemoting;
	private final static Logger LOGGER = Logger.getLogger(ProductController.class);

	/**
	 * 商品显示细节
	 * 
	 * @author yangbin
	 * @date 2017年12月31日
	 * @param productId
	 *            商品Id
	 * @return
	 */
	@RequestMapping(value = "/detail", method = { RequestMethod.POST, RequestMethod.GET })
	public String productDetail(HttpServletRequest request, HttpServletResponse response, Model model, String id) {
		setSearchHeader(request, response, model);

		/*
		 * 取得对应的商品信息(调用搜索微服务)
		 */
		JsonResultData<ProductSearchVo> jsonResultData = searchServiceRemoting.searchByProductId(id);
		ProductSearchVo productSearchVo = jsonResultData.getData();
		model.addAttribute("product", productSearchVo);
		String address = AddressUtils.addressBeanToStr(getAddress(request), false, false, false);
		if (productSearchVo==null||(StringUtils.isNotBlank(address) && StringUtils.isNotBlank(productSearchVo.getProductAddress())
				&& !productSearchVo.getProductAddress().startsWith(address))) {
			model.addAttribute("productFlag", "商品已下架或者已超过配送范围");//表示商品超出配送范围
			return "/product/product-detail";
		}
		/*
		 * 将浏览的信息添加到浏览信息表中
		 */
		User user = null;
		if ((user = getUser(request)) != null) {
			BrowseHistory browseHistory = new BrowseHistory();
			browseHistory.setCreater(user.getId());
			browseHistory.setShopCategoryName(productSearchVo.getShopCategory());
			browseHistory.setProductId(productSearchVo.getProductId());
			browseHistoryService.addBrowseHistory(browseHistory);
		}
		// 得到对应的商品规格信息
		ProductSpecification specification = new ProductSpecification();
		specification.setProductId(id);
		specification.setState(1);
		JsonResultData<List<ProductSpecification>> productSpecificationResult = productServiceRemoting
				.querySpecification(specification);
		model.addAttribute("productSpecificationList", productSpecificationResult.getData());
		// 获取商品描述信息
		ProductDesc productDesc = new ProductDesc();
		productDesc.setProductId(id);
		JsonResultData<ProductDesc> productDescResult = productServiceRemoting.getProductDesc(productDesc);
		model.addAttribute("productDesc", productDescResult.getData());
		// 获取商店信息
		Shop shop = new Shop();
		shop.setId(jsonResultData.getData().getShopId());
		shop = this.shopService.getShop(shop);
		model.addAttribute("shop", shop);
		// 获取商店内部分类
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCreater(shop.getUserId());
		productCategory.setType(2);
		List<ProductCategoryVo> productCategoryVoList = productCategoryService
				.queryMerchantProductCategory(productCategory);
		model.addAttribute("productCategoryList", productCategoryVoList);

		/*
		 * 获取评论分数
		 */
		EvaluationDto evaluationDto = new EvaluationDto();
		evaluationDto.setProductId(productSearchVo.getProductId());
		JsonResultData<EvaluationFractionVo> fractionJsonResult = this.evaluationServiceRemoting
				.getEvaluationFraction(evaluationDto);
		model.addAttribute("evaluationFraction", fractionJsonResult.getData());
		return "/product/product-detail";
	}

	/**
	 * 推荐商品列表
	 * 
	 * @author yangbin
	 * @date 2018年4月6日
	 * @return
	 */
	@RequestMapping(value = "/recommend", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRecommendProduct(HttpServletRequest request,HttpServletResponse response,Model model,ProductDto productDto) {
		setSearchHeader(request, response, model);
		//获取当前区域销量最高的商品
		productDto.setAddress(CookieUtils.getCookieValue(request, CookieConstants.CUSTOMER_ADDRESS_KEY,true));
		productDto.setPageIndex(1);
		productDto.setPageSize(50);
		productDto.setUserId(getCurrentUserId(request));
		if(StringUtils.isNotBlank(productDto.getUserId())){
			BrowseHistory browseHistory=new BrowseHistory();
			browseHistory.setCreater(productDto.getUserId());
			List<BrowseHistory> browseHistoryList=this.browseHistoryService.queryBrowseHistoryByEntity(browseHistory);
			productDto.setBrowseHistoryList(browseHistoryList);
		}
		JsonResultData<List<ProductSearchVo>> jsonResultData =searchServiceRemoting.queryRecommendProduct(productDto);
		model.addAttribute("productList",jsonResultData.getData());
		model.addAttribute("title","猜你喜欢");
		return "/product/product-list";
	}
	
	/**
	 * 跳转到热销商品列表
	 * @author yangbin
	 * @date 2018年5月8日
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hot",method={RequestMethod.POST,RequestMethod.GET})
	public String hotProduct(HttpServletRequest request,HttpServletResponse response,Model model,ProductDto productDto){
		setSearchHeader(request, response, model);
		//获取当前区域销量最高的商品
		productDto.setAddress(CookieUtils.getCookieValue(request, CookieConstants.CUSTOMER_ADDRESS_KEY,true));
		productDto.setQueryCount(50);
		JsonResultData<List<ProductSearchVo>> jsonResultData =productServiceRemoting.queryHotProduct(productDto);
		model.addAttribute("productList",jsonResultData.getData());
		model.addAttribute("title","热销热卖");
		return "/product/product-list";
	}
	
	/**
	 * 获取最新的商品信息
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/recent",method={RequestMethod.POST,RequestMethod.GET})
	public String recentProduct(HttpServletRequest request,HttpServletResponse response,Model model,ProductSearchDto productSearchDto){
		setSearchHeader(request, response, model);
		try {
			//补全搜索属性
			productSearchDto.setPageNow(1);
			productSearchDto.setPageSize(50);
			productSearchDto.setAddTimeOrder(2);//表示降序排序
			productSearchDto.setAddress(CookieUtils.getCookieValue(request, CookieConstants.CUSTOMER_ADDRESS_KEY,true));
			JsonResultData<List<ProductSearchVo>> jsonResultData = searchServiceRemoting.search(productSearchDto);
			model.addAttribute("productList",jsonResultData.getData());
		} catch (Exception e) {
			LOGGER.error("recentProduct....搜索商品失败",e);
		}
		model.addAttribute("title","新品上架");
		return "/product/product-list";
	}
	
}
