package com.ityb.qugou.po.manager;

import com.ityb.qugou.base.entity.BaseEntity;

public class DiscountArea extends BaseEntity{

	/**
	 * serialVersionUID
	 * @date 2018年4月14日
	 */
	private static final long serialVersionUID = 2284871133080939534L;
	private String showPic;
	private String name;
	private String linkUrl;
	private String parentId;
	private Integer sortNum;

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getShowPic() {
		return showPic;
	}

	public void setShowPic(String showPic) {
		this.showPic = showPic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	@Override
	public String getTableName() {
		return "t_p_discount_area";
	}
	
	

}
