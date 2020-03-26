package com.ityb.qugou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ityb.qugou.base.constant.BackgroundConstants;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.dao.MerchantDao;
import com.ityb.qugou.dao.UserDao;
import com.ityb.qugou.dto.manager.UserDto;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.po.merchant.Merchant;
import com.ityb.qugou.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private MerchantDao merchantDao;

	/**
	 * 进行管理员登录
	 */
	@Override
	public User doAdminLogin(User user) {
		Assert.hasText(user.getUserName(),"用户名不能为空");
		Assert.hasText(user.getPassword(),"密码不能为空");
		user.setUserType(BackgroundConstants.USER_TYPE_BACKGOUND);
		// 进行查询
		List<User> userList = userDao.findUsers(user);
		Assert.notEmpty(userList,"用户名或者密码错误");
		User userDB = userList.get(0);
		Assert.isFalse(userDB.getState().equals( BackgroundConstants.STATE_LIMIT),"该账号已被禁用");
		Assert.isTrue(StringUtils.MD5Encrypt(user.getPassword()).equals(userDB.getPassword()),"用户名或者密码错误");
		// 进行密码的判断
		userDB.setPassword(null);
		return userDB;
	}

	@Override
	public User doMerchantLogin(User user) {
		Assert.hasText(user.getUserName(),"用户名不能为空");
		Assert.hasText(user.getPassword(),"密码不能为空");
		user.setUserType(BackgroundConstants.USER_TYPE_MERCHANT);
		// 进行查询
		List<User> userList = userDao.findUsers(user);
		if (CollectionUtils.isEmpty(userList)) {
			//进行手机号码匹配
			Merchant merchant = new Merchant();
			merchant.setPhone(user.getUserName());
			userList = merchantDao.findUsers(user, merchant);
			if (CollectionUtils.isEmpty(userList)) {
				//进行邮箱匹配
				merchant = new Merchant();
				merchant.setEmail(user.getUserName());
				userList = merchantDao.findUsers(user, merchant);
			}
		}
		Assert.notEmpty(userList,"用户名或者密码错误");
		User userDB = userList.get(0);
		Assert.isFalse(userDB.getState().equals(BackgroundConstants.STATE_LIMIT),"该账号已被禁用");
		Assert.isFalse(userDB.getState().equals(BackgroundConstants.STATE_UNREVIEWED),"该账号未经管理员审核暂时不能使用");
		Assert.isFalse(userDB.getState().equals(BackgroundConstants.STATE_UNREVIEWED_UNPASS),"该账号审核未通过，请重新注册");
		Assert.isTrue(StringUtils.MD5Encrypt(user.getPassword()).equals(userDB.getPassword()),"用户名或者密码错误");
		// 进行密码的判断
		userDB.setPassword(null);
		return userDB;
	}

	/**
	 * 商城用户登录
	 */
	@Override
	public User doShopLogin(User user) {
		Assert.hasText(user.getUserName(),"用户ID不能为空");
		Assert.hasText(user.getPassword(),"密码不能为空");
		user.setUserType(BackgroundConstants.USER_TYPE_CUSTOMER);
		// 进行查询
		List<User> userList = userDao.findUsers(user);
		if (CollectionUtils.isEmpty(userList)) {
			//进行手机号码匹配
			UserDto userDto=new UserDto();
			userDto.setPhone(user.getUserName());
			userDto.setUserType(BackgroundConstants.USER_TYPE_CUSTOMER);
			userList = this.userDao.queryUserByDto(userDto);
			if (CollectionUtils.isEmpty(userList)) {
				//进行邮箱匹配
				userDto.setPhone(null);
				userDto.setEmail(user.getUserName());
				userList = userDao.queryUserByDto(userDto);
			}
		}
		Assert.notEmpty(userList,"用户名或者密码错误");
		User userDB = userList.get(0);
		Assert.isTrue(!userDB.getState().equals(BackgroundConstants.STATE_LIMIT),"该账号已被禁用");
		// 进行密码的判断
		Assert.isTrue(StringUtils.MD5Encrypt(user.getPassword()).equals(userDB.getPassword()),"用户名或者密码错误");
		userDB.setPassword(null);
		return userDB;
	}
}
