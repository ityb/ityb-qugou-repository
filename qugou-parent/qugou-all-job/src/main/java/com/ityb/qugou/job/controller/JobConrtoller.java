package com.ityb.qugou.job.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.DateUtils;
import com.ityb.qugou.job.constant.JobConstants;
import com.ityb.qugou.job.service.JobService;

@RestController
@RequestMapping("/job")
@EnableScheduling 
public class JobConrtoller {
	@Autowired
	private JobService jobService;
	private final static Logger LOGGER = Logger.getLogger(JobConrtoller.class);
	
	/**
	 * /*@Scheduled(fixedRate = 5000):每秒执行一次
	 * 批量更新购物车的信息
	 * @author yangbin
	 * @date 2018年3月24日
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	@RequestMapping(value="/cart/batchUpdateStateJob",method={RequestMethod.GET,RequestMethod.POST})
	public JsonResultData<String> batchUpdateStateJob() {
		try {
			jobService.batchUpdateStateJob();
			LOGGER.info("batchUpdateStateJob....执行定时将今日购物车修改为历史购物车执行成功,执行时间为="+DateUtils.date2Str(new Date()));
			return JsonResultData.success("任务执行成功");
		} catch (Exception e) {
			LOGGER.error("batchUpdateStateJob....执行定时将今日购物车修改为历史购物车失败",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 批量取消订单
	 * 每一分钟执行一次
	 * @author yangbin
	 * @date 2018年4月15日
	 * @return
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	@RequestMapping(value="/order/batchCancelOrderJob",method={RequestMethod.GET,RequestMethod.POST})
	public JsonResultData<String> batchCancelOrderJob() {
		try {
			jobService.batchCancelOrderJob();
			LOGGER.info("batchCancelOrderJob....执行定时任务,将"+JobConstants.CENEL_ORDERTIME_INTERVAL_MINUTE+"分钟内未支付的订单取消,执行成功,执行时间为="+DateUtils.date2Str(new Date()));
			return JsonResultData.success("任务执行成功");
		} catch (Exception e) {
			LOGGER.error("batchUpdateStateJob....将"+JobConstants.CENEL_ORDERTIME_INTERVAL_MINUTE+"分钟中内未支付的订单取消，执行失败",e);
			return JsonResultData.error(e.getMessage());
		}
	}
}
