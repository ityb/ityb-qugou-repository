package com.ityb.qugou.order.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ityb.qugou.base.constant.OrderConstants;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.NumberUtils;
import com.ityb.qugou.base.utils.PaymentUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.bo.manager.StatisticsDayBean;
import com.ityb.qugou.bo.manager.StatisticsMonthBean;
import com.ityb.qugou.bo.manager.StatisticsSeasonBean;
import com.ityb.qugou.bo.manager.StatisticsYearBean;
import com.ityb.qugou.bo.order.OrderBean;
import com.ityb.qugou.bo.order.OrderItemBean;
import com.ityb.qugou.dto.manager.StatisticsDto;
import com.ityb.qugou.dto.order.OrderDto;
import com.ityb.qugou.dto.order.OrderItemDto;
import com.ityb.qugou.order.constant.Constants;
import com.ityb.qugou.order.jms.JmsMessageProvideService;
import com.ityb.qugou.order.mapper.OrderItemMapper;
import com.ityb.qugou.order.mapper.OrderMapper;
import com.ityb.qugou.order.remoting.CartServiceRemoting;
import com.ityb.qugou.order.remoting.ProductServiceRemoting;
import com.ityb.qugou.order.service.OrderService;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.po.order.OrderItem;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.vo.order.OrderItemVo;
import com.ityb.qugou.vo.order.OrderShowVo;
import com.ityb.qugou.vo.order.OrderStatisticsVo;
import com.ityb.qugou.vo.order.OrderVo;

