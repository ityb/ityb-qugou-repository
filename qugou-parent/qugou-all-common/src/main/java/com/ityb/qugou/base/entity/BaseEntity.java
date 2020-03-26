package com.ityb.qugou.base.entity;

import java.io.Serializable;
import java.util.Date;
import com.ityb.qugou.base.core.CoreEntity;

public abstract class BaseEntity extends CoreEntity implements Serializable {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月13日
	 */
	private static final long serialVersionUID = 8046714003138128150L;
	private Integer isvalid = 1;
	//@JsonFormat(pattern = "yyyy-MM-dd")
	private Date ctime;
	//@JsonFormat(pattern = "yyyy-MM-dd")
	private Date mtime;
	//@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dtime;
	private String creater;
	private String updater;
	private String deleter;

	public Integer getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(Integer isvalid) {
		this.isvalid = isvalid;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public Date getDtime() {
		return dtime;
	}

	public void setDtime(Date dtime) {
		this.dtime = dtime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getDeleter() {
		return deleter;
	}

	public void setDeleter(String deleter) {
		this.deleter = deleter;
	}

}
