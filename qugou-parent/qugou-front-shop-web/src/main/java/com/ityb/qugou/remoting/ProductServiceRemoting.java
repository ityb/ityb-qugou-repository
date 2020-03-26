package com.ityb.qugou.remoting;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.po.product.ProductDesc;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.vo.search.ProductSearchVo;

@FeignClient(value = "product-service")
public interface ProductServiceRemoting {

	/**
	 * 得到对应的商品规格列表
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param specification
	 * @return
	 */
	@PostMapping(value="/product/querySpecification")
	JsonResultData<List<ProductSpecification>> querySpecification(@RequestBody ProductSpecification specification);

	/**
	 * 获取商品描述
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param productDesc
	 * @return
	 */
	@PostMapping(value="/product/getProductDesc")
	JsonResultData<ProductDesc> getProductDesc(@RequestBody ProductDesc productDesc);
	
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
	 * 获取热点商品
	 * @author yangbin
	 * @date 2018年5月1日
	 * @param productDto
	 * @return
	 */
	@PostMapping(value="/product/queryHotProduct")
	JsonResultData<List<ProductSearchVo>> queryHotProduct(@RequestBody ProductDto productDto);
	
}
