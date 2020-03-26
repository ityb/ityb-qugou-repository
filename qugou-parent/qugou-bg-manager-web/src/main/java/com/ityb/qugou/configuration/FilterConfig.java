package com.ityb.qugou.configuration;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ityb.qugou.filter.RequestFilter;

/**
 * 过滤器配置类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Configuration
public class FilterConfig{
	@Autowired
	private PropertiesConfig propertiesConfig;
	@Bean
	public FilterRegistrationBean contextFilterRegistrationBean(){
		FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
		filterRegistrationBean.setFilter(requestFilter());
		/**
		 * 这里设置需要过滤的路径
		 */
		String[] filterPathMappings=propertiesConfig.getFilterPathMapping().split("\\s*,\\s*");
		for (String filterPathMapping : filterPathMappings) {
			filterRegistrationBean.addUrlPatterns(filterPathMapping+"/saveDtoFile");
			filterRegistrationBean.addUrlPatterns(filterPathMapping+"/save");
			filterRegistrationBean.addUrlPatterns(filterPathMapping+"/save-update");
			filterRegistrationBean.addUrlPatterns(filterPathMapping+"/save-update-file");
			filterRegistrationBean.addUrlPatterns(filterPathMapping+"/delete");
			filterRegistrationBean.addUrlPatterns(filterPathMapping+"/deleteCycle");
		}
		/*filterRegistrationBean.addUrlPatterns("/manager/notice/saveDtoFile");
		filterRegistrationBean.addUrlPatterns("/manager/notice/save");
		filterRegistrationBean.addUrlPatterns("/manager/notice/save-update");
		filterRegistrationBean.addUrlPatterns("/manager/notice/save-update-file");
		filterRegistrationBean.addUrlPatterns("/manager/notice/delete");
		filterRegistrationBean.addUrlPatterns("/manager/notice/deleteCycle");*/
		filterRegistrationBean.setName("requestFilter");
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}
	@Bean 
	public Filter requestFilter(){
		return new RequestFilter();
	}
}
