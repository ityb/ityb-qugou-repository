package com.ityb.qugou.remoting;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.bo.manager.StatisticsDayBean;
import com.ityb.qugou.bo.manager.StatisticsMonthBean;
import com.ityb.qugou.bo.manager.StatisticsSeasonBean;
import com.ityb.qugou.bo.manager.StatisticsYearBean;
import com.ityb.qugou.dto.manager.StatisticsDto;
import com.ityb.qugou.dto.order.OrderDto;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.vo.order.OrderStatisticsVo;
import com.ityb.qugou.vo.order.OrderVo;

@FeignClient(value = "order-service")
public interface OrderServiceRemoting {

	/**
	 * 获取订单列表
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param orderDto
	 * @return
	 */
	@PostMapping(value="/order/queryOrder")
	JsonResultData<List<OrderVo>> queryOrder(@RequestBody OrderDto orderDto);

	/**
	 * 计算订单列表总和
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param orderDto
	 * @return
	 */
	@PostMapping(value="/order/countOrder")
	JsonResultData<Integer> countOrder(@RequestBody OrderDto orderDto);
	
	/**
	 * 得到一个order
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param order
	 * @return
	 */
	@PostMapping(value="/order/getOrder")
	JsonResultData<OrderVo> getOrder(@RequestBody Order order);

	/**
	 * 更新订单信息
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param request
	 * @param productId
	 * @return
	 */
	@PostMapping(value="/order/updateOrder")
	JsonResultData<Boolean> updateOrder(@RequestBody Order order);

	/**
	 * 批量更新订单
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param request
	 * @param order
	 * @param ids
	 * @return
	 */
	@PostMapping(value="/order/updateProductBatch")
	JsonResultData<Boolean> updateProductBatch(@RequestBody Order order, @RequestParam("ids") String ids);


	/**
	 * 统计年销售
	 * @author yangbin
	 * @date 2018年5月11日
	 * @param statisticsDto
	 * @return
	 */
	@PostMapping(value="/order/statistics/queryYearSale")
	JsonResultData<List<StatisticsYearBean>> queryYearSale(@RequestBody StatisticsDto statisticsDto);

	/**
	 * 统计月销售
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param statisticsDto
	 * @return
	 */
	@PostMapping(value="/order/statistics/queryMonthSale")
	JsonResultData<List<StatisticsMonthBean>> queryMonthSale(@RequestBody StatisticsDto statisticsDto);

	/**
	 * 获取季度销售
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param statisticsDto
	 * @return
	 */
	@PostMapping(value="/order/statistics/querySeasonSale")
	JsonResultData<List<StatisticsSeasonBean>> querySeasonSale(@RequestBody StatisticsDto statisticsDto);

	/**
	 * 获取今日成交额
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param order
	 * @param request
	 * @param model
	 * @return
	 */
	@PostMapping(value="/order/statistics/getTodayTrade")
	JsonResultData<OrderStatisticsVo> getTodayTrade(@RequestBody Order order);

	/**
	 * 统计日销售量
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param statisticsDto
	 * @return
	 */
	@PostMapping(value="/order/statistics/queryDaySale")
	JsonResultData<List<StatisticsDayBean>> queryDaySale(@RequestBody StatisticsDto statisticsDto);

}
