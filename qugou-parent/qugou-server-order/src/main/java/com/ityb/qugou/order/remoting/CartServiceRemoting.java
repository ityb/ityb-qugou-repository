package com.ityb.qugou.order.remoting;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.po.product.ProductSpecification;

@FeignClient(value = "cart-service")
public interface CartServiceRemoting {

	/**
	 * 根据商品规格ID删除对应的
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param productSpecification
	 */
	@PostMapping(value="/cart/deleteProductByProductSpecificationId")
	JsonResultData<Boolean> deleteProductByProductSpecificationId(@RequestBody ProductSpecification productSpecification);

	
}
