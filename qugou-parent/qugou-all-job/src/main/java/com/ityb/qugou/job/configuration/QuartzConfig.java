package com.ityb.qugou.job.configuration;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 定时任务相关配置类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Configuration
public class QuartzConfig {
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		return new SchedulerFactoryBean();
	}

	@Bean
	public Scheduler scheduler() {
		return schedulerFactoryBean().getScheduler();
	}
}