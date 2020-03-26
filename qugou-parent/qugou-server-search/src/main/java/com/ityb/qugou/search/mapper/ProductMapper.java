package com.ityb.qugou.search.mapper;

import java.util.List;

import com.ityb.qugou.bo.search.ProductSearchBean;
import com.ityb.qugou.dto.search.ProductSearchDto;

/**
 * 商品查询导入的search
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface ProductMapper {

	/**
	 * 根据搜索商品的地址，得到对应的商品信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productSearchDto
	 * @return
	 */
	List<ProductSearchBean> queryProductForSearch(ProductSearchDto productSearchDto);
}
