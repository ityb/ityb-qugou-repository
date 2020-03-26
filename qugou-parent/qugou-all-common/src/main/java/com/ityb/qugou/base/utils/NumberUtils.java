package com.ityb.qugou.base.utils;

import java.util.Random;

/**
 * 该类主要是是负责数字有关的处理
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils{
	
	/**
	 * 产生数字
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param n 表示产生几位的数字
	 * @return
	 */
	public static String makeNumber(Integer n) {
		Random r = new Random();
		// 9999999 可以生成7位 而9999能产生四位
		String t = "";
		for (int i = 0; i < n; i++) {
			t += "9";
		}
		String num = r.nextInt(Integer.parseInt(t)) + "";
		StringBuffer sb = new StringBuffer();
		// 如果不够4位，前面补零
		for (int i = 0; i < n - num.length(); i++) {
			sb.append("0");
		}
		num = sb.toString() + num;
		return num;
	}
	public static void main(String[] args) {
		System.out.println(makeNumber(6));
	}
	
	/**
	 * 判断数字是否是为空
	 * @author yangbin
	 * @date 2018年3月17日
	 * @param priceLeft
	 * @return
	 */
	public static boolean isNotNull(Object number) {
		if(number==null){
			return false;
		}
		if(number instanceof Integer){
			Integer num=(Integer)number;
			if(num==null||num.intValue()==0){
				return false;
			}
		}else if(number instanceof Double){
			Double num=(Double)number;
			if(num==null||num.doubleValue()==0){
				return false;
			}
		}else if(number instanceof Long){
			Long num=(Long)number;
			if(num==null||num.longValue()==0){
				return false;
			}
		}else if(number instanceof Float){
			Float num=(Float)number;
			if(num==null||num.floatValue()==0){
				return false;
			}
		}else{//表示是其他的类型，不支持
			return false;
		}
		return true;
	}
	
	/**
	 * 判断数字为空
	 * @author yangbin
	 * @date 2018年3月23日
	 * @param number
	 * @return
	 */
	public static boolean isNull(Object number) {
		return !NumberUtils.isNotNull(number);
	}
}
