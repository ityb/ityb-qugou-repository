package com.ityb.qugou.service;

import java.util.List;

import com.ityb.qugou.dto.browseHistory.BrowseHistoryDto;
import com.ityb.qugou.po.product.BrowseHistory;
import com.ityb.qugou.vo.browseHistory.BrowseHistoryVo;

/**
 * 浏览记录服务接口
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface BrowseHistoryService {

	/**
	 * 添加一条浏览记录
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param browseHistory
	 */
	void addBrowseHistory(BrowseHistory browseHistory);

	/**
	 * 获取浏览历史记录
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param browseHistoryDto
	 * @return
	 */
	List<BrowseHistoryVo> queryBrowseHistoryByDto(BrowseHistoryDto browseHistoryDto);

	/**
	 * 计算浏览历史的条数
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param browseHistoryDto
	 * @return
	 */
	Integer countBrowseHistoryByDto(BrowseHistoryDto browseHistoryDto);

	/**
	 * 获取浏览记录通过实体
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param browseHistory
	 * @return
	 */
	List<BrowseHistory> queryBrowseHistoryByEntity(BrowseHistory browseHistory);

}
