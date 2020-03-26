package com.ityb.qugou.po.manager;

import com.ityb.qugou.base.entity.BaseEntity;

/**
 * 搜索关键字实体类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class SearchKeyword extends BaseEntity{

	/**
	 * serialVersionUID
	 * @date 2017年12月30日
	 */
	private static final long serialVersionUID = -8822303710002011013L;

	/**
	 * 搜索关键字的名称
	 */
	private  String name;
	/**
	 * 排序号
	 */
	private Integer sortNum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	/**
	 * 表名
	 */
	@Override
	public String getTableName() {
		return "t_p_search_keyword";
	}

}
