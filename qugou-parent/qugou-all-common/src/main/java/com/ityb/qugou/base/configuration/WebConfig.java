package com.ityb.qugou.base.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ityb.qugou.base.converter.StringToDateConverter;

@Configuration
public class WebConfig {

	/**
	 * 注入日期转换器
	 * @author yangbin
	 * @date 2018年1月26日
	 * @return
	 */
	@Bean
	public StringToDateConverter stringToDateConverter(){
		return new StringToDateConverter();
	}
 }
