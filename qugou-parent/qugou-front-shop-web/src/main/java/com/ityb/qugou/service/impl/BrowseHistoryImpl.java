package com.ityb.qugou.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.constant.CommonConstants;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.dto.browseHistory.BrowseHistoryDto;
import com.ityb.qugou.mapper.BrowseHistoryMapper;
import com.ityb.qugou.po.product.BrowseHistory;
import com.ityb.qugou.service.BrowseHistoryService;
import com.ityb.qugou.vo.browseHistory.BrowseHistoryVo;

@Service
public class BrowseHistoryImpl implements BrowseHistoryService{

	@Autowired
	private BrowseHistoryMapper browseHistoryMapper;
	/**
	 * 添加一条浏览记录
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param browseHistory
	 */
	@Override
	public void addBrowseHistory(BrowseHistory browseHistory) {
		Assert.hasText(browseHistory.getCreater(), "添加人不能为空");
		Assert.hasText(browseHistory.getShopCategoryName(),"添加的分类名称不能为空");
		Assert.hasText(browseHistory.getProductId(),"浏览的商品不能为空");
		
		/**
		 * 判断是否是已经存在,根据商品ID与添加人 以及添加时间
		 * 来判断今日是否有推荐的商品
		 */
		browseHistory.setCtime(new Date());
		BrowseHistory browseHistoryDb=this.browseHistoryMapper.getBrowseHistory(browseHistory);
		if(browseHistoryDb==null){//表示之前没有浏览过，进行添加
			browseHistory.setCreater(browseHistory.getCreater());
			browseHistory.setId(StringUtils.getRandomStr());
			browseHistory.setBrowseCount(1);
			browseHistory.setCtime(new Date());
			browseHistoryMapper.insertBrowseHistory(browseHistory);
		}else{//进行更新
			browseHistoryDb.setBrowseCount(browseHistoryDb.getBrowseCount()+1);
			browseHistoryDb.setMtime(new Date());
			this.browseHistoryMapper.updateBrowseHistory(browseHistoryDb);
		}
	}
	
	/**
	 * 获取浏览历史记录
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param browseHistoryDto
	 * @return
	 */
	@Override
	public List<BrowseHistoryVo> queryBrowseHistoryByDto(BrowseHistoryDto browseHistoryDto) {
		Assert.hasText(browseHistoryDto.getCreater(),"浏览记录所属人不能为空");
		if (browseHistoryDto.getPageIndex() != null && browseHistoryDto.getPageIndex() <= 0) {
			browseHistoryDto.setPageIndex(0);
		}
		if (browseHistoryDto.getPageSize() != null && browseHistoryDto.getPageSize() <= 0) {
			browseHistoryDto.setPageSize(CommonConstants.DEFAULT_PAGE_SIZE);
		}
		if (browseHistoryDto.getPageSize() != null && browseHistoryDto.getPageIndex() != null) {
			browseHistoryDto.setPageStart((browseHistoryDto.getPageIndex() - 1) * browseHistoryDto.getPageSize());
		}
		return this.browseHistoryMapper.queryBrowseHistoryByDto(browseHistoryDto);
	}

	/**
	 * 计算浏览历史的条数
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param browseHistoryDto
	 * @return
	 */
	@Override
	public Integer countBrowseHistoryByDto(BrowseHistoryDto browseHistoryDto) {
		Assert.hasText(browseHistoryDto.getCreater(),"浏览记录所属人不能为空");
		return this.browseHistoryMapper.countBrowseHistoryByDto(browseHistoryDto);
	}

	/**
	 * 获取浏览记录通过实体
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param browseHistory
	 * @return
	 */
	@Override
	public List<BrowseHistory> queryBrowseHistoryByEntity(BrowseHistory browseHistory) {
		Assert.hasText(browseHistory.getCreater(),"浏览记录所属的用户不能为空");
		return this.browseHistoryMapper.queryBrowseHistoryByEntity(browseHistory);
	}
	
}
