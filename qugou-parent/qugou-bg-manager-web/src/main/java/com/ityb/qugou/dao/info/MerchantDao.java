package com.ityb.qugou.dao.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.po.merchant.Merchant;

@Repository
public class MerchantDao extends BaseDao<Merchant> {

	@Autowired
	private BaseMapper baseMapper;

	@Override
	protected BaseMapper getMapper() {
		return baseMapper;
	}

	@Override
	protected Class<?> getModelClass() {
		return Merchant.class;
	}
}
