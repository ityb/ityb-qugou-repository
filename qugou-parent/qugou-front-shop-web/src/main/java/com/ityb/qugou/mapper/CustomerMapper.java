package com.ityb.qugou.mapper;

import com.ityb.qugou.po.manager.Customer;

public interface CustomerMapper {

	/**
	 * 获得用户详细信息
	 * @author yangbin
	 * @date 2018年3月7日
	 * @param user
	 * @return
	 */
	Customer getCustomerByEntity(Customer customer);


}
