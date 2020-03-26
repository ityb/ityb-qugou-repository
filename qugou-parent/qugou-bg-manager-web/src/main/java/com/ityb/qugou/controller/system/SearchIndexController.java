package com.ityb.qugou.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.remoting.SearchServiceRemoting;

/**
 * 搜索索引控制器
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/manager/searchIndex")
public class SearchIndexController {

	@Autowired
	private SearchServiceRemoting searchServiceRemoting;

	/**
	 * 跳转到搜索索引页面
	 * 
	 * @author yangbin
	 * @date 2018年2月11日
	 * @return
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchIndex() {
		return "search-index";
	}

	/**
	 * 导入商品搜索索引到solr环境
	 * @author yangbin
	 * @date 2018年2月11日
	 * @return
	 */
	@RequestMapping(value = "/import", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResultData<Boolean> importIndex() {
		JsonResultData<Boolean> jsonResultData = this.searchServiceRemoting.importIndex();
		
		return jsonResultData;
	}
}
