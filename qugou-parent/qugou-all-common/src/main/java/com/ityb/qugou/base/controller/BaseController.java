package com.ityb.qugou.base.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ityb.qugou.base.constant.CommonConstants;
import com.ityb.qugou.base.core.CoreEntity;
import com.ityb.qugou.base.dto.LayuiDto;
import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.base.utils.JsonResult;
import com.ityb.qugou.base.utils.JsonUtils;
import com.ityb.qugou.base.utils.StringUtils;

/**
 * 
 * @author 杨彬
 * @Date 2017年12月5日
 * TODO 通用controller的设计，包括了添加删除，修改，查询列表等通用方法
 * @param <T>
 */
public abstract class BaseController<T extends CoreEntity> {

	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, @RequestParam Map<String, Object> params)
			throws Exception {
		List<T> list = getService().query(params);
		model.addAttribute("list", list);
		Integer total = getService().count(params);
		model.addAttribute("total", total);
		params.forEach((key,value)->{
			model.addAttribute(key,value);
		});
		return getListPage();
	}

	protected abstract String getListPage();

	protected abstract BaseService<T> getService();

	/**
	 * 获取数据返回给layui前端json格式
	 */
	@RequestMapping("/list-data")
	@ResponseBody
	public LayuiDto<T> getListData() throws Exception {
		LayuiDto<T> layuiDto = new LayuiDto<T>();
		layuiDto.setMsg("数据加载成功");
		layuiDto.setRel(true);
		layuiDto = setLayuiMsg(layuiDto);
		BaseService<T> baseService = getService();
		List<T> list = baseService.getListData();
		layuiDto.setList(list);
		return layuiDto;
	}

	public LayuiDto<T> setLayuiMsg(LayuiDto<T> layui) {
		return layui;
	}

	/**
	 * 添加一条新记录
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model,@RequestParam Map<String, Object> params) throws Exception {
		return add(model,params);
	}

	/**
	 * 如果子类需要在add中做其他的操作可以覆写该方法
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String add(Model model,Map<String, Object> params) throws Exception {
		return add();
	}

	public abstract String add() throws Exception;

	/**
	 * 保存一条记录带附件上传的记录
	 */
	@RequestMapping("/saveDtoFile")
	@ResponseBody
	public String saveDtoAndFile(T dto, @RequestParam("file") MultipartFile file) throws Exception {
		dto = saveFile(dto, file);
		getService().save(dto);
		return addSuccess(dto);
		
	}
	
	/**
	 * 添加成功后的响应
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月10日
	 * @param response
	 * @TODO
	 */
	public String addSuccess(T dto) {
		JsonResult jsonResult = JsonResult.build(CommonConstants.STATE_SUCEESS, "添加成功",dto.getId());
		return JsonUtils.objectToJson(jsonResult);
	}

	/**
	 * 保存文件
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月10日
	 * @param dto
	 * @throws Exception
	 * @TODO
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(T dto) throws Exception {
		dto = setSaveValue(dto);
		getService().save(dto);
		return addSuccess(dto);
	}
	/**
	 * 设置保存的值，主键
	 * @author yangbin
	 * @date 2017年12月30日
	 * @param dto
	 * @return
	 */
	public T setSaveValue(T dto){
		return dto;
	}
	
	/**
	 * 
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月5日
	 * @param dto
	 * @param file
	 * @return
	 * @TODO 主要是用于保存文件
	 */
	public T saveFile(T dto, MultipartFile file) {
		return dto;
	}

	/**
	 * 更新一条记录
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model,T dto) throws Exception {
		return update(model,dto);
	}
	
	/**
	 * 根据ID进行查询数据，并且封装成对象，放入到model中
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月24日
	 * @param model
	 * @param dto
	 * @return
	 */
	public String update(Model model,T dto){
		T one = this .getOneById(dto.getId());
		if(one!=null){
			String name = one.getClass().getName();
			String modelName=StringUtils.toPreLowerCase(name.substring(name.lastIndexOf(".")+1,name.length()));
			model.addAttribute(modelName,one);
		}
		return update(model);
	}

	/**
	 * 跳转到更新页面
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月24日
	 * @return
	 */
	public String update(Model model) {
		return "";
	}

	/**
	 * 保存更新
	 * 
	 * @param dto
	 * @throws Exception
	 */
	@RequestMapping("/save-update")
	@ResponseBody
	public String saveUpdate(T dto) throws Exception {
		dto = setUpdateValue(dto);
		Integer updateLine = getService().saveUpdate(dto);
		if(updateLine>0){
			return updateSuccess();
		}else{
			return updateFail();
		}
	}
	
	@RequestMapping("/save-update-file")
	@ResponseBody
	public String saveUpdateDtoFile(T dto,@RequestParam("file") MultipartFile file) throws Exception {
		dto = setUpdateValue(dto);
		dto = saveFile(dto, file);
		Integer updateLine = getService().saveUpdate(dto);
		if(updateLine.intValue()>0){
			return updateSuccess();
		}else{
			return updateFail();
		}
	}

	/**
	 * 更新失败返回信息
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月24日
	 * @return
	 */
	public String updateFail() {
		JsonResult jsonResult = JsonResult.build(CommonConstants.ERROE_STATUS_CODE, "更新成功");
		return JsonUtils.objectToJson(jsonResult);
	}

	/**
	 * 更新成功状态码
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月24日
	 * @return
	 */
	public String updateSuccess() {
		JsonResult jsonResult = JsonResult.build(CommonConstants.SUCCESS_STATUS_CODE, "更新成功");
		return JsonUtils.objectToJson(jsonResult);
	}

	/**
	 * 如果子类需要补充dto的属性可以覆写该方法
	 * 
	 * @param dto
	 * @return
	 */
	public T setUpdateValue(T dto) {
		return dto;
	}

	/**
	 * 删除一条记录
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id) throws Exception {
		getService().delete(id);
		return  deleteSuccess();
	}

	/**
	 * 递归的删除一条记录
	 * @author yangbin
	 * @date 2017年12月30日
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteCycle")
	@ResponseBody
	public String deleteCycle(String id) throws Exception{
		getService().deleteCycle(id);
		return  deleteSuccess();
	}
	
	/**
	 * 
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月10日
	 * @return
	 * @TODO
	 */
	public String deleteSuccess() {
		JsonResult jsonResult = JsonResult.build(CommonConstants.SUCCEESS, "删除成功");
		return JsonUtils.objectToJson(jsonResult);
	}
	
	/**
	 * 
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月24日
	 * @param id 
	 * @return
	 */
	public T getOneById(String id){
		return getService().getOneById(id);
	}
}
