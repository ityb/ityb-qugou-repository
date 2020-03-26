package com.ityb.qugou.search.facade.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.bo.search.ProductSearchBean;
import com.ityb.qugou.dto.search.ProductSearchDto;
import com.ityb.qugou.search.facade.SearchServiceFacade;
import com.ityb.qugou.search.service.ProductService;
import com.ityb.qugou.search.service.SearchService;

/**
 * 搜索服务service实现类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class SearchServiceFacadeImpl implements SearchServiceFacade{
	@Autowired
	private ProductService productService;
	@Autowired
	private SearchService searchService;
	private final static Logger LOGGER = Logger.getLogger(SearchServiceFacadeImpl.class);
	/**
	 * 导入商品信息到solr服务器
	 * @author yangbin
	 * @date 2018年2月11日
	 */
	@Override
	public void importIndex() {
		ProductSearchDto productSearchDto=new ProductSearchDto();
		List<ProductSearchBean> list=this.productService.queryProductForSearch(productSearchDto);
		this.searchService.importProduct(list);
	}
	/**
	 * 添加一条记录到solr服务器
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 */
	@Override
	public void addProductToSearchByProductId(String productId) {
		Assert.hasText(productId,"商品ID不能为空");
		ProductSearchDto productSearchDto=new ProductSearchDto();
		productSearchDto.setProductIdList(Arrays.asList(productId.split("\\s*,\\s*")));
		List<ProductSearchBean> list=this.productService.queryProductForSearch(productSearchDto);
		this.searchService.addProductToSearch(list);
		LOGGER.info("addProductToSearchByProductId...导入到solr成功");
	}
	/**
	 * 更新一条记录到solr服务器
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 */
	@Override
	public void updateProductToSearchByProductId(String productId) {
		Assert.hasText(productId,"商品ID不能为空");
		/**
		 * 由于solr的添加一条记录，如果ID相同者直接进行更新，因此调用添加方法即可
		 */
		addProductToSearchByProductId(productId);
	}
	/**
	 * 从solr服务器中删除一条信息
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param text
	 */
	@Override
	public void deleteProductToSearchByProductId(String productId) {
		Assert.hasText(productId,"商品ID不能为空");
		List<String> productIdList = Arrays.asList(productId.split("\\s*,\\s*"));
		this.searchService.deleteProductToSearchByProductId(productIdList);
	}
}
