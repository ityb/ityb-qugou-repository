package com.ityb.qugou.search.facade;

/**
 * 搜索服务service接口
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface SearchServiceFacade {

	/**
	 * 导入商品信息到solr服务器
	 * @author yangbin
	 * @date 2018年2月11日
	 */
	void importIndex();

	/**
	 * 添加一条记录到solr服务器
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 */
	void addProductToSearchByProductId(String productId);

	/**
	 * 更新一条记录到solr服务器
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 */
	void updateProductToSearchByProductId(String text);

	/**
	 * 从solr服务器中删除一条信息
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param text
	 */
	void deleteProductToSearchByProductId(String text);
}
