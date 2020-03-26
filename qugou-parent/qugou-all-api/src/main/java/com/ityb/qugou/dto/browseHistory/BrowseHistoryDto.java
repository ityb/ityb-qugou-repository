package com.ityb.qugou.dto.browseHistory;

import com.ityb.qugou.base.dto.PageDto;

public class BrowseHistoryDto extends PageDto{

	/**
	 * serialVersionUID
	 * @date 2018年4月7日
	 */
	private static final long serialVersionUID = 3510107344477324191L;
	private String creater;
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}

}
