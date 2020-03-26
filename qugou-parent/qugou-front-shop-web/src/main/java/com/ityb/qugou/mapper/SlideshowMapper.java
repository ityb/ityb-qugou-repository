package com.ityb.qugou.mapper;

import java.util.List;

import com.ityb.qugou.po.manager.Slideshow;

/**
 * 轮播图mapper
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface SlideshowMapper {

	/**
	 * 获取轮播图列表
	 * @author yangbin
	 * @date 2018年2月14日
	 * @param slideshow
	 * @return
	 */
	List<Slideshow> querySlideshow(Slideshow slideshow);

}
