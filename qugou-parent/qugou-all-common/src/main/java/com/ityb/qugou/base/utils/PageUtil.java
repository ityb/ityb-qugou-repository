package com.ityb.qugou.base.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageUtil<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageSize = 10;// 每页条数
	private int pageNow;// 当前页数
	private int firstPage;// 首页
	private int prePage;// 上一页
	private int nextPage;// 下一页
	private int lastPage;// 尾页
	private int totalCount;// 总条数
	private int startNum; // mysql分页查询limit n,m
	private int state;// 表示状态

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getStartNum() {
		return startNum;
	}

	private List<T> resultList = new ArrayList<T>();

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
		this.startNum = (this.pageNow - 1) * pageSize;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getHtml() {
		StringBuffer sub = new StringBuffer();
		sub.append("<div class=\"pages\">");
		sub.append("<span>共" + this.getTotalCount() + "条</span>");
		sub.append("</div>");
		sub.append("<div class=\"pagination\" targetType=\"navTab\" totalCount=" + this.getTotalCount() + " numPerPage="
				+ this.getPageSize() + " pageNumShown=\"5\" currentPage=" + this.getPageNow() + "></div>");
		return sub.toString();
	}
}
