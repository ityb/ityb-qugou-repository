package com.ityb.qugou.remoting;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.ityb.qugou.vo.product.ProductSpecificationVo;
import com.ityb.qugou.vo.product.ProductVo;

@FeignClient(value = "product-service")
public interface ProductServiceRemoting {

	/**
	 * 查询商品规格库存列表
	 * 
	 * @author yangbin
	 * @date 2018年1月21日
	 * @param productSpecificationDto
	 * @return
	 */
	@PostMapping(value = "/product/queryProductSpecification")
	JsonResultData<List<ProductSpecificationVo>> queryProductSpecification(@RequestBody ProductSpecificationDto productSpecificationDto);

	/**
	 * 获取产品规格数量
	 * @author yangbin
	 * @date 2018年1月26日
	 * @param productSpecificationDto
	 * @return
	 */
	@PostMapping(value="/product/countProductSpecification")
	JsonResultData<Integer> countProductSpecification(@RequestBody ProductSpecificationDto productSpecificationDto);

	/**
	 * 添加商品规格信息（商品信息，商品规格信息）
	 * @author yangbin
	 * @date 2018年1月27日
	 * @return
	 */
	@PostMapping(value="/product/addProductSpecification")
	JsonResultData<Boolean> addProductSpecification(@RequestBody ProductSpecificationBean productSpecificationBean);
	
	/**
	 * 获取一个商品
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@PostMapping(value="/product/getProduct")
	JsonResultData<Product> getProduct(@RequestBody Product product);
	
	/**
	 * 查询商品列表
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@PostMapping(value="/product/queryProduct")
	JsonResultData<List<Product>> queryProduct(@RequestBody Product product);

	/**
	 * 获取商品编号下面的商品规格数量
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param product
	 * @return
	 */
	@PostMapping(value="/product/countSpecification")
	JsonResultData<Integer> countSpecification(@RequestBody Product product);

	/**
	 * 得到商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param product
	 * @return
	 */
	@PostMapping(value="/product/getProductSpecification")
	JsonResultData<ProductSpecification> getProductSpecification(@RequestBody ProductSpecification productSpecification);

	/**
	 * 添加一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	@PostMapping(value="/product/addSpecification")
	JsonResultData<Boolean> addSpecification(@RequestBody ProductSpecification productSpecification);

	/**
	 * 根据规格ID查询对应的信息
	 * @author yangbin
	 * @date 2018年2月4日
	 * @param ids
	 * @return
	 */
	@PostMapping(value="/product/queryProductByIds")
	JsonResultData<List<ProductSpecificationVo>> queryProductByIds(@RequestBody String ids);

	/**
	 * 更新一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	@PostMapping(value="/product/updateSpecification")
	JsonResultData<Boolean> updateSpecification(@RequestBody ProductSpecification productSpecification);

	/**
	 * 删除一个商品规格
	 * @author yangbin
	 * @date 2018年2月4日
	 * @param request
	 * @param model
	 * @param productSpecificationId
	 * @return
	 */
	@PostMapping(value="/product/deleteSpecification")
	JsonResultData<Boolean> deleteSpecification(@RequestBody ProductSpecification productSpecification);

	/**
	 * 根据dto 查询商品列表
	 * @author yangbin
	 * @date 2018年2月8日
	 * @param productDto
	 * @return
	 */
	@PostMapping(value="/product/queryProductByProductDto")
	JsonResultData<List<ProductVo>> queryProduct(@RequestBody ProductDto productDto);

	/**
	 * 根据dto 查询商品列表的数量
	 * @author yangbin
	 * @date 2018年2月8日
	 * @param productDto
	 * @return
	 */
	@PostMapping(value="/product/countProductByProductDto")
	JsonResultData<Integer> countProduct(@RequestBody ProductDto productDto);

	/**
	 * 根据商品po得到对应的规格列表
	 * @author yangbin
	 * @date 2018年2月9日
	 * @param request
	 * @param model
	 * @param product
	 * @return
	 */
	@PostMapping(value="/product/querySpecificationByPoduct")
	JsonResultData<List<SpecificationBean>> querySpecificationByPoduct(@RequestBody Product product);

	/**
	 * 进行商品添加
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@PostMapping(value="/product/addProduct")
	JsonResultData<Boolean> addProduct(@RequestBody ProductBean productBean);

	/**
	 * 查询商品细节
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@PostMapping(value="/product/getProductDetil")
	JsonResultData<ProductDetail> getProductDetil(@RequestBody ProductDetail productDetail);

	/**
	 * 获取商品描述
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param product
	 * @return
	 */
	@PostMapping(value="/product/getProductDesc")
	JsonResultData<ProductDesc> getProductDesc(@RequestBody ProductDesc productDesc);

	/**
	 * 更新商品信息
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@PostMapping(value="/product/updateProduct")
	JsonResultData<Boolean> updateProduct(@RequestBody ProductBean productBean);

	/**
	 * 获取商品详细描述等信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 * @return
	 */
	@PostMapping(value="/product/getProductDetailDesc")
	JsonResultData<ProductDetailDescVo> getProductDetailDesc(@RequestBody Product product);

	/**
	 * 删除一个商品
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param request
	 * @param model
	 * @param productId
	 * @return
	 */
	@PostMapping(value="/product/deleteProduct")
	JsonResultData<Boolean> deleteProduct(@RequestBody Product product);

	/**
	 * 更新商品状态根据商品ID
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param request
	 * @param model
	 * @param productIds
	 * @return
	 */
	@PostMapping(value="/product/updateStateByProductIds")
	JsonResultData<Boolean> updateStateByProductIds(@RequestBody List<String> productIdList,@RequestParam("state") Integer state);

}
