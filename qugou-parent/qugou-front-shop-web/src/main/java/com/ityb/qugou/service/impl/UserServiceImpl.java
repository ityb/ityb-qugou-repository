package com.ityb.qugou.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.mapper.CustomerMapper;
import com.ityb.qugou.po.manager.Customer;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private CustomerMapper customerMapper;
	/**
	 * 获得用户详细信息
	 * @author yangbin
	 * @date 2018年3月7日
	 * @param user
	 * @return
	 */
	@Override
	public Customer getCustomer(User user) {
		if(user==null||StringUtils.isBlank(user.getId())){
			return null;
		}
		Customer customer=new Customer();
		customer.setUserId(user.getId());
		return customerMapper.getCustomerByEntity(customer);
	}
}
