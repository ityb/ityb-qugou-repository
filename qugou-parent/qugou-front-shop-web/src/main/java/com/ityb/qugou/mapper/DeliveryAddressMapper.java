package com.ityb.qugou.mapper;

import java.util.List;

import com.ityb.qugou.po.order.DeliveryAddress;

public interface DeliveryAddressMapper {

	/**
	 * 获取地址列表
	 * @author yangbin
	 * @date 2018年3月27日
	 * @param deliveryAddress
	 * @return
	 */
	List<DeliveryAddress> queryDeliveryAddressByEntity(DeliveryAddress deliveryAddress);

}
