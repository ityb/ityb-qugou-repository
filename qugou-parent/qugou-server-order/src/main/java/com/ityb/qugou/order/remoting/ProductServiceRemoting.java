package com.ityb.qugou.order.remoting;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.po.product.ProductSpecification;

@FeignClient(value = "product-service")
public interface ProductServiceRemoting {

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
	 * 更新一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	@PostMapping(value="/product/updateSpecification")
	JsonResultData<Boolean> updateSpecification(@RequestBody ProductSpecification productSpecification);
}
