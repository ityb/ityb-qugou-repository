package com.ityb.qugou.po.job;

import com.ityb.qugou.base.entity.BaseEntity;

public class ScheduledJob extends BaseEntity{
	/**
	 * serialVersionUID
	 * @date 2018年3月24日
	 */
	private static final long serialVersionUID = -20075318582227141L;
	private String url;
	private String jobDesc;
	private String executeTimeDesc;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getExecuteTimeDesc() {
		return executeTimeDesc;
	}
	public void setExecuteTimeDesc(String executeTimeDesc) {
		this.executeTimeDesc = executeTimeDesc;
	}
	
	@Override
	public String getTableName() {
		return "t_sys_scheduled_job";
	}
}
