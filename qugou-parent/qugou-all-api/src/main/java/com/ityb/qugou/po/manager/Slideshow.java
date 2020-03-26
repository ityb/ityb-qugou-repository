package com.ityb.qugou.po.manager;

import com.ityb.qugou.base.entity.BaseEntity;

/**
 * 
 * @author 杨彬
 * @Date 2017年12月5日
 * TODO 轮播图实体类
 */
public class Slideshow extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4979336572131029088L;

	
	private String linkUrl;// 连接url
	private String picUrl;// 图片url
	private Integer sortNum;// 排序号
	private String remark;//备注

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	@Override
	public String getTableName() {
		return "t_p_slideshow";
	}

}
