package com.ityb.qugou.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ityb.qugou.base.constant.StatusConstants;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.dao.OrderDeliveryDao;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.po.order.OrderDelivery;
import com.ityb.qugou.remoting.OrderServiceRemoting;
import com.ityb.qugou.service.OrderDeliveryService;

/**
 * 配送信息服务类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class OrderDeliveryServiceImpl extends BaseServiceImpl<OrderDelivery> implements OrderDeliveryService{

	@Autowired
	private OrderDeliveryDao deliveryDao;
	@Autowired
	private OrderServiceRemoting orderServiceRemoting;
	
	/**
	 * 添加配送信息
	 * @author yangbin
	 * @date 2018年3月30日
	 * @param orderDelivery
	 */
	@Override
	@Transactional
	public void addOrderDelivery(OrderDelivery orderDelivery) {
		Assert.hasText(orderDelivery.getOrderId(),"订单ID不能为空");
		Assert.hasText(orderDelivery.getDeliveryName(),"配送人的姓名不能为空");
		Assert.hasText(orderDelivery.getDeliveryPhone(),"配送人的联系方式不能为空");
		orderDelivery.setId(StringUtils.getRandomStr());
		orderDelivery.setCtime(new Date());
		this.deliveryDao.insert(orderDelivery);
		Order order=new Order();
		order.setId(orderDelivery.getOrderId());
		order.setState(3);//3表示订单正在派送
		JsonResultData<Boolean> jsonResultData = orderServiceRemoting.updateOrder(order);
		if(jsonResultData.getStatus()==StatusConstants.STATE_FAIL){
			throw new ServiceException("更新订单信息失败");
		}
	}
	
	@Override
	public List<OrderDelivery> getListData() {
		return null;
	}

	@Override
	protected BaseDao<OrderDelivery> getDao() {
		return deliveryDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return OrderDelivery.class;
	}

	/**
	 * 获取一个订单配送信息
	 * @author yangbin
	 * @date 2018年3月30日
	 * @param orderDelivery
	 * @return
	 */
	@Override
	public OrderDelivery getOrderDeliveryByEntity(OrderDelivery orderDelivery) {
		if(StringUtils.isBlank(orderDelivery.getId())&&StringUtils.isBlank(orderDelivery.getOrderId())){
			throw new ServiceException("ID与订单号不能都为空");
		}
		return this.deliveryDao.getOrderDeliveryByEntity(orderDelivery);
	}

	/**
	 * 更新订单配送信息
	 * @author yangbin
	 * @date 2018年3月30日
	 * @param orderDelivery
	 */
	@Override
	public void updateOrderDeliveryByEntity(OrderDelivery orderDelivery) {
		if(StringUtils.isBlank(orderDelivery.getId())&&StringUtils.isBlank(orderDelivery.getOrderId())){
			throw new ServiceException("ID与订单号不能都为空");
		}
		this.deliveryDao.update(orderDelivery);
	}

}
