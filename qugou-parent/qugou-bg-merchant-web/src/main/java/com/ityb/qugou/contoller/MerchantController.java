package com.ityb.qugou.contoller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.bo.common.FileBean;
import com.ityb.qugou.bo.common.UploadResultBean;
import com.ityb.qugou.common.FileUploadService;
import com.ityb.qugou.common.MerchantBaseController;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.service.ShopService;
import com.ityb.qugou.service.UserService;

@Controller
@RequestMapping(value="/merchant/info")
public class MerchantController extends MerchantBaseController{
	
	@Autowired
	private ShopService shopService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private UserService userService;
	private final static Logger LOGGER = Logger.getLogger(MerchantController.class);
	/**
	 * 跳转到
	 * @author yangbin
	 * @date 2018年4月13日
	 * @return
	 */
	@RequestMapping(value="/show",method={RequestMethod.GET})
	public String merchantInfo(HttpServletRequest request,Model model){
		try {
			Shop shop=new Shop();
			shop.setUserId(getCurrentUserId(request));
			Shop shopEntity=shopService.getShopInfo(shop);
			model.addAttribute("shop",shopEntity);
		} catch (Exception e) {
			LOGGER.error("merchantInfo....获取商店信息出错",e);
		}
		return "merchant-info";
	}
	
	/**
	 * 上传文件接口
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/upload",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public UploadResultBean upload(HttpServletRequest request, Model model) {
		MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
		FileBean fileBean = fileUploadService.upload(file);
		UploadResultBean uploadResultBean = null;
		if (fileBean == null) {
			uploadResultBean = new UploadResultBean();
			uploadResultBean.setCode(-1);// 表示成功
			uploadResultBean.setMsg("上传文件失败");
			return uploadResultBean;
		}
		uploadResultBean = new UploadResultBean();
		uploadResultBean.setCode(0);// 表示成功
		uploadResultBean.setMsg("上传文件成功");
		Map<String, Object> map = new HashMap<>();
		map.put("src", fileBean.getPath());
		map.put("title", fileBean.getOriginalFileName());
		uploadResultBean.setData(map);
		return uploadResultBean;
	}
	
	/**
	 * 修改商店信息
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param shop
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> update(Shop shop){
		try {
			this.shopService.update(shop);
		} catch (Exception e) {
			LOGGER.error("update....更新商店信息失败",e);
			return JsonResultData.error(e.getMessage());
		}
		return JsonResultData.success(true);
	}
	
	/**
	 * 跳转到修改密码
	 * @author yangbin
	 * @date 2018年5月15日
	 * @return
	 */
	@RequestMapping(value="/modifyPassword",method={RequestMethod.GET})
	public String modifyPassword(){
		return "merchant-modifyPassword";
	}
	
	/**
	 * 修改密码操作
	 * @author yangbin
	 * @date 2018年5月15日
	 * @return
	 */
	@RequestMapping(value="/modifyPassword",method={RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> doModifyPassword(HttpServletRequest request,User user,String oldPassword){
		user.setId(getCurrentUserId(request));
		try {
			this.userService.modifyPassword(user,oldPassword);
		} catch (Exception e) {
			LOGGER.error("doModifyPassword....修改商家信息失败",e);
			return JsonResultData.error(e.getMessage());
		}
		return JsonResultData.success(true);
	}
}
