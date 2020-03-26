package com.ityb.qugou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.mapper.DeliveryAddressMapper;
import com.ityb.qugou.po.order.DeliveryAddress;
import com.ityb.qugou.service.DeliveryAddressService;

/**
 * 配送地址服务实现类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService{

	@Autowired
	private DeliveryAddressMapper deliveryAddressMapper;
	/**
	 * 获取地址列表
	 * @author yangbin
	 * @date 2018年3月27日
	 * @param deliveryAddress
	 * @return
	 */
	@Override
	public List<DeliveryAddress> queryDeliveryAddressByEntity(DeliveryAddress deliveryAddress) {
		Assert.hasText(deliveryAddress.getCity(),"当地的地址不能为空");
		Assert.hasText(deliveryAddress.getCreater(),"当前人员Id不能为空");
		return deliveryAddressMapper.queryDeliveryAddressByEntity(deliveryAddress);
	}

}
