package com.ityb.qugou.contoller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ityb.qugou.base.constant.StatusConstants;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.bo.common.FileBean;
import com.ityb.qugou.bo.common.UploadResultBean;
import com.ityb.qugou.bo.product.ProductBean;
import com.ityb.qugou.bo.product.ProductDetailDescVo;
import com.ityb.qugou.bo.product.SpecificationBean;
import com.ityb.qugou.common.FileUploadService;
import com.ityb.qugou.common.MerchantBaseController;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.po.product.Product;
import com.ityb.qugou.po.product.ProductDesc;
import com.ityb.qugou.po.product.ProductDetail;
import com.ityb.qugou.remoting.ProductServiceRemoting;
import com.ityb.qugou.service.CategoryService;
import com.ityb.qugou.vo.merchant.CategoryVo;
import com.ityb.qugou.vo.merchant.ResultVo;
import com.ityb.qugou.vo.product.ProductVo;

/**
 * 商品孔控制器
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/merchant/product")
public class ProductController extends MerchantBaseController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductServiceRemoting productServiceRemoting;
	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * 跳转到商品列表界面
	 * 
	 * @author yangbin
	 * @date 2018年2月5日
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model) {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setType(2);//表示是商城分类
		productCategory.setCreater(getCurrentUserId(request));
		List<CategoryVo> categroyVoList = categoryService.queryCategoryForOption(productCategory);
		model.addAttribute("categoryList", categroyVoList);
		return "product-list";
	}

	/**
	 * 跳转到商品列表界面
	 * 
	 * @author yangbin
	 * @date 2018年2月5日
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	@ResponseBody
	public ResultVo<ProductVo> list(HttpServletRequest request, Model model, ProductDto productDto) {
		productDto.setCreater(getCurrentUserId(request));
		// 向远程服务端去获取数据
		JsonResultData<List<ProductVo>> jsonResultDataList = this.productServiceRemoting.queryProduct(productDto);
		// 向远程服务端区获取商品查询数量
		JsonResultData<Integer> jsonResultDataCount = this.productServiceRemoting.countProduct(productDto);
		if (jsonResultDataList.getStatus() == StatusConstants.STATE_FAIL
				|| jsonResultDataCount.getStatus() == StatusConstants.STATE_FAIL) {
			return ResultVo.error(jsonResultDataList.getMsg());
		}
		return ResultVo.success(jsonResultDataList.getData(), jsonResultDataCount.getData());
	}

	/**
	 * 得到一个商品
	 * 
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/getProduct", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResultData<Product> getProduct(HttpServletRequest request, Product product) {
		product.setCreater(getCurrentUserId(request));
		JsonResultData<Product> jsonResultData = productServiceRemoting.getProduct(product);
		if (jsonResultData.getStatus() == StatusConstants.STATE_FAIL) {
			return JsonResultData.error("获取数据失败");
		}
		return JsonResultData.success(jsonResultData.getData());
	}

	/**
	 * 跳转到商品信息添加页面
	 * 
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String addProduct(HttpServletRequest request, Model model) {
		Product product = new Product();
		product.setCreater(getCurrentUserId(request));
		JsonResultData<List<Product>> jsonResultData = this.productServiceRemoting.queryProduct(product);
		//获取商品列表
		if (!CollectionUtils.isEmpty(jsonResultData.getData())) {
			model.addAttribute("productList", jsonResultData.getData());
		}
		//获取商城分类列表
		ProductCategory productCategory = new ProductCategory();
		productCategory.setType(1);//表示的商城分类
		List<CategoryVo> shopCategoryVoList=this.categoryService.queryCategoryForOption(productCategory);
		model.addAttribute("shopCategoryList", shopCategoryVoList);
		//获取商店分类列表
		productCategory.setType(2);//表示的商城分类
		productCategory.setCreater(getCurrentUserId(request));
		List<CategoryVo> merchantCategroyVoList = categoryService.queryCategoryForOption(productCategory);
		model.addAttribute("merchantCategoryList", merchantCategroyVoList);
		return "product-add";
	}

	/**
	 * 进行商品添加
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	@ResponseBody
	public JsonResultData<Boolean> addProduct(HttpServletRequest request, ProductBean productBean) {
		//保存到商品服务
		productBean.setAddDate(new Date());
		productBean.setCreater(getCurrentUserId(request));
		JsonResultData<Boolean> jsonResultData=this.productServiceRemoting.addProduct(productBean);
		if(jsonResultData.getStatus()==StatusConstants.STATE_FAIL){
			return JsonResultData.error(jsonResultData.getMsg());
		}
		return JsonResultData.success(true);
	}

	/**
	 * 上传图片
	 * 
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/upload", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public UploadResultBean upload(HttpServletRequest request, Model model) {
		MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
		FileBean fileBean = fileUploadService.upload(file);
		UploadResultBean uploadResultBean = null;
		if (fileBean == null) {
			uploadResultBean = new UploadResultBean();
			uploadResultBean.setCode(-1);// 表示成功
			uploadResultBean.setMsg("上传文件失败");
			return uploadResultBean;
		}
		uploadResultBean = new UploadResultBean();
		uploadResultBean.setCode(0);// 表示成功
		uploadResultBean.setMsg("上传文件成功");
		Map<String, Object> map = new HashMap<>();
		map.put("src", fileBean.getPath());
		map.put("title", fileBean.getOriginalFileName());
		uploadResultBean.setData(map);
		return uploadResultBean;
	}
	
	/**
	 * 查询商品细节
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/getProductDetil", method = { RequestMethod.POST })
	@ResponseBody
	public JsonResultData<ProductDetail> queryProductDetil(HttpServletRequest request, ProductDetail productDetail) {
		//保存到商品服务
		JsonResultData<ProductDetail> jsonResultData=this.productServiceRemoting.getProductDetil(productDetail);
		if(jsonResultData.getStatus()==StatusConstants.STATE_FAIL){
			return JsonResultData.error(jsonResultData.getMsg());
		}
		return JsonResultData.success(jsonResultData.getData());
	}
	
	/**
	 * 跳转到商品信息添加页面
	 * 
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/update/{productId}/{productName}", method = { RequestMethod.GET })
	public String updateProduct(HttpServletRequest request, Model model,@PathVariable String productId,@PathVariable String productName) {
		Product productEntity=new Product();
		productEntity.setId(productId);
		productEntity.setName(productName);
		//得到商品名称
		model.addAttribute("product",productEntity);
		ProductDetail productDetail=new ProductDetail();
		productDetail.setProductId(productId);
		//获取商品细节
		JsonResultData<ProductDetail> detilJsonData = this.productServiceRemoting.getProductDetil(productDetail);
		model.addAttribute("productDetail",detilJsonData.getData());
		//获取商品以及商品规格
		Product product=new Product();
		product.setId(productId);
		product.setState(1);//表示以及添加进去的状态
		JsonResultData<List<SpecificationBean>> specificationListJsonData = this.productServiceRemoting.querySpecificationByPoduct(product);
		model.addAttribute("specificationList",specificationListJsonData.getData());
		//获取商品描述
		ProductDesc productDesc=new ProductDesc();
		productDesc.setProductId(productId);
		JsonResultData<ProductDesc> descJsonData=this.productServiceRemoting.getProductDesc(productDesc);
		model.addAttribute("productDesc",descJsonData.getData());
		//获取商城分类列表
		ProductCategory productCategory = new ProductCategory();
		productCategory.setType(1);//表示的商城分类
		List<CategoryVo> shopCategoryVoList=this.categoryService.queryCategoryForOption(productCategory);
		model.addAttribute("shopCategoryList", shopCategoryVoList);
		//获取商店分类列表
		productCategory.setType(2);//表示的商城分类
		productCategory.setCreater(getCurrentUserId(request));
		List<CategoryVo> merchantCategroyVoList = categoryService.queryCategoryForOption(productCategory);
		model.addAttribute("merchantCategoryList", merchantCategroyVoList);
		return "product-update";
	}
	/**
	 * 更新商品信息
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public JsonResultData<Boolean> updateProduct(HttpServletRequest request, ProductBean productBean) {
		//保存到商品服务
		productBean.setModifyDate(new Date());
		productBean.setUpdater(getCurrentUserId(request));
		JsonResultData<Boolean> jsonResultData=this.productServiceRemoting.updateProduct(productBean);
		if(jsonResultData.getStatus()==StatusConstants.STATE_FAIL){
			return JsonResultData.error(jsonResultData.getMsg());
		}
		return JsonResultData.success(true);
	}
	
	/**
	 * 跳转到浏览商品信息页面
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param request
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/lookProduct", method = { RequestMethod.GET})
	public String lookProduct(HttpServletRequest request, String productId,Model model) {
	 Product product=new Product();
	 product.setId(productId);
	 JsonResultData<ProductDetailDescVo> jsonResultData=this.productServiceRemoting.getProductDetailDesc(product);
	 model.addAttribute("productDetailDesc",jsonResultData.getData());
	 return "product-look";
	}
	
	/**
	 * 删除一个商品
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param request
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/delete",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public  JsonResultData<Boolean> deleteProduct(HttpServletRequest request,Model model,String productId){
		if(StringUtils.isBlank(productId)){
			return JsonResultData.error("商品ID不能为空");
		}
		Product product=new Product(); 
		product.setId(productId);
		product.setDeleter(getCurrentUserId(request));
		product.setDtime(new Date());
		JsonResultData<Boolean> jsonResultData=this.productServiceRemoting.deleteProduct(product);
		if(jsonResultData.getStatus()==StatusConstants.STATE_FAIL){
			return JsonResultData.error(jsonResultData.getMsg());
		}
		return  JsonResultData.success(jsonResultData.getData());
	}
	
	/**
	 * 更新商品状态根据商品ID
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param request
	 * @param model
	 * @param productIds
	 * @return
	 */
	@RequestMapping(value="/updateProductState",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public  JsonResultData<Boolean> upperProduct(HttpServletRequest request,Model model,String productIds,Integer state){
		if(StringUtils.isBlank(productIds)){
			return JsonResultData.error("商品ID不能为空");
		}
		if(state==null||(state!=1&&state!=0)){
			return JsonResultData.error("商品ID不能为空");
		}
		List<String> productIdList=Arrays.asList(productIds.split("\\s*,\\s*"));
		JsonResultData<Boolean> jsonResultData=this.productServiceRemoting.updateStateByProductIds(productIdList,state);
		if(jsonResultData.getStatus()==StatusConstants.STATE_FAIL){
			return JsonResultData.error(jsonResultData.getMsg());
		}
		return  JsonResultData.success(jsonResultData.getData());
	}
}
