package com.ityb.qugou.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ityb.qugou.base.controller.BaseController;
import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.po.manager.SearchKeyword;
import com.ityb.qugou.service.content.SearchKeywordService;

/**
 * 搜索关键字的controller
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@RequestMapping("/manager/searchKeyword")
@Controller
public class SearchKeywordConrtoller extends BaseController<SearchKeyword>{

	@Autowired
	private SearchKeywordService searchKeywordService;
	
	/**
	 * 列表显示页面
	 */
	@Override
	protected String getListPage() {
		return "searchKeyword-list";
	}

	@Override
	protected BaseService<SearchKeyword> getService() {
		return searchKeywordService;
	}

	/**
	 * 跳转到添加页面
	 */
	@Override
	public String add() throws Exception {
		return "searchKeyword-add";
	}
	/**
	 * 跳转到更新页面
	 */
	@Override
	public String update(Model model) {
		return "searchKeyword-update";
	}

}
