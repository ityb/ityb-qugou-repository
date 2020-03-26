package com.ityb.qugou.service.system.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.dao.system.ScheduledJobDao;
import com.ityb.qugou.po.job.ScheduledJob;
import com.ityb.qugou.service.system.ScheduledJobService;

/**
 * 搜索关键字实现类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class ScheduledJobServiceImpl extends BaseServiceImpl<ScheduledJob> implements ScheduledJobService{

	@Autowired
	private ScheduledJobDao  scheduledJobDao;
	@Override
	public List<ScheduledJob> getListData() {
		return null;
	}

	@Override
	protected BaseDao<ScheduledJob> getDao() {
		return scheduledJobDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return ScheduledJob.class;
	}

}
