package com.ityb.qugou.base.exception;

import com.ityb.qugou.base.utils.NumberUtils;

/**
 * 断言工具类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class Assert extends org.springframework.util.Assert {
	/**
	 * 判断是会否是手机号码
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param object
	 * @param message
	 */
	public static void isPhone(String phone, String message) {
		Assert.hasText(phone,message);
		String regex="^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\\d{8})$";
		if (!phone.matches(regex)) {
			throw new ServiceException(message);
		}
	}
	/**
	 * 判断是否是密码
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param password
	 * @param message
	 */
	public static void isPassword(String password, String message) {
		Assert.hasText(password,message);
		String regex="^(?![^a-zA-Z]+$)(?!\\D+$).{6,20}$";
		if (!password.matches(regex)) {
			throw new ServiceException(message);
		}
	}
	/**
	 * 判断字符串最大长度
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param text
	 * @param length
	 * @param message
	 */
	public static void isMaxLength(String text,int length,String message){
		Assert.hasText(text,message);
		if(text.length()>length){
			throw new ServiceException(message);
		}
	}
	/**
	 * 判断字符串最小长度
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param text
	 * @param length
	 * @param message
	 */
	public static void isMinLength(String text,int length,String message){
		Assert.hasText(text,message);
		if(text.length()<length){
			throw new ServiceException(message);
		}
	}
	/**
	 * 判断字符串范围
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param text
	 * @param length
	 * @param message
	 */
	public static void isBetweenLength(String text,int minLength,int maxLength,String message){
		Assert.hasText(text,message);
		if(text.length()>maxLength||text.length()<minLength){
			throw new ServiceException(message);
		}
	}
	
	/**
	 * 判断是否是false
	 * @author yangbin
	 * @date 2018年3月22日
	 * @param expression
	 * @param message
	 */
	public static void isFalse(boolean expression, String message) {
		if (expression) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 判断数字是否为空
	 * @author yangbin
	 * @date 2018年3月22日
	 * @param number
	 * @param message
	 */
	public static void NumberIsNotNull(Object number,String message){
		if(!NumberUtils.isNotNull(number)){
			throw new IllegalArgumentException(message);
		}
	}
	public static void main(String[] args) {
		try {
			Assert.isPhone("157767426510", "非法手机号码");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
