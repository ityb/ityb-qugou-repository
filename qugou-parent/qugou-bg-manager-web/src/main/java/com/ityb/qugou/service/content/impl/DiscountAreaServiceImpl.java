package com.ityb.qugou.service.content.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.dao.content.DiscountAreaDao;
import com.ityb.qugou.po.manager.DiscountArea;
import com.ityb.qugou.service.content.DiscountAreaService;

@Service
public class DiscountAreaServiceImpl extends BaseServiceImpl<DiscountArea> implements DiscountAreaService{

	@Autowired
	private DiscountAreaDao discountAreaDao;

	@Override
	public List<DiscountArea> query(DiscountArea discountArea) {
		return discountAreaDao.query(discountArea);
	}

	@Override
	public List<DiscountArea> getListData() {
		return null;
	}

	@Override
	protected BaseDao<DiscountArea> getDao() {
		return discountAreaDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return DiscountArea.class;
	}
}
