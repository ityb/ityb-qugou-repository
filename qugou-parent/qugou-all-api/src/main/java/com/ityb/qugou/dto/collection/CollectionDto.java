package com.ityb.qugou.dto.collection;

import com.ityb.qugou.base.dto.PageDto;

public class CollectionDto extends PageDto{

	/**
	 * serialVersionUID
	 * @date 2018年4月7日
	 */
	private static final long serialVersionUID = 1489604471052553499L;
	private String creater;
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
}
