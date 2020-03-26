package com.ityb.qugou.remoting;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.ityb.qugou.base.message.JsonResultData;

@FeignClient(value = "search-service")
public interface SearchServiceRemoting {

	/**
	 * 导入商品搜索索引到solr环境
	 * @author yangbin
	 * @date 2018年2月11日
	 * @return
	 */
	@PostMapping(value="/search/import")
	JsonResultData<Boolean> importIndex();
	
}
