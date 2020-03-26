package com.ityb.qugou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.cache.CacheService;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.constant.RedisConstants;
import com.ityb.qugou.mapper.DirectSupermarketMapper;
import com.ityb.qugou.po.manager.DirectSupermarket;
import com.ityb.qugou.service.DirectSupermarketService;
import com.ityb.qugou.vo.manager.DirectSupermarketVo;

/**
 * 直通超市服务实现类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class DirectSupermarketServiceImpl implements DirectSupermarketService{

	@Autowired
	private DirectSupermarketMapper directSupermarketMapper;
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 获取直通超市列表
	 * @author yangbin
	 * @date 2018年5月1日
	 * @param directSupermarket
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DirectSupermarketVo> queryDirectSupermarket(DirectSupermarket directSupermarket) {
		Assert.hasText(directSupermarket.getCity(),"直通超市的所属区域不能为空");
		if(cacheService.isKeyExists(RedisConstants.DIRECT_SUPERMARKET_KEY+"_"+directSupermarket.getCity())){
			return (List<DirectSupermarketVo>)cacheService.get(RedisConstants.DIRECT_SUPERMARKET_KEY+"_"+directSupermarket.getCity());
		}
		List<DirectSupermarketVo> list=directSupermarketMapper.queryDirectSupermarket(directSupermarket);
		cacheService.put(RedisConstants.DIRECT_SUPERMARKET_KEY+"_"+directSupermarket.getCity(), list);
		cacheService.expire(RedisConstants.DIRECT_SUPERMARKET_KEY+"_"+directSupermarket.getCity(), RedisConstants.EXPIRESECONDS);
		return list;
	}
}
