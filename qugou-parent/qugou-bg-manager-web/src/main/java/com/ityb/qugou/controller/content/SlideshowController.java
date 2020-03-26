package com.ityb.qugou.controller.content;

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
import com.ityb.qugou.bo.common.FileBean;
import com.ityb.qugou.bo.common.UploadResultBean;
import com.ityb.qugou.common.FileUploadService;
import com.ityb.qugou.po.manager.Slideshow;
import com.ityb.qugou.service.content.SlideshowService;

/**
 * 
 * @author 杨彬
 * @Date 2017年12月5日
 */
@Controller
@RequestMapping("/manager/slideshow")
public class SlideshowController extends BaseController<Slideshow> {

	@Autowired
	private SlideshowService slideshowService;
	@Autowired
	private FileUploadService fileUploadService;
	private static final Logger LOGGER = LoggerFactory.getLogger(SlideshowController.class);

	/**
	 * 跳转到轮播图页面
	 */
	@Override
	protected String getListPage() {
		return "slideshow-list";
	}

	/**
	 * 跳转到添加页面
	 */
	@Override
	public String add() throws Exception {
		return "slideshow-add";
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

	/**
	 * 跳转到更新页面
	 */
	@Override
	public String update(Model model) {
		return "slideshow-update";
	}

	@Override
	protected BaseService<Slideshow> getService() {
		return slideshowService;
	}
}
