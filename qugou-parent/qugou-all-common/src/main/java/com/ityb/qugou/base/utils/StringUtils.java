package com.ityb.qugou.base.utils;

import org.springframework.util.DigestUtils;
import java.util.UUID;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	public static String toPreUpperCase(String s) {
		if (isBlank(s)) {
			return s;
		}
		String head = s.substring(0, 1);
		String tail = s.substring(1, s.length());
		return head.toUpperCase() + tail;
	}

	public static String getRandomStr() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static boolean isString(String str) {
		String s = str.substring(str.lastIndexOf(".") + 1, str.length());
		if ("String".equals(s)) {
			return true;
		}
		return false;
	}

	public static String MD5Encrypt(String password) {
		return DigestUtils.md5DigestAsHex(password.getBytes());
	}

	public static String upperCase2Underline(String str) {
		StringBuffer data = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				data.append("_");
				data.append(Character.toLowerCase(c));
			} else {
				data.append(c);
			}
		}
		return data.toString();
	}

	public static void main(String[] args) {
		System.out.println(getRandomStr());
		System.out.println(MD5Encrypt("yangbin"));
	}

	/**
	 * 首字母小写
	 * 
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月24日
	 * @param substring
	 * @return
	 */
	public static String toPreLowerCase(String s) {
		if (isBlank(s)) {
			return s;
		}
		String head = s.substring(0, 1);
		String tail = s.substring(1, s.length());
		return head.toLowerCase() + tail;
	}

	/**
	 * 判断是都是手机号码
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		if (isBlank(phone)) {
			return false;
		}
		String regex = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\\d{8})$";
		if (!phone.matches(regex)) {
			return false;
		}
		return true;
	}
}
