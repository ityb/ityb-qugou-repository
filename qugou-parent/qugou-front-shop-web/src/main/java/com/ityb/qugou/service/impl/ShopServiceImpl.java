package com.ityb.qugou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.constant.ShopConstants;
import com.ityb.qugou.dto.shop.ShopDto;
import com.ityb.qugou.mapper.ShopMapper;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.service.ShopService;
import com.ityb.qugou.vo.shop.ShopVo;

/**
 * 商店服务
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class ShopServiceImpl implements ShopService{

	@Autowired
	private ShopMapper shopMapper;
	/**
	 * 获取商店信息
	 */
	@Override
	public Shop getShop(Shop shop) {
		Assert.hasText(shop.getId(),"商店ID不能为空");
		return shopMapper.getShop(shop);
	}
	
	/**
	 * 获取商店信息根据Dto
	 * @author yangbin
	 * @date 2018年3月31日
	 * @param shopDto
	 * @return
	 */
	@Override
	public List<ShopVo> queryShopByDto(ShopDto shopDto) {
		if (shopDto.getPageIndex() == null || shopDto.getPageIndex() <= 0) {
			shopDto.setPageIndex(ShopConstants.DEFAULT_PAGE_NOW);
		}
		if (shopDto.getPageSize() == null || shopDto.getPageSize() <= 0) {
			shopDto.setPageSize(ShopConstants.DEFAULT_PAGE_SIZE);
		}
		shopDto.setPageStart((shopDto.getPageIndex() - 1) * shopDto.getPageSize());
		return shopMapper.queryShopByDto(shopDto);
	}
	/**
	 * 查询商店的条数
	 * @author yangbin
	 * @date 2018年3月31日
	 * @param shopDto
	 * @return
	 */
	@Override
	public Integer countShopByDto(ShopDto shopDto) {
		return shopMapper.countShopByDto(shopDto);
	}
}
