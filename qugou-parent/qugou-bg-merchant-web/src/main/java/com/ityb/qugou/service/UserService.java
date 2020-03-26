package com.ityb.qugou.service;

import com.ityb.qugou.po.manager.User;

public interface UserService {

	/**
	 * 修改密码操作
	 * @author yangbin
	 * @param oldPassword 
	 * @date 2018年5月15日
	 * @return
	 */
	void modifyPassword(User user, String oldPassword);

}
