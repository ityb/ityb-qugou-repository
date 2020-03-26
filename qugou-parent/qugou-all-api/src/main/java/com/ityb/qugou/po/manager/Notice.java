package com.ityb.qugou.po.manager;

import com.ityb.qugou.base.entity.BaseEntity;

public class Notice extends BaseEntity{

	/**
	 * serialVersionUID
	 * @DATE 2017年12月24日
	 */
	private static final long serialVersionUID = 3325123675811272623L;

	/**
	 * 公告内容
	 */
	private String content;
	/**
	 * 连接Url
	 */
	private String linkUrl;
	/**
	 * 是否显示在首页
	 */
	private Integer isIndexShow;
	public Integer getIsIndexShow() {
		return isIndexShow;
	}
	public void setIsIndexShow(Integer isIndexShow) {
		this.isIndexShow = isIndexShow;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	@Override
	public String getTableName() {
		return "t_p_notice";
	}

}
