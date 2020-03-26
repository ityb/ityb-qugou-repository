package com.ityb.qugou.base.converter;

import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.exception.code.impl.ParamExceptionEnum;
import com.ityb.qugou.base.utils.StringUtils;

/**
 * 日期转化器
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class StringToDateConverter implements Converter<String, Date> {
	private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	private static final String dateFormat = "yyyy-MM-dd";

	/**
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public Date convert(String source) {
		if ("null".equalsIgnoreCase(source)||StringUtils.isBlank(source)) {
			return null;
		}
		source = source.trim();
		try {
			return DateUtils.parseDate(source, dateTimeFormat);
		} catch (ParseException e) {
			try {
				return DateUtils.parseDate(source, dateFormat);
			} catch (ParseException e1) {
				throw new ServiceException(ParamExceptionEnum.DATE_EXCETION.getErrorCode(), "日期类型错误");
			}
		}
	}

}