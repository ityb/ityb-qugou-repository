package com.ityb.qugou.service;

import com.ityb.qugou.po.manager.User;

public interface LoginService {

	/**
	 * 进行管理员登录
	 * @param user
	 * @return
	 */
	User doAdminLogin(User user);

	/**
	 * 进行商家登录
	 * @param user
	 * @return
	 */
	User doMerchantLogin(User user);

	/**
	 * 进行商城系统登录
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param user
	 * @return
	 */
	User doShopLogin(User user);
	
}
