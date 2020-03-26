package com.ityb.qugou.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.bo.search.ProductSearchBean;
import com.ityb.qugou.dto.search.ProductSearchDto;
import com.ityb.qugou.search.mapper.ProductMapper;
import com.ityb.qugou.search.service.ProductService;

/**
 * 商品信息service
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductMapper productMapper;
	
	/**
	 * 根据搜索商品的地址，得到对应的商品信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productSearchDto
	 * @return
	 */
	@Override
	public List<ProductSearchBean> queryProductForSearch(ProductSearchDto productSearchDto) {
		return productMapper.queryProductForSearch(productSearchDto);
	}

}
