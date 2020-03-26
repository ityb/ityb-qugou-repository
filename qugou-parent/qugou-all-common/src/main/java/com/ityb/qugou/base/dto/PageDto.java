package com.ityb.qugou.base.dto;

import java.io.Serializable;

public class PageDto implements Serializable {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月21日
	 */
	private static final long serialVersionUID = 6920662140659214644L;
	private Integer pageSize; //一页显示多少条记录
	private Integer pageIndex;//页面下标
	private Integer pageStart;//开始

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageStart() {
		return pageStart;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}

}
