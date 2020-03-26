package com.ityb.qugou.order.service;

import java.util.List;

import com.ityb.qugou.bo.manager.StatisticsDayBean;
import com.ityb.qugou.bo.manager.StatisticsMonthBean;
import com.ityb.qugou.bo.manager.StatisticsSeasonBean;
import com.ityb.qugou.bo.manager.StatisticsYearBean;
import com.ityb.qugou.dto.manager.StatisticsDto;
import com.ityb.qugou.dto.order.OrderDto;
import com.ityb.qugou.dto.order.OrderItemDto;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.po.order.OrderItem;
import com.ityb.qugou.vo.order.OrderShowVo;
import com.ityb.qugou.vo.order.OrderStatisticsVo;
import com.ityb.qugou.vo.order.OrderVo;

/**
 * 订单服务接口
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface OrderService {


	/**
	 * 获取订单列表
	 * 
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param orderDto
	 * @return
	 */
	List<OrderVo> queryOrder(OrderDto orderDto);

	/**
	 * 获取产品规格数量
	 * 
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param orderDto
	 * @return
	 */
	int countOrder(OrderDto orderDto);

	/**
	 * 获取订单详细
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param order
	 * @return
	 */
	OrderVo getOrder(Order order);

	/**
	 * 更新订单信息
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param order
	 * @return
	 */
	void updateOrder(Order order);

	/**
	 * 批量更新订单
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param order
	 * @param ids
	 * @return
	 */
	void updateProductBatch(Order order, String ids);

	/**
	 * 添加新订单
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param list 传入的订单DTO 是一个集合
	 * @return 订单ID，中间用逗号分隔
	 */
	String addOrder(List<OrderItemDto> list);

	/**
	 * 获取订单，根据订单号
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param orderNumbers
	 * @return
	 */
	List<Order> queryOrderByOrderNumber(String orderNumbers);

	/**
	 * 更新订单信息通过订单编号
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param order
	 * @return
	 */
	void updateOrderByOrderNumber(Order order);

	/**
	 * 获取订单信息通过Dto
	 * @author yangbin
	 * @date 2018年3月29日
	 * @param orderDto
	 * @return
	 */
	List<OrderShowVo> queryOrderInfoByOrderDto(OrderDto orderDto);

	/**
	 * 计算订单的总条数
	 * @author yangbin
	 * @date 2018年3月29日
	 * @param orderDto
	 * @return
	 */
	Integer countOrderInfoByOrderDto(OrderDto orderDto);

	/**
	 * 获取订单项的条数
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param orderItem
	 * @return
	 */
	Integer countOrderItemByEntity(OrderItem orderItem);

	/**
	 * 更新订单项
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param orderItem
	 */
	void updateOrdeItem(OrderItem orderItem);

	/**
	 * 删除一个订单根据订单号
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param order
	 */
	void deleteOrderByOrderNumber(Order order);

	/**
	 * 统计年销售
	 * @author yangbin
	 * @date 2018年5月11日
	 * @param statisticsDto
	 * @return
	 */
	List<StatisticsYearBean> queryYearSale(StatisticsDto statisticsDto);

	/**
	 * 统计月销售
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param statisticsDto
	 * @return
	 */
	List<StatisticsMonthBean> queryMonthSale(StatisticsDto statisticsDto);

	/**
	 * 获取季度销售情况
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param statisticsDto
	 * @return
	 */
	List<StatisticsSeasonBean> querySeasonSale(StatisticsDto statisticsDto);

	/**
	 * 获取日成交额
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param order
	 * @return
	 */
	OrderStatisticsVo getTodayTrade(Order order);

	/**
	 * 统计日销售量
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param statisticsDto
	 * @return
	 */
	List<StatisticsDayBean> queryDaySale(StatisticsDto statisticsDto);

}
