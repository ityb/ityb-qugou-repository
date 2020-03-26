package com.ityb.qugou.service.info.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.dao.info.MerchantDao;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.po.merchant.Merchant;
import com.ityb.qugou.service.info.MerchantService;

@Service
public class MerchantServiceImpl extends BaseServiceImpl<Merchant> implements MerchantService {

	@Autowired
	private MerchantDao merchantDao;

	@Override
	public List<Merchant> getListData() {
		return null;
	}

	@Override
	protected BaseDao<Merchant> getDao() {
		return merchantDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return User.class;
	}

}
