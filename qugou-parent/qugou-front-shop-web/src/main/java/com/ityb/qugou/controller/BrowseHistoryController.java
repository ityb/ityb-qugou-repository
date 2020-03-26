package com.ityb.qugou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ityb.qugou.common.ShopBaseController;
import com.ityb.qugou.dto.browseHistory.BrowseHistoryDto;
import com.ityb.qugou.service.BrowseHistoryService;
import com.ityb.qugou.vo.browseHistory.BrowseHistoryVo;

/**
 * 浏览记录相关controller
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/shop/browseHistory")
public class BrowseHistoryController extends ShopBaseController{

	@Autowired
	private BrowseHistoryService browseHistoryService;
	
	/**
	 * 跳转到我的足迹页面
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param request
	 * @param response
	 * @param model
	 * @param browseHistoryDto
	 * @return
	 */
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request,HttpServletResponse response,Model model,BrowseHistoryDto browseHistoryDto){
		setSearchHeader(request, response, model);
		browseHistoryDto.setCreater(getCurrentUserId(request));
		List<BrowseHistoryVo> list=browseHistoryService.queryBrowseHistoryByDto(browseHistoryDto);
		model.addAttribute("browseHistoryList",list);
		Integer count=browseHistoryService.countBrowseHistoryByDto(browseHistoryDto);
		model.addAttribute("totalRecord",count);
		return "/browseHistory/browseHistory-list";
	}
}
