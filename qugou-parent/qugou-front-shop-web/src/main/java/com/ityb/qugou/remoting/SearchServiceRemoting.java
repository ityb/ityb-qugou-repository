package com.ityb.qugou.remoting;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.dto.search.ProductSearchDto;
import com.ityb.qugou.vo.search.ProductSearchVo;

/**
 * 商品搜索远程服务
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@FeignClient(value = "search-service")
public interface SearchServiceRemoting {
	
	/**
	 * 商品搜索
	 * @author yangbin
	 * @date 2018年3月17日
	 * @param productSearchDto
	 * @return
	 */
	@PostMapping(value="/search")
	public JsonResultData<List<ProductSearchVo>> search(@RequestBody ProductSearchDto productSearchDto);

	/**
	 * 通过商品ID查询对应的商品信息
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param id
	 * @return
	 */
	@PostMapping(value="/searchByProductId")
	public JsonResultData<ProductSearchVo> searchByProductId(@RequestBody String productId);

	/**
	 * 求得搜索到的记录数
	 * @author yangbin
	 * @date 2018年4月1日
	 * @param productSearchDto
	 * @return
	 */
	@PostMapping(value="/countSearch")
	public JsonResultData<Integer> countSearch(@RequestBody ProductSearchDto productSearchDto);

	/**
	 * 获取推荐商品列表
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param productDto
	 * @return
	 */
	@PostMapping(value="/queryRecommendProduct")
	public JsonResultData<List<ProductSearchVo>> queryRecommendProduct(@RequestBody ProductDto productDto);
}
