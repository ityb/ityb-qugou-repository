package com.ityb.qugou.search.remoting;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "product-service")
public interface ProductServiceRemoting {
	
}
