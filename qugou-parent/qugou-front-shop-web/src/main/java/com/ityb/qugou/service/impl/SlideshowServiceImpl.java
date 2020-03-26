package com.ityb.qugou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.annotation.RedisCache;
import com.ityb.qugou.base.enums.CacheType;
import com.ityb.qugou.constant.RedisConstants;
import com.ityb.qugou.mapper.SlideshowMapper;
import com.ityb.qugou.po.manager.Slideshow;
import com.ityb.qugou.service.SlideshowService;

/**
 * 轮播图管理
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class SlideshowServiceImpl implements SlideshowService{

	@Autowired
	private SlideshowMapper slideshowMapper;
	/**
	 * 添加缓存切面
	 * 获取轮播图列表
	 * @author yangbin
	 * @date 2018年2月14日
	 * @param slideshow
	 * @return
	 */
	@Override
	@RedisCache(cacheType=CacheType.KeyValue,keyName=RedisConstants.SLIDESHOW_KEY,expireSeconds=RedisConstants.EXPIRESECONDS)
	public List<Slideshow> querySlideshow(Slideshow slideshow) {
		List<Slideshow> list = slideshowMapper.querySlideshow(slideshow);
		return list;
	}
	
}
