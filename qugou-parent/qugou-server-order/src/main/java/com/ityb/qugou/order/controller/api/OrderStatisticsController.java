package com.ityb.qugou.order.controller.api;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.bo.manager.StatisticsDayBean;
import com.ityb.qugou.bo.manager.StatisticsMonthBean;
import com.ityb.qugou.bo.manager.StatisticsSeasonBean;
import com.ityb.qugou.bo.manager.StatisticsYearBean;
import com.ityb.qugou.dto.manager.StatisticsDto;
import com.ityb.qugou.order.service.OrderService;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.vo.order.OrderStatisticsVo;

/**
 * 订单统计api接口类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@RestController
@RequestMapping(value = "/order/statistics")
public class OrderStatisticsController {

	@Autowired
	private OrderService orderService;

	private final static Logger LOGGER = Logger.getLogger(OrderStatisticsController.class);

	/**
	 * 统计年销售
	 * @author yangbin
	 * @date 2018年5月11日
	 * @param statisticsDto
	 * @return
	 */
	@RequestMapping(value = "/queryYearSale", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<List<StatisticsYearBean>> queryYearSale(@RequestBody StatisticsDto statisticsDto) {
		try {
			List<StatisticsYearBean> list = this.orderService.queryYearSale(statisticsDto);
			return JsonResultData.success("获取数据成功", list);
		} catch (ServiceException e) {
			LOGGER.error("queryYearSale....统计年销售额失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 统计月销售
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param statisticsDto
	 * @return
	 */
	@RequestMapping(value = "/queryMonthSale", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<List<StatisticsMonthBean>> queryMonthSale(@RequestBody StatisticsDto statisticsDto) {
		try {
			List<StatisticsMonthBean> list = this.orderService.queryMonthSale(statisticsDto);
			return JsonResultData.success("获取数据成功", list);
		} catch (ServiceException e) {
			LOGGER.error("queryMonthSale....统计月销售额失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 统计日销售量
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param statisticsDto
	 * @return
	 */
	@RequestMapping(value = "/queryDaySale", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<List<StatisticsDayBean>> queryDaySale(@RequestBody StatisticsDto statisticsDto) {
		try {
			List<StatisticsDayBean> list = this.orderService.queryDaySale(statisticsDto);
			return JsonResultData.success("获取数据成功", list);
		} catch (ServiceException e) {
			LOGGER.error("queryMonthSale....统计日销售额失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取季度销售情况
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param statisticsDto
	 * @return
	 */
	@RequestMapping(value = "/querySeasonSale", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<List<StatisticsSeasonBean>> querySeasonSale(@RequestBody StatisticsDto statisticsDto) {
		try {
			List<StatisticsSeasonBean> list = this.orderService.querySeasonSale(statisticsDto);
			return JsonResultData.success("获取数据成功", list);
		} catch (ServiceException e) {
			LOGGER.error("querySeasonSale....统计季度销售额失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取日成交额
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/getTodayTrade", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<OrderStatisticsVo> getTodayTrade(@RequestBody Order order) {
		try {
			OrderStatisticsVo orderStatisticsVo = this.orderService.getTodayTrade(order);
			return JsonResultData.success("获取数据成功", orderStatisticsVo);
		} catch (ServiceException e) {
			LOGGER.error("getTodayTrade....获取今日成交额失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
}
