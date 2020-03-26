package com.ityb.qugou.controller.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ityb.qugou.base.constant.CommonConstants;
import com.ityb.qugou.base.controller.BaseController;
import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.base.utils.JsonResult;
import com.ityb.qugou.base.utils.JsonUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.po.manager.ProductCategory;
import com.ityb.qugou.service.content.ProductCategoryService;
import com.ityb.qugou.vo.manager.ProductCategoryVo;

/**
 * 商品分类控制器
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/manager/category")
public class ProductCategoryController extends BaseController<ProductCategory>{
	@Autowired
	private ProductCategoryService productCategoryService;
	
	/**
	 * 显示商品分类列表
	 * @author yangbin
	 * @date 2017年12月30日
	 * @param model
	 * @return json格式
	 */
	@RequestMapping(value="/show",method={RequestMethod.GET,RequestMethod.POST})
	public String list(Model model){
		List<ProductCategoryVo> list=this.productCategoryService.queryProductCatgeory();
		model.addAttribute("productCategoryList", JsonUtils.objectToJson(list));
		return getListPage();
	}

	/**
	 * 获取分类的数量
	 * @author yangbin
	 * @date 2017年12月30日
	 * @return
	 */
	@RequestMapping(value="/countProductCategory",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult countProductCategory(String categoryId,Integer type){
		Integer count=this.productCategoryService.countProductCategroy(categoryId,type);
		if(count!=null){
			return JsonResult.ok(count);
		}else{
			return JsonResult.build(CommonConstants.ERROE, "获取数据失败", null);
		}
	}
	@Override
	protected String getListPage() {
		return "product-category";
	}

	@Override
	protected BaseService<ProductCategory> getService() {
		return productCategoryService;
	}

	@Override
	public String add() throws Exception {
		return null;
	}
	@Override
	public ProductCategory setSaveValue(ProductCategory dto) {
		dto.setId(StringUtils.getRandomStr());
		return dto;
	}
}
