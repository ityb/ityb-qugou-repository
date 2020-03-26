package com.ityb.qugou.contoller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ityb.qugou.base.constant.CommonConstants;
import com.ityb.qugou.base.constant.StatusConstants;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.DateUtils;
import com.ityb.qugou.base.utils.ExcelUtils;
import com.ityb.qugou.base.utils.JsonUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.bo.product.ProductSpecificationBean;
import com.ityb.qugou.bo.product.SpecificationBean;
import com.ityb.qugou.common.MerchantBaseController;
import com.ityb.qugou.dto.product.ProductSpecificationDto;
import com.ityb.qugou.po.product.Product;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.remoting.ProductServiceRemoting;
import com.ityb.qugou.vo.merchant.ResultVo;
import com.ityb.qugou.vo.product.ProductSpecificationVo;

/**
 * 库存管理
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/merchant/stock")
public class StockController extends MerchantBaseController{

	@Autowired
	private ProductServiceRemoting productServiceRemoting;
	
	/**
	 * 跳转到lis页面
	 * @author yangbin
	 * @date 2018年1月21日
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list() {
		return "stock-list";
	}
	
	/**
	 * 获取数据
	 * @author yangbin
	 * @date 2018年1月25日
	 * @param productSpecificationDto
	 * @return
	 */
	@RequestMapping(value = "/list-data", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultVo<ProductSpecificationVo> list(HttpServletRequest request,ProductSpecificationDto productSpecificationDto) {
		productSpecificationDto.setCreater(getCurrentUserId(request));
		JsonResultData<List<ProductSpecificationVo>> productJsonResult = productServiceRemoting
				.queryProductSpecification(productSpecificationDto);
		if(productJsonResult.getStatus()==StatusConstants.STATE_FAIL){
			return ResultVo.error("获取数据列表失败");
		}
		JsonResultData<Integer> countJsonResult=productServiceRemoting.countProductSpecification(productSpecificationDto);
		if(countJsonResult.getStatus()==StatusConstants.STATE_FAIL){
			return ResultVo.error("获取数据数量失败");
		}
		return  ResultVo.success(productJsonResult.getData(), countJsonResult.getData());
	}
	
	/**
	 * 跳转到添加页面
	 * @author yangbin
	 * @date 2018年1月27日
	 * @return
	 */
	@RequestMapping(value="/add",method={ RequestMethod.GET})
	public String add(){
		return "stock-productSpecification-add";
	}
	/**
	 * 添加商品规格信息
	 * @author yangbin
	 * @date 2018年1月27日
	 * @return
	 */
	@RequestMapping(value="/add",method={ RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> add(String content,String creater){
		if(StringUtils.isBlank(content)){
			return JsonResultData.error("传入的内容不能为空");
		}
		ProductSpecificationBean productSpecificationBean = JsonUtils.jsonToPojo(content, ProductSpecificationBean.class);
		productSpecificationBean.setCreater(creater);
		productSpecificationBean.setCtime(new Date());
		JsonResultData<Boolean> jsonResult=productServiceRemoting.addProductSpecification(productSpecificationBean);
		if(jsonResult.getStatus()==CommonConstants.STATE_FAIL){
			return JsonResultData.error(jsonResult.getMsg());
		}
		return JsonResultData.success("添加成功",jsonResult.getData());
	}
	/**
	 * 添加商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @return
	 */
	@RequestMapping(value="/addSpecification",method={RequestMethod.GET})
	public String addSpecification(HttpServletRequest request,Model model){
		Product product=new Product();
		product.setCreater(getCurrentUserId(request));
		JsonResultData<List<Product>> jsonResultData=this.productServiceRemoting.queryProduct(product);
		if(!CollectionUtils.isEmpty(jsonResultData.getData())){
			model.addAttribute("productList",jsonResultData.getData());
		}
		return "stcok-specification-add";
	}
	/**
	 * 添加一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	@RequestMapping(value="/addSpecification",method={RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> addSpecification(HttpServletRequest request,ProductSpecification productSpecification){
		if(StringUtils.isBlank(productSpecification.getName())){
			return JsonResultData.error("规格名称不能为空");
		}
		if(StringUtils.isBlank(productSpecification.getProductId())){
			return JsonResultData.error("商品ID不能为空");
		}
		if(productSpecification.getPrice()==null||productSpecification.getPrice()<=0){
			return JsonResultData.error("商品单价不能为空或者小于等于0");
		}
		if(productSpecification.getStock()==null||productSpecification.getStock()<=0){
			return JsonResultData.error("商品库存量不能为空或者小于等于0");
		}
		if(productSpecification.getWeight()==null||productSpecification.getWeight()<=0){
			return JsonResultData.error("商品净重不能为空或者小于等于0");
		}
		productSpecification.setCreater(getCurrentUserId(request));
		productSpecification.setCtime(new Date());
		JsonResultData<Boolean> jsonResultData=this.productServiceRemoting.addSpecification(productSpecification);
		if(jsonResultData.getStatus()==StatusConstants.STATE_FAIL){
			return JsonResultData.error(jsonResultData.getMsg());
		}
		return  JsonResultData.success(jsonResultData.getData());
	}
	
	/**
	 * 获取商品编号下面的商品规格数量
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/countSpecification",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Integer> countSpecification(HttpServletRequest request,Product product){
		product.setCreater(getCurrentUserId(request));
		JsonResultData<Integer> jsonResultData=this.productServiceRemoting.countSpecification(product);
		return jsonResultData;
	}
	
	/**
	 * 得到商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/getProductSpecification",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<ProductSpecification> getProductSpecification(HttpServletRequest request,ProductSpecification productSpecification){
		if(StringUtils.isBlank(productSpecification.getProductId())){
			return JsonResultData.error("商品ID不能为空");
		}
		if(StringUtils.isBlank(productSpecification.getName())){
			return JsonResultData.error("规格名称不能为空");
		}
		productSpecification.setCreater(getCurrentUserId(request));
		JsonResultData<ProductSpecification> jsonResultData=this.productServiceRemoting.getProductSpecification(productSpecification);
		return jsonResultData;
	}
	
	/**
	 * 导出库存信息
	 * @author yangbin
	 * @date 2018年2月4日
	 * @param response
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/export",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> exportInfo(HttpServletRequest request,HttpServletResponse response,String ids) throws Exception{
		if(StringUtils.isBlank(ids)){
			return JsonResultData.error("请重新选择商品");
		}
		JsonResultData<List<ProductSpecificationVo>> jsonResultData=this.productServiceRemoting.queryProductByIds(ids);
		List<ProductSpecificationVo> productSpecificationVoList = jsonResultData.getData();
		if(jsonResultData.getStatus()==StatusConstants.STATE_FAIL||CollectionUtils.isEmpty(jsonResultData.getData())){
			return JsonResultData.error("导出失败");
		}
		if(!CollectionUtils.isEmpty(productSpecificationVoList)){
			/**
			 * 进行导出处理
			 */
			String[] rowName={"序号","商品编号","商品名称","商品单价（元）","商品净重（kg）","规格名称","库存量（件）","添加时间"};
			List<Object[]> list=new ArrayList<>();
			Object[] item=null;
			ProductSpecificationVo productSpecificationVo=null;
			for(int i=0;i<productSpecificationVoList.size();i++){
				item=new Object[rowName.length];
				productSpecificationVo=productSpecificationVoList.get(i);
				item[0]=i+1;
				item[1]=productSpecificationVo.getProductNumber();
				item[2]=productSpecificationVo.getProductName();
				item[3]=productSpecificationVo.getPrice();
				item[4]=productSpecificationVo.getWeight();
				item[5]=productSpecificationVo.getProductSpecificationName();
				item[6]=productSpecificationVo.getStock();
				item[7]=DateUtils.date2Str(productSpecificationVo.getProductAddDate());
				list.add(item);
			}
			ExcelUtils.export(request,response,"库存信息", rowName, list,"库存信息表");
		}
		return JsonResultData.success("导出成功",true);
	}
	/**
	 * 导出全部的库存信息
	 * @author yangbin
	 * @date 2018年2月4日
	 * @param response
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/exportAll",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> exportAll(HttpServletResponse response,HttpServletRequest request) throws Exception{
		ProductSpecificationDto productSpecificationDto=new ProductSpecificationDto();
		productSpecificationDto.setCreater(getCurrentUserId(request));
		JsonResultData<List<ProductSpecificationVo>> jsonResultData=this.productServiceRemoting.queryProductSpecification(productSpecificationDto);
		List<ProductSpecificationVo> productSpecificationVoList = jsonResultData.getData();
		if(jsonResultData.getStatus()==StatusConstants.STATE_FAIL||CollectionUtils.isEmpty(jsonResultData.getData())){
			return JsonResultData.error("导出失败");
		}
		if(!CollectionUtils.isEmpty(productSpecificationVoList)){
			/**
			 * 进行导出处理
			 */
			String[] rowName={"序号","商品编号","商品名称","商品单价（元）","商品净重（kg）","规格名称","库存量（件）","添加时间"};
			List<Object[]> list=new ArrayList<>();
			Object[] item=null;
			ProductSpecificationVo productSpecificationVo=null;
			for(int i=0;i<productSpecificationVoList.size();i++){
				item=new Object[rowName.length];
				productSpecificationVo=productSpecificationVoList.get(i);
				item[0]=i+1;
				item[1]=productSpecificationVo.getProductNumber();
				item[2]=productSpecificationVo.getProductName();
				item[3]=productSpecificationVo.getPrice();
				item[4]=productSpecificationVo.getWeight();
				item[5]=productSpecificationVo.getProductSpecificationName();
				item[6]=productSpecificationVo.getStock();
				item[7]=DateUtils.date2Str(productSpecificationVo.getProductAddDate());
				list.add(item);
			}
			ExcelUtils.export(request,response,"库存信息", rowName, list,"库存信息表");
		}
		return JsonResultData.success("导出成功",true);
	}
	
	/**
	 * 跳转到商品规格修改页面
	 * @author yangbin
	 * @date 2018年1月28日
	 * @return
	 */
	@RequestMapping(value="/updateSpecification",method={RequestMethod.GET})
	public String updateSpecification(HttpServletRequest request,Model model,String productSpecificationId){
		ProductSpecificationDto productSpecificationDto=new ProductSpecificationDto();
		productSpecificationDto.setProductSpecificationId(productSpecificationId);
		productSpecificationDto.setCreater(getCurrentUserId(request));
		JsonResultData<List<ProductSpecificationVo>> jsonResultData=this.productServiceRemoting.queryProductSpecification(productSpecificationDto);
		ProductSpecificationVo productSpecificationVo=jsonResultData.getData().get(0);
		model.addAttribute("productSpecificationVo",productSpecificationVo);
		return "stcok-specification-update";
	}
	/**
	 * 更新一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	@RequestMapping(value="/updateSpecification",method={RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> updateSpecification(HttpServletRequest request,ProductSpecification productSpecification){
		if(StringUtils.isBlank(productSpecification.getId())){
			return JsonResultData.error("商品规格ID不能为空");
		}
		if(productSpecification.getPrice()==null||productSpecification.getPrice()<=0){
			return JsonResultData.error("商品单价不能为空或者小于等于0");
		}
		if(productSpecification.getStock()==null||productSpecification.getStock()<=0){
			return JsonResultData.error("商品库存量不能为空或者小于等于0");
		}
		if(productSpecification.getWeight()==null||productSpecification.getWeight()<=0){
			return JsonResultData.error("商品净重不能为空或者小于等于0");
		}
		productSpecification.setMtime(new Date());
		JsonResultData<Boolean> jsonResultData=this.productServiceRemoting.updateSpecification(productSpecification);
		if(jsonResultData.getStatus()==StatusConstants.STATE_FAIL){
			return JsonResultData.error(jsonResultData.getMsg());
		}
		return  JsonResultData.success(jsonResultData.getData());
	}
	

	/**
	 * 删除一个商品规格
	 * @author yangbin
	 * @date 2018年2月4日
	 * @param request
	 * @param model
	 * @param productSpecificationId
	 * @return
	 */
	@RequestMapping(value="/deleteSpecification",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public  JsonResultData<Boolean> deleteSpecification(HttpServletRequest request,Model model,String specificationId){
		if(StringUtils.isBlank(specificationId)){
			return JsonResultData.error("规格ID不能为空");
		}
		ProductSpecification productSpecification=new ProductSpecification();
		productSpecification.setId(specificationId);
		productSpecification.setDtime(new Date());
		JsonResultData<Boolean> jsonResultData=this.productServiceRemoting.deleteSpecification(productSpecification);
		if(jsonResultData.getStatus()==StatusConstants.STATE_FAIL){
			return JsonResultData.error(jsonResultData.getMsg());
		}
		return  JsonResultData.success(jsonResultData.getData());
	}

	/**
	 * 根据商品po得到对应的规格列表
	 * @author yangbin
	 * @date 2018年2月9日
	 * @param request
	 * @param model
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/querySpecificationByPoduct",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public  JsonResultData<List<SpecificationBean>> querySpecificationByPoduct(HttpServletRequest request,Model model,Product product){
		if(StringUtils.isBlank(product.getId())){
			return JsonResultData.error("商品ID不能为空");
		}
		JsonResultData<List<SpecificationBean>> jsonResultDataList=this.productServiceRemoting.querySpecificationByPoduct(product);
		if(jsonResultDataList.getStatus()==StatusConstants.STATE_FAIL){
			return JsonResultData.error(jsonResultDataList.getMsg());
		}
		return jsonResultDataList;
	}
}
