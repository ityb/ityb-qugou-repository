package com.ityb.qugou.mapper;

import java.util.List;

import com.ityb.qugou.dto.browseHistory.BrowseHistoryDto;
import com.ityb.qugou.po.product.BrowseHistory;
import com.ityb.qugou.vo.browseHistory.BrowseHistoryVo;

/**
 * 对应操作的表名为t_p_browse_history
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface BrowseHistoryMapper {

	/**
	 * 添加一条浏览记录
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param browseHistory
	 */
	void insertBrowseHistory(BrowseHistory browseHistory);

	/**
	 * 获取一条记录
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param browseHistory
	 * @return
	 */
	BrowseHistory getBrowseHistory(BrowseHistory browseHistory);

	/**
	 * 进行添加
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param browseHistoryDb
	 */
	void updateBrowseHistory(BrowseHistory browseHistory);

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
