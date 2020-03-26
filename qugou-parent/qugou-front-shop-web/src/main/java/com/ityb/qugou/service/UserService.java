package com.ityb.qugou.service;

import com.ityb.qugou.po.manager.Customer;
import com.ityb.qugou.po.manager.User;

/**
 * 用户service接口
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface UserService {

	/**
	 * 获得用户详细信息
	 * @author yangbin
	 * @date 2018年3月7日
	 * @param user
	 * @return
	 */
	Customer getCustomer(User user);
	
}
