package com.ityb.qugou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.common.ShopBaseController;
import com.ityb.qugou.dto.collection.CollectionDto;
import com.ityb.qugou.po.collection.Collection;
import com.ityb.qugou.service.CollectionService;
import com.ityb.qugou.vo.collection.CollectionProductVo;

/**
 * 收藏相关的controller
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/shop/collection")
public class CollectionController extends ShopBaseController{
	
	@Autowired
	private CollectionService collectionService;
	
	/**
	 * 添加一个收藏记录
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param request
	 * @param collection
	 * @return
	 */
	@RequestMapping(value="/add",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> add(HttpServletRequest request,Collection collection){
		collection.setCreater(getCurrentUserId(request));
		try {
			collectionService.add(collection);
		} catch (Exception e) {
			return JsonResultData.success(false);
		}
		return JsonResultData.success(true);
	}
	
	/**
	 * 取消收藏
	 * @author yangbin
	 * @date 2018年5月16日
	 * @param request
	 * @param collection
	 * @return
	 */
	@RequestMapping(value="/product/delete",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> productDelete(HttpServletRequest request,Collection collection){
		try {
			collectionService.delete(collection);
		} catch (Exception e) {
			return JsonResultData.success(false);
		}
		return JsonResultData.success(true);
	}
	
	/**
	 * 跳转商品收藏列表
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param request
	 * @param response
	 * @param model
	 * @param collectionDto
	 * @return
	 */
	@RequestMapping(value="/product/list",method={RequestMethod.GET,RequestMethod.POST})
	public String  listProductCollection(HttpServletRequest request,HttpServletResponse response,Model model,CollectionDto collectionDto){
		setSearchHeader(request, response, model);
		collectionDto.setCreater(getCurrentUserId(request));
		List<CollectionProductVo> list=this.collectionService.queryProductCollectionByDto(collectionDto);
		model.addAttribute("collectionProductList",list);
		return "/collection/collection-product-list";
	}
	
	
}
