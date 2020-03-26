package com.ityb.qugou.po.collection;

import com.ityb.qugou.base.entity.BaseEntity;

public class Collection extends BaseEntity{

	/**
	 * serialVersionUID
	 * @date 2018年4月7日
	 */
	private static final long serialVersionUID = 7929292066937257733L;

	private String collectionId;
	private Integer type;
	
	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String getTableName() {
		return "t_p_collection";
	}

}
