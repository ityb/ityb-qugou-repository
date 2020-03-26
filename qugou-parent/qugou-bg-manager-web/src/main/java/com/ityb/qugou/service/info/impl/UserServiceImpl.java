package com.ityb.qugou.service.info.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.dao.info.UserDao;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.service.info.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

	@Autowired
	private UserDao customerDao;
	@Override
	public List<User> getListData() {
		return null;
	}

	@Override
	protected BaseDao<User> getDao() {
		return customerDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return User.class;
	}

}
