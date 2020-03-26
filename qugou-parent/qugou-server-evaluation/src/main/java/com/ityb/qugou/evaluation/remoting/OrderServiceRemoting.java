package com.ityb.qugou.evaluation.remoting;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.po.order.OrderItem;

@FeignClient(value = "order-service")
public interface OrderServiceRemoting {

	/**
	 * 获取订单项的的条数
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param orderItem
	 * @return
	 */
	@PostMapping(value="/order/countOrderItemByEntity")
	JsonResultData<Integer> countOrderItemByEntity(@RequestBody OrderItem orderItem);


	/**
	 * 更新订单状态
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param order
	 * @return
	 */
	@PostMapping(value="/order/updateOrder")
	JsonResultData<Boolean> updateOrder(@RequestBody Order order);

	/**
	 * 更新订单项
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param orderItem
	 * @return
	 */
	@PostMapping(value="/order/updateOrdeItem")
	JsonResultData<Boolean> updateOrderItem(@RequestBody OrderItem orderItem);
}
