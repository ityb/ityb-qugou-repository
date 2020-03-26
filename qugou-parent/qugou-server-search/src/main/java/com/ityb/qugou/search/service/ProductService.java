package com.ityb.qugou.search.service;

import java.util.List;

import com.ityb.qugou.bo.search.ProductSearchBean;
import com.ityb.qugou.dto.search.ProductSearchDto;

/**
 * 商品搜索服务
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface ProductService {

	
	/**
	 * 根据搜索商品的地址，得到对应的商品信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productSearchDto
	 * @return
	 */
	List<ProductSearchBean> queryProductForSearch(ProductSearchDto productSearchDto);

}
