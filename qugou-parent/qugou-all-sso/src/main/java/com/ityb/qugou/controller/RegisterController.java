package com.ityb.qugou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyuncs.exceptions.ClientException;
import com.ityb.qugou.base.constant.BackgroundConstants;
import com.ityb.qugou.base.constant.CookieConstant;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.CookieUtils;
import com.ityb.qugou.base.utils.FileUploadUtils;
import com.ityb.qugou.base.utils.MessageSendUtils;
import com.ityb.qugou.base.utils.NumberUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.configuration.PropertiesConfig;
import com.ityb.qugou.dto.manager.MerchantDto;
import com.ityb.qugou.dto.manager.UserDto;
import com.ityb.qugou.service.UserService;
/**
 * 注册控制器
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@RequestMapping("/sso")
@Controller
public class RegisterController {
	@Autowired
	private UserService userService;
	@Autowired
	private PropertiesConfig propertiesConfig;
	
	private final static Logger LOGGER = Logger.getLogger(RegisterController.class);
	/**
	 * 跳转到登录界面
	 * @author yangbin
	 * @date 2018年3月2日
	 * @return
	 */
	@RequestMapping(value="/shop/register",method={RequestMethod.GET})
	public String register(){
		return "/shop/register";
	}
	
	/**
	 * 商家注册页面
	 * @author yangbin
	 * @date 2018年3月7日
	 * @return
	 */
	@RequestMapping(value="/shop/merchant/register",method={RequestMethod.GET})
	public String merchantRegister(){
		return "/shop/merchant-register";
	}
	
	
	
	/**
	 * 跳转到注册成功页面
	 * @author yangbin
	 * @date 2018年3月3日
	 * @return
	 */
	@RequestMapping(value="/shop/register/success",method={RequestMethod.GET})
	public String registerSuccess(){
		return "/shop/register-success";
	}
	
	/**
	 * 跳转到注册失败页面
	 * @author yangbin
	 * @date 2018年3月3日
	 * @return
	 */
	@RequestMapping(value="/shop/register/error",method={RequestMethod.GET})
	public String registerError(){
		return "/shop/register-error";
	}
	
	/**
	 * 注册
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param request
	 * @param userDto
	 * @return
	 */
	@RequestMapping(value="/shop/register",method={RequestMethod.POST})
	@ResponseBody
	public JsonResultData<String> doRegister(HttpServletRequest request,UserDto userDto){
		//将cookie中的验证码取出
		userDto.setUserType(BackgroundConstants.USER_TYPE_CUSTOMER);
		String verificationCode = CookieUtils.getCookieValue(request, CookieConstant.SHOP_VERIFICATION_CODE_KEY);
		String code = userDto.getVerificationCode();
		if(StringUtils.isBlank(code)||!code.equals(verificationCode)){
			return JsonResultData.error("验证码不正确");
		}
		try {
			this.userService.addUser(userDto);
			return JsonResultData.success("注册成功");
		} catch (Exception e) {
			LOGGER.error("doRegister....注册失败");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 利用手机发送验证码
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param phone
	 * @param userType 表示客户类型，1表示管理员，2表示商家，3表示商城用户
	 * @return
	 */
	@RequestMapping(value="/shop/register/sendVerificationCode",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> sendVerificationCode(HttpServletResponse response,String phone,Integer userType){
		if(userType==null){
			return JsonResultData.error("参数非法");
		}
		if(!StringUtils.isPhone(phone)){
			return JsonResultData.error("手机非法，验证码发送失败");
		}
		//参数6位的验证码
		String number=NumberUtils.makeNumber(6);
		//进行手机发送
		try {
			MessageSendUtils.sendVerificationCode(phone, number);
			//成功之后执行存
			if(BackgroundConstants.USER_TYPE_CUSTOMER==userType){
				CookieUtils.setCookie(response, CookieConstant.SHOP_VERIFICATION_CODE_KEY, number,30*60);
			}else if(BackgroundConstants.USER_TYPE_MERCHANT==userType){
				CookieUtils.setCookie(response, CookieConstant.MERCHANT_VERIFICATION_CODE_KEY, number,30*60);
			}
			return JsonResultData.success(true);
		} catch (ClientException e) {
			LOGGER.error("sendVerificationCode...发送失败",e);
			return JsonResultData.error("验证码发送失败");
		}
	}
	
	/**
	 * 判断用户的验证码是否与cookie中的验证码相等
	 * @author yangbin
	 * @date 2018年3月13日
	 * @param userDto
	 * @return
	 */
	@RequestMapping(value="/shop/register/isEqVerificationCode",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> isEqVerificationCode(HttpServletRequest request,UserDto userDto){
		if(userDto.getUserType()==null){
			return JsonResultData.error("参数非法");
		}
		if(StringUtils.isBlank(userDto.getVerificationCode())){
			return JsonResultData.error("验证码不能为空");
		}
		//获取cookie中的验证码
		String code=CookieUtils.getCookieValue(request, CookieConstant.MERCHANT_VERIFICATION_CODE_KEY);
		if(userDto.getVerificationCode().equals(code)){
			return JsonResultData.success(true);
		}
		return JsonResultData.error("验证码不正确");
	}	
	
	/**
	 * 商家注册操作
	 * @author yangbin
	 * @date 2018年3月15日
	 * @return
	 */
	@RequestMapping(value="/shop/merchant/register",method={RequestMethod.POST})
	public String doMerchantRegister(Model model,MerchantDto merchantDto,@RequestParam("idcardFile") MultipartFile idcardFile,@RequestParam("businessLicenceFile") MultipartFile businessLicenceFile){
		if(idcardFile==null||businessLicenceFile==null){
			model.addAttribute("msg","上传的文件不能为空");
			return "/shop/register-error";
		}
		merchantDto.setIdentityCardPic(FileUploadUtils.upload(idcardFile, propertiesConfig.getFilePhysicsAddress(), propertiesConfig.getFileServerAddress()));
		merchantDto.setBusinessLicencePic(FileUploadUtils.upload(businessLicenceFile, propertiesConfig.getFilePhysicsAddress(), propertiesConfig.getFileServerAddress()));
		try {
			this.userService.addMerchant(merchantDto);
		} catch (Exception e) {
			LOGGER.error("doMerchantRegister....."+e.getMessage());
			model.addAttribute("msg",e.getMessage());
			return "/shop/register-error";
		}
		return "/shop/merchant-register-success";
	}
}
