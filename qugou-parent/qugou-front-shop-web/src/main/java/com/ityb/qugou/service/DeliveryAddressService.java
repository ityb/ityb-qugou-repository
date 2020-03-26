package com.ityb.qugou.service;

import java.util.List;

import com.ityb.qugou.po.order.DeliveryAddress;

/**
 * 配送地址service接口
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface DeliveryAddressService {

	/**
	 * 获取地址列表
	 * @author yangbin
	 * @date 2018年3月27日
	 * @param deliveryAddress
	 * @return
	 */
	List<DeliveryAddress> queryDeliveryAddressByEntity(DeliveryAddress deliveryAddress);

}
