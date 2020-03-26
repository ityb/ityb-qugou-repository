package com.ityb.qugou.po.manager;

import com.ityb.qugou.base.entity.BaseEntity;

public class Area extends BaseEntity {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年4月15日
	 */
	private static final long serialVersionUID = -6200907420182090335L;
	private String parentId; //父节点ID
	private String name; //名称
	private String shortName; //简称
	private Float longitude; //经度
	private Float latitude;//维度
	private Integer level;//等级(1省/直辖市,2地级市,3区县,4镇/街道)
	private Integer sort; //排序号
	private Integer status;//状态


	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String getTableName() {
		return "t_sys_area";
	}

}
