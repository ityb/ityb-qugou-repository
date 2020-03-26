package com.ityb.qugou.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ityb.qugou.bo.order.OrderBean;
import com.ityb.qugou.dto.order.OrderDto;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.po.order.OrderItem;
import com.ityb.qugou.vo.order.OrderItemVo;
import com.ityb.qugou.vo.order.OrderStatisticsVo;
import com.ityb.qugou.vo.order.OrderVo;

/**
 * 订单相关表的mapper文件
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface OrderMapper {

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
	 * 获取订单商品选项列表
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param order
	 * @return
	 */
	List<OrderItemVo> queryOrderItem(OrderItem orderItem);

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
	void updateProductBatch(@Param("order")Order order, @Param("orderIdList")List<String> orderIdList);

	/**
	 * 添加一个订单
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param order
	 */
	void insertOrder(Order order);

	/**
	 * 获取订单，根据订单号
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param orderNumbers
	 * @return
	 */
	List<Order> queryOrderByNumberList(@Param("orderNumberList")List<String> orderNumberList);

	/**
	 * 更新订单信息通过订单编号
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param order
	 * @return
	 */
	void updateOrderByOrderNumber(@Param("order")Order order, @Param("orderNumberList")List<String> orderNumberList);
	
	/**
	 * 更具Dto获取对应的订单信息
	 * @author yangbin
	 * @date 2018年3月29日
	 * @param orderDto
	 * @return
	 */
	List<OrderBean> queryOrderByDto(OrderDto orderDto);

	/**
	 * 计算订单的总条数
	 * @author yangbin
	 * @date 2018年3月29日
	 * @param orderDto
	 * @return
	 */
	Integer countOrderByDto(OrderDto orderDto);

	/**
	 * 通过实体删除一条数据
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param order
	 */
	void deleteByEntity(Order order);

	/**
	 * 查询一条记录通过实体
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param order
	 * @return
	 */
	Order getOrderByEntity(Order order);
	
	/**
	 * 获取日成交额
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param order
	 * @return
	 */
	OrderStatisticsVo getTodayTrade(Order order);	
}