/**
 * 订单服务service实现类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private ProductServiceRemoting productServiceRemoting;
	@Autowired
	private CartServiceRemoting cartServiceRemoting;
	@Autowired
	private JmsMessageProvideService jmsMessageProvideService;

	/**
	 * 获取订单列表
	 * 
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param orderDto
	 * @return
	 */
	@Override
	public List<OrderVo> queryOrder(OrderDto orderDto) {
		return orderMapper.queryOrder(orderDto);
	}

	/**
	 * 获取产品规格数量
	 * 
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param orderDto
	 * @return
	 */
	@Override
	public int countOrder(OrderDto orderDto) {
		return orderMapper.countOrder(orderDto);
	}

	/**
	 * 获取订单详细
	 * 
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param order
	 * @return
	 */
	@Override
	public OrderVo getOrder(Order order) {
		Assert.hasText(order.getId(), "订单ID不能为空");
		OrderVo orderVo = this.orderMapper.getOrder(order);
		Assert.notNull(orderVo, "订单ID有误");
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(order.getId());
		List<OrderItemVo> orderItemList = this.orderMapper.queryOrderItem(orderItem);
		orderVo.setOrderItemVoList(orderItemList);
		return orderVo;
	}

	/**
	 * 更新订单信息
	 * 
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param order
	 * @return
	 */
	@Override
	@Transactional
	public void updateOrder(Order order) {
		Assert.hasText(order.getId(), "订单ID不能为空");
		Boolean isRefuse = false;
		if (NumberUtils.isNotNull(order.getState())) {// 表示是需要更新订单状态
			if (order.getState() == Constants.ORDER_STATE_REFUSE) {
				isRefuse = true;
			}
		}
		this.orderMapper.updateOrder(order);
		if (isRefuse) {
			/*
			 * 1.判断是否是已付款订单
			 */
			OrderVo orderVo = this.orderMapper.getOrder(order);
			if (Constants.ORDER_TRADE_WAY_ZXZF.equals(orderVo.getTradeWay())) {// 表示是货到付款订单
				// 进行退款，这里暂时不调用易付宝退款信息
				try {
					PaymentUtils.toRefund(orderVo.getSerialNumber());
				} catch (Exception e) {
					throw new ServiceException("退款失败");
				}
			}
			/*
			 * 2.将商品对应的规格，补充到库存中
			 */
			List<OrderItemBean> orderBeanList = this.orderItemMapper.queryOrderItemByOrderId(orderVo.getOrderId());
			for (OrderItemBean orderItemBean : orderBeanList) {
				ProductSpecification productSpecification = new ProductSpecification();
				productSpecification.setId(orderItemBean.getProductSpecificationId());
				productSpecification.setStock(orderItemBean.getStock() + orderItemBean.getBuyNumber());
				this.productServiceRemoting.updateSpecification(productSpecification);
			}
		}
	}

	/**
	 * 批量更新订单
	 * 
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param order
	 * @param ids
	 * @return
	 */
	@Transactional
	@Override
	public void updateProductBatch(Order order, String ids) {
		Assert.hasText(ids, "订单ID不能为空");
		List<String> orderIdList = Arrays.asList(ids.replaceAll("，", ",").split("\\s*,\\s*"));
		Boolean isRefuse = false;
		if (NumberUtils.isNotNull(order.getState())) {// 表示是需要更新订单状态
			if (order.getState() == Constants.ORDER_STATE_REFUSE) {
				isRefuse = true;
			}
		}
		if (isRefuse) {
			Order orderTemp = new Order();
			for (String orderId : orderIdList) {
				orderTemp.setId(orderId);
				/*
				 * 1.判断是否是已付款订单
				 */
				OrderVo orderVo = this.orderMapper.getOrder(orderTemp);
				if (Constants.ORDER_TRADE_WAY_ZXZF.equals(orderVo.getTradeWay())) {// 表示是货到付款订单
					// 进行退款，这里暂时不调用易付宝退款信息
					try {
						PaymentUtils.toRefund(orderVo.getSerialNumber());
					} catch (Exception e) {
						throw new ServiceException("退款失败");
					}
				}
				/*
				 * 2.将商品对应的规格，补充到库存中
				 */
				List<OrderItemBean> orderBeanList = this.orderItemMapper.queryOrderItemByOrderId(orderVo.getOrderId());
				for (OrderItemBean orderItemBean : orderBeanList) {
					ProductSpecification productSpecification = new ProductSpecification();
					productSpecification.setId(orderItemBean.getProductSpecificationId());
					productSpecification.setStock(orderItemBean.getStock() + orderItemBean.getBuyNumber());
					this.productServiceRemoting.updateSpecification(productSpecification);
				}
			}
		}
		this.orderMapper.updateProductBatch(order, orderIdList);
	}

	/**
	 * 添加新订单
	 * 
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param list
	 *            传入的订单DTO 是一个集合
	 * @return 订单ID，中间用逗号分隔
	 */
	@Override
	@Transactional
	public String addOrder(List<OrderItemDto> list) {
		Assert.notEmpty(list, "订单内容不能为空");
		StringBuffer orderNumberBuff = new StringBuffer();
		for (OrderItemDto orderItemDto : list) {
			Order order = orderItemDto.getOrder();
			Assert.hasText(order.getCreater(), "添加人不能为空");
			Assert.hasText(order.getCustomerPhone(), "联系电话不能为空");
			Assert.hasText(order.getMerchantId(), "所属商家不能不为空");
			Assert.hasText(order.getDeliveryAddress(), "配送地址不能为空");
			String orderNumber = System.currentTimeMillis() + NumberUtils.makeNumber(4);
			order.setNumber(orderNumber);
			orderNumberBuff.append(orderNumber).append(",");
			String orderId = StringUtils.getRandomStr();
			order.setId(orderId);// 生成订单号
			order.setState(-1);// 将订单状态设置为未付款订单
			order.setCtime(new Date());
			List<OrderItem> orderItemList = orderItemDto.getOrderItemList();
			Assert.notEmpty(orderItemList, "订单对应的商品不能为空");
			for (OrderItem orderItem : orderItemList) {
				Assert.NumberIsNotNull(orderItem.getBuyNum(), "购买的商品数量不能为空");
				Assert.hasText(orderItem.getProductSpecificationId(), "商品规格Id不能为空");
				Assert.NumberIsNotNull(orderItem.getSubtotal(), "订单小计不能为空");
				orderItem.setOrderId(orderId);
				orderItem.setId(StringUtils.getRandomStr());
				orderItem.setCtime(new Date());
				if (StringUtils.isBlank(orderItem.getCreater())) {
					orderItem.setCreater(order.getCreater());
				}
				orderItemMapper.insertOrderItem(orderItem);
				this.productSpecificationAfterAddOrder(orderItem);
				ProductSpecification productSpecification = new ProductSpecification();
				productSpecification.setId(orderItem.getProductSpecificationId());
				productSpecification.setCreater(order.getCreater());
				this.cartServiceRemoting.deleteProductByProductSpecificationId(productSpecification);
				//进行将其添加到消息队列中
				if(order.getTradeWay()==Constants.ORDER_TRADE_WAY_HDFK_FLAG){
					new Thread(()->{
						jmsMessageProvideService.sendMessageByQueue(Constants.NEW_ORDER_DESTINATION_NAME,order.getMerchantId()+","+order.getId());
					}).start();
				}
			}
			// 进行订单的添加
			this.orderMapper.insertOrder(order);
		}
		return orderNumberBuff.delete(orderNumberBuff.length() - 1, orderNumberBuff.length()).toString();
	}

	/**
	 * 更新库存需要加同步方法
	 * 
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param buyNum
	 */
	private synchronized void productSpecificationAfterAddOrder(OrderItem orderItem) {
		ProductSpecification productSpecification = new ProductSpecification();
		productSpecification.setId(orderItem.getProductSpecificationId());
		JsonResultData<ProductSpecification> specificationJsonResult = this.productServiceRemoting
				.getProductSpecification(productSpecification);
		ProductSpecification specification = specificationJsonResult.getData();
		Assert.notNull(specification, "商品规格ID不正确");
		Assert.isTrue(specification.getStock() >= orderItem.getBuyNum(), "库存量不足");
		productSpecification.setStock(specification.getStock() - orderItem.getBuyNum());
		// 进行修改库存量
		this.productServiceRemoting.updateSpecification(productSpecification);
	}

	/**
	 * 获取订单，根据订单号
	 * 
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param orderNumbers
	 * @return
	 */
	@Override
	public List<Order> queryOrderByOrderNumber(String orderNumbers) {
		Assert.hasText(orderNumbers, "订单号不能为空");
		List<String> orderNumberList = Arrays.asList(orderNumbers.split("\\s*,\\s*"));
		return this.orderMapper.queryOrderByNumberList(orderNumberList);
	}

	/**
	 * 更新订单信息通过订单编号
	 * 
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param order
	 * @return
	 */
	@Override
	public void updateOrderByOrderNumber(Order order) {
		Assert.hasText(order.getNumber(), "订单编号不能为空");
		List<String> orderNumberList = Arrays.asList(order.getNumber().split("\\s*,\\s*"));
		if(NumberUtils.isNotNull(order.getState())&&order.getState()==OrderConstants.ORDER_STATE_PAID){//表示为支付状态
			for (String number : orderNumberList) {
				Order paramOrder=new Order();
				paramOrder.setNumber(number);
				Order orderEntity = this.orderMapper.getOrderByEntity(paramOrder); 
				new Thread(()->{
					jmsMessageProvideService.sendMessageByQueue(Constants.NEW_ORDER_DESTINATION_NAME,orderEntity.getMerchantId()+","+orderEntity.getId());
				}).start();
			}
		}
		this.orderMapper.updateOrderByOrderNumber(order, orderNumberList);
	}

	/**
	 * 获取订单信息通过Dto
	 * 
	 * @author yangbin
	 * @date 2018年3月29日
	 * @param orderDto
	 * @return
	 */
	@Override
	public List<OrderShowVo> queryOrderInfoByOrderDto(OrderDto orderDto) {
		Assert.hasText(orderDto.getUserId(), "订单所属用户不能为空");
		if (orderDto.getPageIndex() == null || orderDto.getPageIndex() <= 0) {
			orderDto.setPageIndex(Constants.DEFAULT_PAGE_NOW);
		}
		if (orderDto.getPageSize() == null || orderDto.getPageSize() <= 0) {
			orderDto.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		}
		orderDto.setPageStart((orderDto.getPageIndex() - 1) * orderDto.getPageSize());
		List<OrderShowVo> orderShowVoList = new ArrayList<>();
		List<OrderBean> orderList = this.orderMapper.queryOrderByDto(orderDto);
		if (CollectionUtils.isEmpty(orderList)) {
			return orderShowVoList;
		}
		OrderShowVo orderShowVo = null;
		for (OrderBean orderBean : orderList) {
			orderShowVo = new OrderShowVo();
			orderShowVo.setOrderBean(orderBean);
			OrderItem orderItem=new OrderItem();
			orderItem.setOrderId(orderBean.getId());
			orderItem.setIsEvaluation(orderDto.getIsEvaluation());
			List<OrderItemBean> orderItemBeanList = this.orderItemMapper.queryOrderItemByEntity(orderItem);
			if(CollectionUtils.isNotEmpty(orderItemBeanList)){//只有将符合条件的订单项不为空的时候才会放入集合
				orderShowVo.setOrderItemBeanList(orderItemBeanList);
				orderShowVoList.add(orderShowVo);
			}
		}
		return orderShowVoList;
	}

	/**
	 * 计算订单的总条数
	 * 
	 * @author yangbin
	 * @date 2018年3月29日
	 * @param orderDto
	 * @return
	 */
	@Override
	public Integer countOrderInfoByOrderDto(OrderDto orderDto) {
		Assert.hasText(orderDto.getUserId(), "订单所属用户不能为空");
		return this.orderMapper.countOrderByDto(orderDto);
	}

	

	/**
	 * 获取订单项的条数
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param orderItem
	 * @return
	 */
	@Override
	public Integer countOrderItemByEntity(OrderItem orderItem) {
		return this.orderItemMapper.countOrderItemByEntity(orderItem);
	}

	/**
	 * 更新订单项
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param orderItem
	 */
	@Override
	public void updateOrdeItem(OrderItem orderItem) {
		if(StringUtils.isBlank(orderItem.getId())){
			Assert.isTrue(StringUtils.isNotBlank(orderItem.getOrderId())&&StringUtils.isNotBlank(orderItem.getProductSpecificationId()),"订单ID与订单项不能为空");
		}
		orderItem.setMtime(new Date());
		this.orderItemMapper.updateOrdeItem(orderItem);
	}

	/**
	 * 删除一个订单根据订单号
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param order
	 */
	@Override
	@Transactional
	public void deleteOrderByOrderNumber(Order order) {
		Assert.hasText(order.getNumber(),"商品编号不能为空");
		Order orderEntity= this.orderMapper.getOrderByEntity(order);
		this.orderMapper.deleteByEntity(order);
		OrderItem orderItem=new OrderItem();
		orderItem.setOrderId(orderEntity.getId());
		this.orderItemMapper.deleteItemByEntity(orderItem);
	}

	/**
	 * 统计年销售
	 * @author yangbin
	 * @date 2018年5月11日
	 * @param statisticsDto
	 * @return
	 */
	@Override
	public List<StatisticsYearBean> queryYearSale(StatisticsDto statisticsDto) {
		Assert.NumberIsNotNull(statisticsDto.getYear(), "年份不能为空");
		Assert.hasText(statisticsDto.getMerchantId(),"商家ID不能为空");
		return this.orderItemMapper.queryYearSale(statisticsDto);
	}

	/**
	 * 统计月销售
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param statisticsDto
	 * @return
	 */
	@Override
	public List<StatisticsMonthBean> queryMonthSale(StatisticsDto statisticsDto) {
		Assert.hasText(statisticsDto.getMerchantId(),"商家ID不能为空");
		Assert.NumberIsNotNull(statisticsDto.getYear(), "年份不能为空");
		Assert.NumberIsNotNull(statisticsDto.getMonth(), "月份不能为空");
		return this.orderItemMapper.queryMonthSale(statisticsDto);
	}

	/**
	 * 获取季度销售情况
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param statisticsDto
	 * @return
	 */
	@Override
	public List<StatisticsSeasonBean> querySeasonSale(StatisticsDto statisticsDto) {
		Assert.hasText(statisticsDto.getMerchantId(),"商家ID不能为空");
		Assert.NumberIsNotNull(statisticsDto.getYear(),"年份不能为空");
		Assert.notEmpty(statisticsDto.getMonthList(),"季度月份区间不能为空");
		return this.orderItemMapper.querySeasonSale(statisticsDto);
	}

	/**
	 * 获取日成交额
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param order
	 * @return
	 */
	@Override
	public OrderStatisticsVo getTodayTrade(Order order) {
		Assert.hasText(order.getMerchantId(),"商家ID不能为空");
		return this.orderMapper.getTodayTrade(order);
	}

	/**
	 * 统计日销售量
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param statisticsDto
	 * @return
	 */
	@Override
	public List<StatisticsDayBean> queryDaySale(StatisticsDto statisticsDto) {
		Assert.hasText(statisticsDto.getMerchantId(),"商家ID不能为空");
		Assert.notNull(statisticsDto.getDay(),"统计的日期不能为空");
		return this.orderItemMapper.queryDaySale(statisticsDto);
	}
}
