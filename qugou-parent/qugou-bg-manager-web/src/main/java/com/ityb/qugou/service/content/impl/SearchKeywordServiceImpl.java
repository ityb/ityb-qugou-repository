package com.ityb.qugou.service.content.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.dao.content.SearchKeywordDao;
import com.ityb.qugou.po.manager.SearchKeyword;
import com.ityb.qugou.service.content.SearchKeywordService;

/**
 * 搜索关键字实现类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class SearchKeywordServiceImpl extends BaseServiceImpl<SearchKeyword> implements SearchKeywordService{

	@Autowired
	private SearchKeywordDao  searchKeywordDao;
	@Override
	public List<SearchKeyword> getListData() {
		return null;
	}

	@Override
	protected BaseDao<SearchKeyword> getDao() {
		return searchKeywordDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return SearchKeyword.class;
	}

}
