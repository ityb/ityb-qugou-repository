package com.ityb.qugou.search.service;

import java.util.List;

import com.ityb.qugou.bo.search.ProductSearchBean;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.dto.search.ProductSearchDto;
import com.ityb.qugou.vo.search.ProductSearchVo;

/**
 * 搜索服务service接口
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface SearchService {

	/**
	 * 导入商品进入solr服务器
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param list
	 */
	void importProduct(List<ProductSearchBean> list);

	/**
	 * 添加一条记录到solr服务器
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 */
	void addProductToSearch(List<ProductSearchBean> list);

	/**
	 * 从solr服务器中删除一条信息
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param text
	 */
	void deleteProductToSearchByProductId(List<String> productIdList);

	/**
	 * 商品搜索
	 * @author yangbin
	 * @date 2018年3月17日
	 * @param productSearchDto
	 * @return
	 */
	List<ProductSearchVo> search(ProductSearchDto productSearchDto);

	/**
	 * 商品搜索通过分类（包括商城分类，商家分类）
	 * @author yangbin
	 * @date 2018年3月18日
	 * @param productSearchDto
	 * @return
	 */
	List<ProductSearchVo> searchByCategory(ProductSearchDto productSearchDto);

	/**
	 * 根据商品ID得到对应的商品信息
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param productId
	 * @return
	 */
	ProductSearchVo searchByProductId(String productId);

	/**
	 * 计算搜索的条数
	 * @author yangbin
	 * @date 2018年4月1日
	 * @param productSearchDto
	 * @return
	 */
	Integer countSearch(ProductSearchDto productSearchDto);

	/**
	 * 获取推荐商品
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param productDto
	 * @return
	 */
	List<ProductSearchVo> queryRecommendProduct(ProductDto productDto);
}
