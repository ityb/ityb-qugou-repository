package com.ityb.qugou.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ityb.qugou.bo.manager.StatisticsDayBean;
import com.ityb.qugou.bo.manager.StatisticsMonthBean;
import com.ityb.qugou.bo.manager.StatisticsSeasonBean;
import com.ityb.qugou.bo.manager.StatisticsYearBean;
import com.ityb.qugou.bo.order.OrderItemBean;
import com.ityb.qugou.dto.manager.StatisticsDto;
import com.ityb.qugou.po.order.OrderItem;

/**
 * 订单项mapper
 * 对应表t_p_order_item
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface OrderItemMapper {

	/**
	 * 添加订单项
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param orderItem
	 */
	void insertOrderItem(OrderItem orderItem);

	/**
	 * 获取订单项，通过订单ID
	 * @author yangbin
	 * @date 2018年3月29日
	 * @param id
	 * @return
	 */
	List<OrderItemBean> queryOrderItemByOrderId(@Param("orderId")String id);

	/**
	 * 根据实体获取对应的商品信息
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param orderItem
	 * @return
	 */
	List<OrderItemBean> queryOrderItemByEntity(OrderItem orderItem);
	
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
	 * 删除订单项通过实体
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param orderItem
	 */
	void deleteItemByEntity(OrderItem orderItem);

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
	 * @date 2018年5月11日
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
	 * 统计日销售量
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param statisticsDto
	 * @return
	 */
	List<StatisticsDayBean> queryDaySale(StatisticsDto statisticsDto);
}
