package com.ityb.qugou.service.content;

import java.util.List;

import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.po.manager.DiscountArea;

public interface DiscountAreaService extends BaseService<DiscountArea>{

	/**
	 * 获取数据
	 * @author yangbin
	 * @date 2018年4月14日
	 * @param discountArea
	 * @return
	 */
	List<DiscountArea> query(DiscountArea discountArea);

}
