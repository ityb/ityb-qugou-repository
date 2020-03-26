package com.ityb.qugou.configuration;
import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ityb.qugou.interceptor.LoginInterceptor;


@Configuration
public class SpringMVCConfig extends WebMvcConfigurerAdapter {
    
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 判断用户是否登录的拦截器
    	/*registry.addInterceptor(loginInterceptor).excludePathPatterns("/merchant/layer/**");
    	registry.addInterceptor(loginInterceptor).excludePathPatterns("/merchant/layui/**");
    	registry.addInterceptor(loginInterceptor).excludePathPatterns("/merchant/zTree/**");
    	registry.addInterceptor(loginInterceptor).excludePathPatterns("/merchant/index");*/
        registry.addInterceptor(loginInterceptor).addPathPatterns("/merchant/**");
    }
	
	//显示声明CommonsMultipartResolver为mutipartResolver
	 @Bean
	    public MultipartConfigElement multipartConfigElement() {
	        MultipartConfigFactory factory = new MultipartConfigFactory();
	        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
	        // 这样在文件上传的地方就需要进行异常信息的处理了;
	        factory.setMaxFileSize("10MB"); // KB,MB
	        /// 设置总上传数据总大小
	        factory.setMaxRequestSize("20MB");
	        // Sets the directory location where files will be stored.
	        // factory.setLocation("路径地址");
	        return factory.createMultipartConfig();
	    }
}
