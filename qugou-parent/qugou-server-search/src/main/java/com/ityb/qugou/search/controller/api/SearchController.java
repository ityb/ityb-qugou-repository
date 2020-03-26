package com.ityb.qugou.search.controller.api;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.dto.search.ProductSearchDto;
import com.ityb.qugou.search.facade.SearchServiceFacade;
import com.ityb.qugou.search.service.SearchService;
import com.ityb.qugou.vo.search.ProductSearchVo;

/**
 * 商品搜索控制器
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@RestController
public class SearchController {
	@Autowired
	private SearchServiceFacade searchServiceFacade;
	@Autowired
	private SearchService searchService;
	
	private final static Logger LOGGER = Logger.getLogger(SearchController.class);
	/**
	 * 导入商品信息到solr搜索服务器中
	 * @author yangbin
	 * @date 2018年2月11日
	 * @return
	 */
	@RequestMapping(value = "/search/import", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResultData<Boolean> importIndex() {
		try {
			this.searchServiceFacade.importIndex();
		} catch (Exception e) {
			LOGGER.error("importIndex....导入商品信息进入solr服务器中失败");
			return JsonResultData.error(e.getMessage());
		}
		return JsonResultData.success(true);
	}
	
	/**
	 * 商品搜索 
	 * @author yangbin
	 * @date 2018年3月17日
	 * @param productSearchDto
	 * @return
	 */
	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResultData<List<ProductSearchVo>> search(@RequestBody ProductSearchDto productSearchDto) {
		try {
			List<ProductSearchVo> list=this.searchService.search(productSearchDto);
			return JsonResultData.success(list);
		} catch (Exception e) {
			LOGGER.error("search....商品搜索失败");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 根据商品ID得到对应的商品信息
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/searchByProductId", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResultData<ProductSearchVo> searchByProductId(@RequestBody String productId) {
		try {
			ProductSearchVo productSearchVo=this.searchService.searchByProductId(productId);
			return JsonResultData.success(productSearchVo);
		} catch (Exception e) {
			LOGGER.error("searchByProductId....根据商品ID搜索商品失败");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 商品搜索通过分类
	 * @author yangbin
	 * @date 2018年3月17日
	 * @param productSearchDto
	 * @return
	 */
	@RequestMapping(value = "/searchBycategory", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResultData<List<ProductSearchVo>> searchByCategory(@RequestBody ProductSearchDto productSearchDto) {
		try {
			List<ProductSearchVo> list=this.searchService.searchByCategory(productSearchDto);
			return JsonResultData.success(list);
		} catch (Exception e) {
			LOGGER.error("searchByCategory....商品搜索失败");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 计算搜索的条数
	 * @author yangbin
	 * @date 2018年4月1日
	 * @param productSearchDto
	 * @return
	 */
	@RequestMapping(value = "/countSearch", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResultData<Integer> countSearch(@RequestBody ProductSearchDto productSearchDto) {
		try {
			Integer count=this.searchService.countSearch(productSearchDto);
			return JsonResultData.success(count);
		} catch (Exception e) {
			LOGGER.error("countSearch....计算商品搜索的条数失败");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取推荐商品
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param productSearchDto
	 * @return
	 */
	@RequestMapping(value = "/queryRecommendProduct", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResultData<List<ProductSearchVo>> queryRecommendProduct(@RequestBody ProductDto productDto) {
		try {
			List<ProductSearchVo> list=this.searchService.queryRecommendProduct(productDto);
			return JsonResultData.success(list);
		} catch (Exception e) {
			LOGGER.error("queryRecommendProduct....获取用户的推荐商品");
			return JsonResultData.error(e.getMessage());
		}
	}
}
