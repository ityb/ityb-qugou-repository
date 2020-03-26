package com.ityb.qugou.mapper;

import java.util.List;

import com.ityb.qugou.po.manager.DiscountArea;

public interface DiscountAreaMapper {

	/**
	 * 获取优惠区域
	 * @author yangbin
	 * @date 2018年5月1日
	 * @param discountArea
	 * @return
	 */
	List<DiscountArea> queryDiscountArea(DiscountArea discountArea);

}
