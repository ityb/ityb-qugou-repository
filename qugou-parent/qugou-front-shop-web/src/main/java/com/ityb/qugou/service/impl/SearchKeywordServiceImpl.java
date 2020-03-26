package com.ityb.qugou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.annotation.RedisCache;
import com.ityb.qugou.base.enums.CacheType;
import com.ityb.qugou.constant.RedisConstants;
import com.ityb.qugou.mapper.SearchKeywordMapper;
import com.ityb.qugou.po.manager.SearchKeyword;
import com.ityb.qugou.service.SearchKeywordService;

/**
 * 搜索关键字service
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class SearchKeywordServiceImpl implements SearchKeywordService{

	@Autowired
	private SearchKeywordMapper searchKeywordMapper;
	/**
	 * 获取搜索关键字列表
	 * @author yangbin
	 * @date 2018年2月14日
	 * @param searchKeyword
	 * @return
	 */
	@Override
	@RedisCache(cacheType=CacheType.KeyValue,keyName=RedisConstants.SEARCHKEYWORD_KEY,expireSeconds=RedisConstants.EXPIRESECONDS)
	public List<SearchKeyword> querySearchKeyword(SearchKeyword searchKeyword) {
		List<SearchKeyword> list = searchKeywordMapper.querySearchKeyword(searchKeyword);
		return list;
	}
	
}
