package com.ityb.qugou.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.bo.manager.UserBean;
import com.ityb.qugou.dto.manager.UserDto;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.service.UserService;
/**
 * 用户信息控制类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/sso")
public class UserController {
	
	@Autowired
	private UserService userService;

	private final static Logger LOGGER = Logger.getLogger(UserController.class);
	/**
	 * 获取一个用户信息
	 * @author yangbin
	 * @date 2018年3月2日
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/user/isGetUser",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> getUser(User user){
		try {
			User userEntity=userService.getUser(user);
			if(userEntity!=null){
				return JsonResultData.success(true);
			}
			return JsonResultData.error("不存在该用户");
		} catch (Exception e) {
			LOGGER.error("getUser....获取用户基本信息失败");
			return JsonResultData.error(e.getMessage());
		}
	}
	/**
	 * 获取一个用户信息详细信息
	 * @author yangbin
	 * @date 2018年3月2日
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/user/isGetUserInfo",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> getUserInfo(UserDto userDto){
		try {
			UserBean userBean=userService.getUserInfo(userDto);
			if(userBean!=null){
				return JsonResultData.success(true);
			}
			return JsonResultData.error("不存在该用户信息");
		} catch (Exception e) {
			LOGGER.error("getUserInfo....获取用户详细信息失败");
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取商家用户信息
	 * @author yangbin
	 * @date 2018年3月13日
	 * @param userDto
	 * @return
	 */
	@RequestMapping(value="/user/isGetMechant",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> getMechant(UserDto userDto){
		try {
			User user=userService.getMerchantUser(userDto);
			if(user!=null){
				return JsonResultData.success(true);
			}
			return JsonResultData.error("不存在该用户信息");
		} catch (Exception e) {
			LOGGER.error("getUserInfo....获取用户详细信息失败");
			return JsonResultData.error(e.getMessage());
		}
	}
}
