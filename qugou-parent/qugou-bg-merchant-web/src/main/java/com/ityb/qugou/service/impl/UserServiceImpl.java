package com.ityb.qugou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.dao.UserDao;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	

	/**
	 * 修改密码操作
	 * @author yangbin
	 * @date 2018年5月15日
	 * @return
	 */
	@Override
	public void modifyPassword(User user,String oldPassword) {
		Assert.hasText(user.getId(),"商家ID不能为空");
		Assert.isPassword(user.getPassword(), "密码不合规定");
		Assert.hasText(oldPassword, "原密码不能为空");
		User userEntity=this.userDao.getUser(user);
		if(userEntity.getPassword().equals(StringUtils.MD5Encrypt(oldPassword))){
			if(userEntity.getPassword().equals(StringUtils.MD5Encrypt(user.getPassword()))){
				throw new ServiceException("原密码与新密码不能相同");
			}else{
				user.setPassword(StringUtils.MD5Encrypt(user.getPassword()));
				userDao.update(user);
			}
		}else{
			throw new ServiceException("原密码不正确");
		}
	}
}
