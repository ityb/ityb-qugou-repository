package com.ityb.qugou.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	// 时间变为日期，比如2017-05-14 15:30:20 变为 2017-05-14 0:0:0
	public static Date time2Date(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(time);
		try {
			Date date = sdf.parse(s);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	// 得到几个月之后的日期
	public static Date getAfterDate(Date date, Integer month) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}

	/**
	 * 获取几分钟之前的的日期
	 * 
	 * @author yangbin
	 * @date 2018年4月15日
	 * @param Minute
	 * @return
	 */
	public static Date getCurrentTimeBeforeMinuteDate(Integer Minute) {
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.MINUTE, -Minute);// 3分钟之前的时间
		Date beforeD = beforeTime.getTime();
		return beforeD;
	}

	// 日期转换为字符串
	public static String date2Str(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sdf.format(date);
		return s;
	}

	public static String date2Str(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String s = sdf.format(date);
		return s;
	}

	// 字符串转日期
	public static Date str2Date(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static String getYear(Date date) {
		String year = new SimpleDateFormat("yyyy").format(date);
		return year;
	}

	public static String getLastYear(Date date) {
		String year = new SimpleDateFormat("yyyy").format(date);
		int parseInt = Integer.parseInt(year);
		return (parseInt - 1) + "";
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.date2Str(DateUtils.getCurrentTimeBeforeMinuteDate(15), "yyMMddHHmm"));
	//	System.out.println(DateUtils.date2Str(DateUtils.getCurrentTimeBeforeMinuteDate(15), "yyyyMMddHHmm"));
	}

	/**
	 * 获取月数
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param date
	 * @return
	 */
	public static Integer getMonth(Date date) {
		String month = new SimpleDateFormat("MM").format(date);
		return Integer.parseInt(month);
	}

	/**
	 * 获取当前时间所在的季度
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param date
	 * @return
	 */
	public static Integer getSeason(Date date) {
		Integer month = DateUtils.getMonth(date);
		if(month>=1&&month<=3){
			return 1;
		}else if(month>=4&&month<=6){
			return 2;
		}else if(month>=7&&month<=9){
			return 3;
		}else{
			return 4;
		}
	}
	
	/**
	 * 获取季度的范围月数
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param season
	 * @return
	 */
	public static List<Integer> getMonthsBySeason(Integer season){
		List<Integer> list=new ArrayList<>();
		int start=0;
		if(season<1||season>4){
			return list;
		}
		if(season==1){
			start=1;
		}else if(season==2){
			start=4;
		}else if(season==3){
			start=7;
		}else if(season==4){
			start=10;
		}
		for(int i=start;i<start+3;i++){
			list.add(i);
		}
		return list;
	} 
}
