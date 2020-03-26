package com.ityb.qugou.product.controller.api;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ityb.qugou.base.constant.CommonConstants;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.bo.product.ProductBean;
import com.ityb.qugou.bo.product.ProductDetailDescVo;
import com.ityb.qugou.bo.product.ProductSpecificationBean;
import com.ityb.qugou.bo.product.SpecificationBean;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.dto.product.ProductSpecificationDto;
import com.ityb.qugou.po.product.Product;
import com.ityb.qugou.po.product.ProductDesc;
import com.ityb.qugou.po.product.ProductDetail;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.product.facade.productServiceFacade;
import com.ityb.qugou.product.service.ProductService;
import com.ityb.qugou.product.service.ProductSpecificationService;
import com.ityb.qugou.vo.product.ProductSpecificationVo;
import com.ityb.qugou.vo.product.ProductVo;
import com.ityb.qugou.vo.search.ProductSearchVo;

/**
 * product相关api
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@RestController
public class ProductController {

	@Autowired
	private ProductSpecificationService productSpecificationService;
	@Autowired
	private ProductService productService;
	@Autowired
	private productServiceFacade productServiceFacade;
	private final static Logger LOGGER = Logger.getLogger(ProductController.class);

	/**
	 * 查询商品规格列表
	 * @author yangbin
	 * @date 2018年1月22日
	 * @param productSpecificationDto
	 * @return
	 */
	@RequestMapping(value = "/product/queryProductSpecification", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<List<ProductSpecificationVo>> queryProductSpecification(
			@RequestBody ProductSpecificationDto productSpecificationDto) {
		if (productSpecificationDto.getPageIndex() != null && productSpecificationDto.getPageIndex() <= 0) {
			productSpecificationDto.setPageIndex(1);
		}
		if (productSpecificationDto.getPageSize() != null && productSpecificationDto.getPageSize() <= 0) {
			productSpecificationDto.setPageSize(CommonConstants.DEFAULT_PAGE_SIZE);
		}
		if(productSpecificationDto.getPageSize()!=null&&productSpecificationDto.getPageIndex()!=null){
			productSpecificationDto.setPageStart((productSpecificationDto.getPageIndex() - 1) * productSpecificationDto.getPageSize());
		}
		try {
			List<ProductSpecificationVo> resultList = this.productSpecificationService
					.queryProductSpecification(productSpecificationDto);
			return JsonResultData.success("获取数据成功", resultList);
		} catch (ServiceException e) {
			LOGGER.error("queryProductSpecification....获取商品规格列表失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}

	/**
	 * 获取产品规格数量
	 * @author yangbin
	 * @date 2018年1月26日
	 * @param productSpecificationDto
	 * @return
	 */
	@RequestMapping(value = "/product/countProductSpecification", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Integer> countProductSpecification(
			@RequestBody ProductSpecificationDto productSpecificationDto) {
		try {
			int count= this.productSpecificationService.countProductSpecification(productSpecificationDto);
			return JsonResultData.success("获取数据成功", count);
		} catch (ServiceException e) {
			LOGGER.error("countProductSpecification....获取商品规格列表数量...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	/**
	 * 添加商品规格列表
	 * @author yangbin
	 * @date 2018年1月27日
	 * @param productSpecificationBean
	 * @return
	 */
	@RequestMapping(value = "/product/addProductSpecification", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Boolean> addProductSpecification(
			@RequestBody ProductSpecificationBean productSpecificationBean) {
		try {
			this.productSpecificationService.addProductSpecification(productSpecificationBean);
			return JsonResultData.success("获取数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("addProductSpecification....添加商品规格失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 得到一个商品
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/product/getProduct",method={ RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Product> getProduct(@RequestBody Product product){
		try {
			Product productEntity = this.productService.getProduct(product);
			return JsonResultData.success("获取数据成功", productEntity);
		} catch (ServiceException e) {
			LOGGER.error("getProduct....获取商品信息失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	/**
	 * 获取商品列表
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/product/queryProduct",method={ RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<List<Product>> queryProduct(@RequestBody Product product){
		try {
			List<Product> productList = this.productService.queryProduct(product);
			return JsonResultData.success("获取数据成功", productList);
		} catch (ServiceException e) {
			LOGGER.error("getProduct....获取商品列表失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	/**
	 * 获取商品编号下面的商品规格数量
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/product/countSpecification",method={ RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Integer> countSpecification(@RequestBody Product product){
		try {
			int count=this.productSpecificationService.countSpecification(product);
			return JsonResultData.success("获取数据成功", count);
		} catch (ServiceException e) {
			LOGGER.error("countSpecification....获取商品规格数量失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	/**
	 * 得到商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/product/getProductSpecification",method={ RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<ProductSpecification> getProductSpecification(@RequestBody ProductSpecification productSpecification){
		try {
			ProductSpecification productSpecificationEntity=this.productSpecificationService.getProductSpecification(productSpecification);
			return JsonResultData.success("获取数据成功", productSpecificationEntity);
		} catch (ServiceException e) {
			LOGGER.error("getProductSpecification....获取商品规格失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 添加一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	@RequestMapping(value="/product/addSpecification",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<Boolean> addSpecification(@RequestBody ProductSpecification productSpecification){
		try {
			this.productSpecificationService.addSpecification(productSpecification);
			return JsonResultData.success("获取数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("getProductSpecification....添加商品规格失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 根据规格ids获取对应的规格信息
	 * @author yangbin
	 * @date 2018年2月4日
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/product/queryProductByIds",method={ RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<List<ProductSpecificationVo>> queryProductByIds(@RequestBody String ids){
		try {
			List<ProductSpecificationVo> list=this.productSpecificationService.queryProductByIds(ids);
			return JsonResultData.success("获取数据成功", list);
		} catch (ServiceException e) {
			LOGGER.error("getProductSpecification...根据商品ids获取商品失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 更新一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	@RequestMapping(value="/product/updateSpecification",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<Boolean> updateSpecification(@RequestBody ProductSpecification productSpecification){
		try {
			this.productSpecificationService.updateSpecification(productSpecification);
			return JsonResultData.success("更新数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("getProductSpecification....更新商品规格失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 删除一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	@RequestMapping(value="/product/deleteSpecification",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<Boolean> deleteSpecification(@RequestBody ProductSpecification productSpecification){
		try {
			this.productSpecificationService.deleteSpecification(productSpecification);
			return JsonResultData.success("删除数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("getProductSpecification....删除商品规格失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 根据dto获取对应的商品信息
	 * @author yangbin
	 * @date 2018年2月8日
	 * @param productDto
	 * @return
	 */
	@RequestMapping(value="/product/queryProductByProductDto",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<List<ProductVo>> queryProductByProductDto(@RequestBody ProductDto productDto){
		try {
			List<ProductVo> list=this.productService.queryProductByProductDto(productDto);
			return JsonResultData.success("获取数据成功", list);
		} catch (ServiceException e) {
			LOGGER.error("queryProductByProductDto....获取商品列表失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 根据dto 查询商品列表的数量
	 * @author yangbin
	 * @date 2018年2月8日
	 * @param productDto
	 * @return
	 */
	@RequestMapping(value="/product/countProductByProductDto",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<Integer> countProductByProductDto(@RequestBody ProductDto productDto){
		try {
			Integer count=this.productService.countProductByProductDto(productDto);
			return JsonResultData.success("获取数据成功", count);
		} catch (ServiceException e) {
			LOGGER.error("countProductByProductDto....获取商品列表数量失败",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 根据商品po得到对应的规格列表
	 * @author yangbin
	 * @date 2018年2月9日
	 * @param request
	 * @param model
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/product/querySpecificationByPoduct",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<List<SpecificationBean>> querySpecificationByPoduct(@RequestBody Product product){
		try {
			List<SpecificationBean> list=this.productSpecificationService.querySpecificationByPoduct(product);
			return JsonResultData.success("获取数据成功", list);
		} catch (ServiceException e) {
			LOGGER.error("querySpecificationByPoduct....根据商品po得到对应的规格列表失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	/**
	 * 添加商品信息
	 * @author yangbin
	 * @date 2018年2月9日
	 * @param request
	 * @param model
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/product/addProduct",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<Boolean> addProduct(@RequestBody ProductBean productBean){
		try {
			this.productServiceFacade.addProduct(productBean);
			return JsonResultData.success("添加数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("addProduct....添加商品信息失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	/**
	 * 修改商品信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productBean
	 * @return
	 */
	@RequestMapping(value="/product/updateProduct",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<Boolean> updateProduct(@RequestBody ProductBean productBean){
		try {
			this.productServiceFacade.updateProduct(productBean);
			return JsonResultData.success("修改数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("updateProduct....修改商品信息失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 查询商品细节
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productDetail
	 * @return
	 */
	@RequestMapping(value="/product/getProductDetil",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<ProductDetail> getProductDetil(@RequestBody ProductDetail productDetail){
		try {
			ProductDetail productDetailEntity=this.productService.getProductDetil(productDetail);
			return JsonResultData.success("获取数据成功", productDetailEntity);
		} catch (ServiceException e) {
			LOGGER.error("queryProductDetil....查询商品细节失败....",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取商品描述根据商品Id
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productDesc
	 * @return
	 */
	@RequestMapping(value="/product/getProductDesc",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<ProductDesc> getProductDesc(@RequestBody ProductDesc productDesc){
		try {
			ProductDesc productDescEntity=this.productService.getProductDesc(productDesc);
			return JsonResultData.success("获取数据成功", productDescEntity);
		} catch (ServiceException e) {
			LOGGER.error("queryProductDetil....查询商品描述失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	

	/**
	 * 获取商品详细描述等信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/product/getProductDetailDesc",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<ProductDetailDescVo> getProductDetailDesc(@RequestBody Product product){
		try {
			ProductDetailDescVo productDetailDescVo=this.productServiceFacade.getProductDetailDesc(product);
			return JsonResultData.success("获取数据成功", productDetailDescVo);
		} catch (ServiceException e) {
			LOGGER.error("getProductDetailDesc....查询商品详细失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	/**
	 * 删除一个商品信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/product/deleteProduct",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<Boolean> deleteProduct(@RequestBody Product product){
		try {
			this.productServiceFacade.deleteProduct(product);
			return JsonResultData.success("删除数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("deleteProduct....删除商品信息失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 更新商品状态
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/product/updateStateByProductIds",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<Boolean> updateStateByProductIds(@RequestBody List<String> productIdList,Integer state){
		try {
			this.productService.updateStateByProductIds(productIdList,state);
			return JsonResultData.success("更新数据成功", true);
		} catch (ServiceException e) {
			LOGGER.error("deleteProduct....更新商品信息失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取对应的商品规格信息
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param productSpecification
	 * @return
	 */
	@RequestMapping(value="/product/querySpecification",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<List<ProductSpecification>> querySpecification(@RequestBody ProductSpecification productSpecification){
		try {
			List<ProductSpecification> list=this.productSpecificationService.querySpecification(productSpecification);
			return JsonResultData.success("获取数据成功", list);
		} catch (ServiceException e) {
			LOGGER.error("querySpecification....获取商品规格信息失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 得到热销商品
	 * @author yangbin
	 * @date 2018年5月2日
	 * @param productDto
	 * @return
	 */
	@RequestMapping(value="/product/queryHotProduct",method={ RequestMethod.POST, RequestMethod.GET })
	JsonResultData<List<ProductSearchVo>> queryHotProduct(@RequestBody ProductDto productDto){
		try {
			List<ProductSearchVo> list=this.productService.queryHotProduct(productDto);
			return JsonResultData.success("获取数据成功", list);
		} catch (ServiceException e) {
			LOGGER.error("querySpecification....获取商品规格信息失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
}
