package com.ityb.qugou.base.builder;

public class FilterPage {
	private Integer pageNumber;
	private Integer pageSize;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public FilterPage() {

	}

	public FilterPage(Integer start, Integer pageSize) {
		this.pageNumber = start;
		this.pageSize = pageSize;
	}

}
