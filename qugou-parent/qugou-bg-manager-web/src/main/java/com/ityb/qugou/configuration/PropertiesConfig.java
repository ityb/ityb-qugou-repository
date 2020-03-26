package com.ityb.qugou.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 该类只要是用于读取配置文件中的内容
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@RefreshScope
@Component
@ConfigurationProperties
public class PropertiesConfig {

	// 过滤的路径前缀
	private static String filterPathMapping;

	private static Integer sessionExpireTime;
	// 文件的服务地址
	private static String filePhysicsAddress;
	// 上传文件的服务的链接
	private static String fileServerAddress;
	
	private static String httpPrefix;

	@Value("${http.prefix}")
	public void setHttpPrefix(String httpPrefix){
		PropertiesConfig.httpPrefix=httpPrefix;
	}
	
	@Value("${filter_path_mapping}")
	public void setFilterPathMapping(String filterPathMapping) {
		PropertiesConfig.filterPathMapping = filterPathMapping;
	}

	public  String getFilterPathMapping() {
		return PropertiesConfig.filterPathMapping;
	}

	@Value("${session.expire.time}")
	public void setSessionExpireTime(Integer sessionExpireTime) {
		PropertiesConfig.sessionExpireTime = sessionExpireTime;
	}

	public  Integer getSessionExpireTime() {
		return PropertiesConfig.sessionExpireTime;
	}

	public String getHttpPrefix() {
		return PropertiesConfig.httpPrefix;
	}
	@Value("${file.physics.address}")
	public void setFilePhysicsAddress(String filePhysicsAddress){
		PropertiesConfig.filePhysicsAddress=filePhysicsAddress;
	}
	
	public String getFilePhysicsAddress(){
		return PropertiesConfig.filePhysicsAddress;
	}
	@Value("${file.server.address}")
	public void setFileServerAddress(String fileServerAddress){
		PropertiesConfig.fileServerAddress=fileServerAddress;
	}
	public String getFileServerAddress(){
		return PropertiesConfig.fileServerAddress;
	}
}
