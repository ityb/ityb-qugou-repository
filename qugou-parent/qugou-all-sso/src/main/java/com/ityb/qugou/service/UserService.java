package com.ityb.qugou.service;

import com.ityb.qugou.bo.manager.UserBean;
import com.ityb.qugou.dto.manager.MerchantDto;
import com.ityb.qugou.dto.manager.UserDto;
import com.ityb.qugou.po.manager.User;

/**
 * 用户service接口
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface UserService {

	/**
	 * 获取一个用户
	 * @author yangbin
	 * @date 2018年3月2日
	 * @param user
	 * @return
	 */
	User getUser(User user);

	/**
	 * 得到用户详细信息
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param userDto
	 * @return
	 */
	UserBean getUserInfo(UserDto userDto);

	/**
	 * 添加一个用户
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param userDto
	 */
	void addUser(UserDto userDto);

	/**
	 * 获取商家用户
	 * @author yangbin
	 * @date 2018年3月13日
	 * @param userDto
	 * @return
	 */
	User getMerchantUser(UserDto userDto);

	/**
	 * 添加一个用户
	 * @author yangbin
	 * @date 2018年3月16日
	 * @param merchantDto
	 */
	void addMerchant(MerchantDto merchantDto);

}
