package com.ityb.qugou.job.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.utils.DateUtils;
import com.ityb.qugou.job.constant.JobConstants;
import com.ityb.qugou.job.mapper.JobMapper;
import com.ityb.qugou.job.service.JobService;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobMapper jobMapper;

	/**
	 * 批量更新购物车的信息
	 * 
	 * @author yangbin
	 * @date 2018年3月24日
	 */
	@Override
	public void batchUpdateStateJob() {
		jobMapper.batchUpdateStateJob();
	}

	@Override
	public void batchCancelOrderJob() {
		jobMapper.batchCancelOrderJob(DateUtils.date2Str(DateUtils.getCurrentTimeBeforeMinuteDate(JobConstants.CENEL_ORDERTIME_INTERVAL_MINUTE), "yyMMddHHmm"));
	}

}
