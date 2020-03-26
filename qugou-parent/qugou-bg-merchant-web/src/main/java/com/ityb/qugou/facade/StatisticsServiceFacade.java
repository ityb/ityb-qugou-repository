package com.ityb.qugou.facade;

import java.util.List;

import com.ityb.qugou.bo.manager.StatisticsDayBean;
import com.ityb.qugou.bo.manager.StatisticsMonthBean;
import com.ityb.qugou.bo.manager.StatisticsSeasonBean;
import com.ityb.qugou.bo.manager.StatisticsYearBean;
import com.ityb.qugou.dto.manager.StatisticsDto;

/**
 * 统计服务facade接口
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface StatisticsServiceFacade {

	/**
	 * 统计年销售
	 * @author yangbin
	 * @date 2018年5月11日
	 * @param statisticsDto
	 * @return
	 */
	List<StatisticsYearBean> queryYearSale(StatisticsDto statisticsDto);

	/**
	 * 获取待统计的年年份
	 * @author yangbin
	 * @date 2018年5月11日
	 * @param merchantId
	 * @return
	 */
	List<Integer> queryStatisticsYear(String merchantId);

	/**
	 * 获取统计的月数
	 * @author yangbin
	 * @date 2018年5月12日
	 * @return
	 */
	List<Integer> queryStatisticsMonth();

	/**
	 * 获取月销售信息
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param request
	 * @param model
	 * @param statisticsDto
	 * @return
	 */
	List<StatisticsMonthBean> queryMonthSale(StatisticsDto statisticsDto);

	/**
	 * 获取需要统计的季度
	 * @author yangbin
	 * @date 2018年5月12日
	 * @return
	 */
	List<Integer> queryStatisticsSeason();

	/**
	 * 获取季度销售情况
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param statisticsDto
	 * @return
	 */
	List<StatisticsSeasonBean> querySeasonSale(StatisticsDto statisticsDto);

	/**
	 * 统计日销售
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param statisticsDto
	 * @return
	 */
	List<StatisticsDayBean> queryDaySale(StatisticsDto statisticsDto);

}
