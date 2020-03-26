package com.ityb.qugou.remoting;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.dto.order.OrderDto;
import com.ityb.qugou.dto.order.OrderItemDto;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.vo.order.OrderShowVo;

@FeignClient(value = "order-service")
public interface OrderServiceRemoting {

	/**
	 * 添加新的订单
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param list
	 * @return
	 */
	@PostMapping(value="/order/addOrder")
	JsonResultData<String> addOrder(@RequestBody List<OrderItemDto> list);

	/**
	 * 
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param orderNumbers
	 * @return
	 */
	@PostMapping(value="/order/queryOrderByOrderNumber")
	JsonResultData<List<Order>> queryOrderByOrderNumber(@RequestParam("orderNumber") String orderNumbers);

	/**
	 * 更新订单信息
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param order
	 * @return
	 */
	@PostMapping(value="/order/updateOrderByOrderNumber")
	JsonResultData<Boolean> updateOrderByOrderNumber(@RequestBody Order order);

	/**
	 * 获取订单信息通过Dto
	 * @author yangbin
	 * @date 2018年3月29日
	 * @param orderDto
	 * @return
	 */
	@PostMapping(value="/order/queryOrderInfoByOrderDto")
	JsonResultData<List<OrderShowVo>> queryOrderInfoByOrderDto(@RequestBody OrderDto orderDto);

	/**
	 * 计算订单的总条数
	 * @author yangbin
	 * @date 2018年3月29日
	 * @param orderDto
	 * @return
	 */
	@PostMapping(value="/order/countOrderInfoByOrderDto")
	JsonResultData<Integer> countOrderInfoByOrderDto(@RequestBody OrderDto orderDto);

	/**
	 * 
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param order
	 * @return
	 */
	@PostMapping(value="/order/deleteOrderByOrderNumber")
	JsonResultData<Boolean> deleteOrderByOrderNumber(@RequestBody Order order);
}
