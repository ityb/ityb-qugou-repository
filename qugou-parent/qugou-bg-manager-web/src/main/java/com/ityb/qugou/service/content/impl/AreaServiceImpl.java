package com.ityb.qugou.service.content.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.dao.content.AreaDao;
import com.ityb.qugou.po.manager.Area;
import com.ityb.qugou.service.content.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;

	/**
	 * 获取区域
	 */
	@Override
	public List<Area> querySecondLevelArea() {
		return areaDao.querySecondLevelArea();
	}

}
