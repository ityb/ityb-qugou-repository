package com.ityb.qugou.service.content;

import java.util.List;

import com.ityb.qugou.po.manager.Area;

public interface AreaService {

	/**
	 * 获取对应的区域列表
	 * @author yangbin
	 * @date 2018年4月15日
	 * @return
	 */
	List<Area> querySecondLevelArea();

}
