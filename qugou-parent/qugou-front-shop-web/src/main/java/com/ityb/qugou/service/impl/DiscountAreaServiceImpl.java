package com.ityb.qugou.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.annotation.RedisCache;
import com.ityb.qugou.base.enums.CacheType;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.constant.RedisConstants;
import com.ityb.qugou.mapper.DiscountAreaMapper;
import com.ityb.qugou.po.manager.DiscountArea;
import com.ityb.qugou.service.DiscountAreaService;
import com.ityb.qugou.vo.manager.DiscountAreaVo;

/**
 * 优惠区域服务实现类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class DiscountAreaServiceImpl implements DiscountAreaService {

	@Autowired
	private DiscountAreaMapper discountAreaMapper;

	/**
	 * 获取优惠区域
	 * @author yangbin
	 * @date 2018年5月1日
	 * @param discountArea
	 * @return
	 */
	@Override
	@RedisCache(cacheType=CacheType.KeyValue,keyName=RedisConstants.DISCOUNT_AREA_KEY,expireSeconds=RedisConstants.EXPIRESECONDS)
	public List<DiscountAreaVo> queryDiscountArea(DiscountArea discountArea) {
		List<DiscountArea> list = discountAreaMapper.queryDiscountArea(discountArea);
		List<DiscountAreaVo> discountAreaList=new ArrayList<>();
		for (DiscountArea item : list) {
			if(StringUtils.isBlank(item.getParentId())||"0".equals(item.getParentId())){
				DiscountAreaVo discountAreaVo=new DiscountAreaVo();
				discountAreaVo.setDiscountAreaParent(item);
				List<DiscountArea> childrenList=new ArrayList<>();
				for (DiscountArea children : list) {
					if(StringUtils.isNotBlank(children.getParentId())&&
							!"0".equals(children.getParentId())&&
							children.getParentId().equals(item.getId())){
						childrenList.add(children);
					}
				}
				discountAreaVo.setDiscountAreaChildren(childrenList);
				discountAreaList.add(discountAreaVo);
			}
		}
		return discountAreaList;
	}

}
