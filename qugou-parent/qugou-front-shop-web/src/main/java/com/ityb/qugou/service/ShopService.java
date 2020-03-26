package com.ityb.qugou.service;

import java.util.List;

import com.ityb.qugou.dto.shop.ShopDto;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.vo.shop.ShopVo;

/**
 * 商店服务service
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface ShopService {

	/**
	 * 获取商品信息
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param shop
	 * @return
	 */
	Shop getShop(Shop shop);

	/**
	 * 获取商店信息根据Dto
	 * @author yangbin
	 * @date 2018年3月31日
	 * @param shopDto
	 * @return
	 */
	List<ShopVo> queryShopByDto(ShopDto shopDto);

	/**
	 * 查询商店的条数
	 * @author yangbin
	 * @date 2018年3月31日
	 * @param shopDto
	 * @return
	 */
	Integer countShopByDto(ShopDto shopDto);

}
