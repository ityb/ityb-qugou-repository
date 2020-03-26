package com.ityb.qugou.job.mapper;

import org.apache.ibatis.annotations.Param;

public interface JobMapper {

	/**
	 * 批量更新购物车的信息
	 * @author yangbin
	 * @date 2018年3月24日
	 */
	void batchUpdateStateJob();

	/**
	 * 批量取消订单
	 * @author yangbin
	 * @date 2018年4月15日
	 * @param canelTime
	 */
	void batchCancelOrderJob(@Param("canelTime")String canelTime);
}
