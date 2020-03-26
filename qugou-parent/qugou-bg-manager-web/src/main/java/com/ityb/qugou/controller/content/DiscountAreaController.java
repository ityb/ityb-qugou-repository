package com.ityb.qugou.controller.content;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ityb.qugou.base.controller.BaseController;
import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.base.utils.JsonUtils;
import com.ityb.qugou.bo.common.FileBean;
import com.ityb.qugou.bo.common.UploadResultBean;
import com.ityb.qugou.common.FileUploadService;
import com.ityb.qugou.po.manager.DiscountArea;
import com.ityb.qugou.service.content.DiscountAreaService;

@Controller
@RequestMapping(value = "/manager/discountArea")
public class DiscountAreaController extends BaseController<DiscountArea> {

	@Autowired
	private DiscountAreaService discountAreaService;
	@Autowired
	private FileUploadService fileUploadService;
	private static final Logger LOGGER = LoggerFactory.getLogger(DiscountAreaController.class);
	/**
	 * 跳转到列表页面
	 * 
	 * @author yangbin
	 * @date 2018年4月14日
	 * @param discountArea
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/show", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(DiscountArea discountArea, Model model) {
		List<DiscountArea> list = this.discountAreaService.query(discountArea);
		model.addAttribute("list", JsonUtils.objectToJson(list));
		return getListPage();
	}

	/**
	 * 跳转到更新页面
	 */
	@Override
	public String update(Model model) {
		return "discountArea-update";
	}

	/**
	 * 跳转到数据显示页面
	 */
	@Override
	protected String getListPage() {
		return "discountArea-list";
	}

	@Override
	protected BaseService<DiscountArea> getService() {
		return discountAreaService;
	}
	
	@Override
	public String add(Model model,Map<String, Object> params) throws Exception {
		params.forEach((key,value)->{
			model.addAttribute(key,value);
		});
		return add();
	}
	@Override
	public String add() throws Exception {
		return "discountArea-add";
	}
	
	@RequestMapping(value = "/upload", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public UploadResultBean upload(HttpServletRequest request) {
		MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
		FileBean fileBean = fileUploadService.upload(file);
		UploadResultBean uploadResultBean = null;
		if (fileBean == null) {
			uploadResultBean = new UploadResultBean();
			uploadResultBean.setCode(404);// 表示成功
			uploadResultBean.setMsg("上传文件失败");
			LOGGER.error("upload...文件上传失败");
			return uploadResultBean;
		}
		uploadResultBean = new UploadResultBean();
		uploadResultBean.setCode(200);// 表示成功
		uploadResultBean.setSrc(fileBean.getPath());
		uploadResultBean.setMsg("上传文件成功");
		return uploadResultBean;
	}
}
