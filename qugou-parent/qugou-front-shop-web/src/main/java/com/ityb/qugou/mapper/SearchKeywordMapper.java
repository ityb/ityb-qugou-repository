package com.ityb.qugou.mapper;

import java.util.List;

import com.ityb.qugou.po.manager.SearchKeyword;

/**
 * 搜索关键字mapper
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface SearchKeywordMapper {

	/**
	 * 获取搜索关键字列表
	 * @author yangbin
	 * @date 2018年2月14日
	 * @param searchKeyword
	 * @return
	 */
	List<SearchKeyword> querySearchKeyword(SearchKeyword searchKeyword);

}
