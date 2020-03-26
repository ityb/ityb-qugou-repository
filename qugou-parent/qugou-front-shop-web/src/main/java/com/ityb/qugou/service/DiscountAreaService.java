package com.ityb.qugou.service;

import java.util.List;

import com.ityb.qugou.po.manager.DiscountArea;
import com.ityb.qugou.vo.manager.DiscountAreaVo;

/**
 * 优惠专区service
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface DiscountAreaService {

	/**
	 * 获取优惠区域
	 * @author yangbin
	 * @date 2018年5月1日
	 * @param discountArea
	 * @return
	 */
	List<DiscountAreaVo> queryDiscountArea(DiscountArea discountArea);

}
