package com.ityb.qugou.service.content.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.dao.content.DirectSupermarketDao;
import com.ityb.qugou.dto.manager.DirectSupermarketDto;
import com.ityb.qugou.po.manager.DirectSupermarket;
import com.ityb.qugou.service.content.DirectSupermarketService;
import com.ityb.qugou.vo.manager.DirectSupermarketVo;

@Service
public class DirectSupermarketServiceImpl extends BaseServiceImpl<DirectSupermarket> implements DirectSupermarketService{

	@Autowired
	private  DirectSupermarketDao directSupermarketDao;
	
	@Override
	public List<DirectSupermarket> getListData() {
		return null;
	}

	@Override
	protected BaseDao<DirectSupermarket> getDao() {
		return directSupermarketDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return DirectSupermarket.class;
	}

	/**
	 * 获取直通超市列表
	 * @author yangbin
	 * @date 2018年4月15日
	 * @param directSupermarketDto
	 * @return
	 */
	@Override
	public List<DirectSupermarketVo> queryDirectSupermarket(DirectSupermarketDto directSupermarketDto) {
		return directSupermarketDao.queryDirectSupermarket(directSupermarketDto);
	}

}
