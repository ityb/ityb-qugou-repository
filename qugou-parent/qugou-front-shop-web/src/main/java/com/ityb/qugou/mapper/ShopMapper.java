package com.ityb.qugou.mapper;

import java.util.List;

import com.ityb.qugou.dto.shop.ShopDto;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.vo.shop.ShopVo;

public interface ShopMapper {

	/**
	 * 获取商店信息
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
