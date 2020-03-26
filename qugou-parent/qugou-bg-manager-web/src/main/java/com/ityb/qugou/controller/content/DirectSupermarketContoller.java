package com.ityb.qugou.controller.content;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.controller.BaseController;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.JsonUtils;
import com.ityb.qugou.dto.manager.DirectSupermarketDto;
import com.ityb.qugou.po.manager.Area;
import com.ityb.qugou.po.manager.DirectSupermarket;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.service.content.AreaService;
import com.ityb.qugou.service.content.DirectSupermarketService;
import com.ityb.qugou.service.info.ShopService;
import com.ityb.qugou.vo.manager.DirectSupermarketVo;

@Controller
@RequestMapping("/manager/directSupermarket")
public class DirectSupermarketContoller extends BaseController<DirectSupermarket> {

	@Autowired
	private AreaService areaService;
	@Autowired
	private DirectSupermarketService directSupermarketService;
	@Autowired
	private ShopService shopService;

	/**
	 * 跳转到直通超市页面
	 * @author yangbin
	 * @date 2018年4月15日
	 * @return
	 */
	@RequestMapping(value = "/show", method = { RequestMethod.GET, RequestMethod.POST })
	public String show(Model model) {
		List<Area> areaList = areaService.querySecondLevelArea();
		model.addAttribute("areaList", JsonUtils.objectToJson(areaList));
		return "directSupermarket-show";
	}

	/**
	 * 查询数据
	 * 
	 * @author yangbin
	 * @date 2018年4月15日
	 * @param model
	 * @param areaName
	 * @return
	 */
	@RequestMapping(value = "/query", method = { RequestMethod.GET, RequestMethod.POST })
	public String query(Model model, DirectSupermarketDto directSupermarketDto) {
		List<DirectSupermarketVo> list = this.directSupermarketService.queryDirectSupermarket(directSupermarketDto);
		model.addAttribute("list", list);
		model.addAttribute("directSupermarket", directSupermarketDto);
		return getListPage();
	}

	@Override
	protected String getListPage() {
		return "directSupermarket-list";
	}

	@Override
	protected BaseService<DirectSupermarket> getService() {
		return directSupermarketService;
	}

	@Override
	public String add(Model model,Map<String, Object> params) throws Exception {
		params.forEach((key,value)->{
			model.addAttribute(key,value);
		});
		List<Shop> shopList=shopService.queryShopByCity((String)params.get("city"));
		model.addAttribute("shopList", shopList);
		return add();
	}
	
	@RequestMapping(value="/desc", method = { RequestMethod.GET, RequestMethod.POST })
	public String desc(){
		return "directSupermarket-desc";
	}
	
	/**
	 * 根据商店Id得到对应的直通超市
	 * @author yangbin
	 * @date 2018年4月15日
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/getByShopId", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResultData<Boolean> getByShopId(@RequestParam Map<String,Object> params){
		List<DirectSupermarket> list = this.directSupermarketService.query(params);
		if(CollectionUtils.isNotEmpty(list)){
			return JsonResultData.success(true);
		}
		return JsonResultData.error("数据为空");
	}
	
	@Override
	public String add() throws Exception {
		return "directSupermarket-add";
	}
}
