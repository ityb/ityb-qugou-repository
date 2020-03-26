package com.ityb.qugou.order.controller.api;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ityb.qugou.base.constant.CommonConstants;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.dto.order.OrderDto;
import com.ityb.qugou.dto.order.OrderItemDto;
import com.ityb.qugou.order.service.OrderService;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.po.order.OrderItem;
import com.ityb.qugou.vo.order.OrderShowVo;
import com.ityb.qugou.vo.order.OrderVo;

/**
 * 商品服务api
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	private final static Logger LOGGER = Logger.getLogger(OrderController.class);

	/**
	 * 获取订单列表
	 * 
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/queryOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<List<OrderVo>> queryOrder(@RequestBody OrderDto orderDto) {
		if (orderDto.getPageIndex() != null && orderDto.getPageIndex() <= 0) {
			orderDto.setPageIndex(1);
		}
		if (orderDto.getPageSize() != null && orderDto.getPageSize() <= 0) {
			orderDto.setPageSize(CommonConstants.DEFAULT_PAGE_SIZE);
		}
		if (orderDto.getPageSize() != null && orderDto.getPageIndex() != null) {
			orderDto.setPageStart((orderDto.getPageIndex() - 1) * orderDto.getPageSize());
		}
		try {
			List<OrderVo> resultList = this.orderService
					.queryOrder(orderDto);
			return JsonResultData.success("获取数据成功", resultList);
		} catch (ServiceException e) {
			LOGGER.error("queryOrder....获取订单列表失败...");
			return JsonResultData.error(e.getMessage());
		}
	}

	/**
	 * 获取产品规格数量
	 * 
	 * @author yangbin
	 * @date 2018年1月26日
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/countOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Integer> countOrder(@RequestBody OrderDto orderDto) {
		try {
			int count = this.orderService.countOrder(orderDto);
			return JsonResultData.success("获取数据成功", count);
		} catch (ServiceException e) {
			LOGGER.error("countProductSpecification....获取订单列表数量...");
			return JsonResultData.error(e.getMessage());
		}
	}
	/**
	 * 获取订单详细
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/order/getOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<OrderVo> getOrder(@RequestBody Order order) {
		try {
			OrderVo orderVo = this.orderService.getOrder(order);
			return JsonResultData.success("获取数据成功", orderVo);
		} catch (Exception e) {
			LOGGER.error("getOrder....获订单详细失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
	/**
	 * 更新订单信息
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/order/updateOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Boolean> updateOrder(@RequestBody Order order) {
		try {
			this.orderService.updateOrder(order);
			return JsonResultData.success("获取数据成功", true);
		} catch (Exception e) {
			LOGGER.error("updateOrder....更新单失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 批量更新订单
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param order
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/order/updateProductBatch", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Boolean> updateProductBatch(@RequestBody Order order, @RequestParam("ids") String ids) {
		try {
			this.orderService.updateProductBatch(order,ids);
			return JsonResultData.success("更新数据成功", true);
		} catch (Exception e) {
			LOGGER.error("updateProductBatch....批量更新单失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 添加新订单
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param list 传入的订单DTO 是一个集合
	 * @return 订单ID，中间用逗号分隔
	 */
	@RequestMapping(value = "/order/addOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<String> addOrder(@RequestBody List<OrderItemDto> list) {
		try {
			String orderIds=this.orderService.addOrder(list);
			return JsonResultData.success("生成订单信息", orderIds);
		} catch (Exception e) {
			LOGGER.error("addOrder....生成订单失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取订单，根据订单号
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param orderNumbers
	 * @return
	 */
	@RequestMapping(value = "/order/queryOrderByOrderNumber", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<List<Order>> queryOrderByOrderNumber(@RequestParam("orderNumber")String orderNumbers) {
		try {
			List<Order> orderList=this.orderService.queryOrderByOrderNumber(orderNumbers);
			return JsonResultData.success("生成订单信息", orderList);
		} catch (Exception e) {
			LOGGER.error("queryOrderByOrderNumber....获取订单信息失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 更新订单信息通过订单编号
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/order/updateOrderByOrderNumber", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Boolean> updateOrderByOrderNumber(@RequestBody Order order) {
		try {
			this.orderService.updateOrderByOrderNumber(order);
			return JsonResultData.success("更新订单信息", true);
		} catch (Exception e) {
			LOGGER.error("updateOrderByOrderNumber....更新订单信息失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取订单信息通过Dto
	 * @author yangbin
	 * @date 2018年3月29日
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/queryOrderInfoByOrderDto", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<List<OrderShowVo>> queryOrderInfoByOrderDto(@RequestBody OrderDto orderDto) {
		try {
			List<OrderShowVo> list=this.orderService.queryOrderInfoByOrderDto(orderDto);
			return JsonResultData.success("获取订单信息", list);
		} catch (Exception e) {
			LOGGER.error("queryOrderInfoByOrderDto....获取订单信息失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 计算订单的总条数
	 * @author yangbin
	 * @date 2018年3月29日
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/countOrderInfoByOrderDto", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Integer> countOrderInfoByOrderDto(@RequestBody OrderDto orderDto) {
		try {
			Integer count=this.orderService.countOrderInfoByOrderDto(orderDto);
			return JsonResultData.success("获取数据成功", count);
		} catch (Exception e) {
			LOGGER.error("countOrderInfoByOrderDto....计算订单总数失败...");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取订单项的条数
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param orderItem
	 * @return
	 */
	@RequestMapping(value = "/order/countOrderItemByEntity", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Integer> countOrderItemByEntity(@RequestBody OrderItem orderItem) {
		try {
			Integer count=this.orderService.countOrderItemByEntity(orderItem);
			return JsonResultData.success("获取数据成功", count);
		} catch (Exception e) {
			LOGGER.error("countOrderItemByEntity....计算订单项总数失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 更新订单项
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param orderItem
	 * @return
	 */
	@RequestMapping(value = "/order/updateOrdeItem", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Boolean> updateOrdeItem(@RequestBody OrderItem orderItem) {
		try {
			this.orderService.updateOrdeItem(orderItem);
			return JsonResultData.success("更新数据成功", true);
		} catch (Exception e) {
			LOGGER.error("updateOrdeItem....订单项更新失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 删除一个订单
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/order/deleteOrderByOrderNumber", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonResultData<Boolean> deleteOrderByOrderNumber(@RequestBody Order order) {
		try {
			this.orderService.deleteOrderByOrderNumber(order);
			return JsonResultData.success("删除数据成功", true);
		} catch (Exception e) {
			LOGGER.error("deleteOrderByOrderNumber....订单删除数据失败...",e);
			return JsonResultData.error(e.getMessage());
		}
	}
}
