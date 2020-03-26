package com.ityb.qugou.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 该类只要是用于读取配置文件中的内容
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Component
@RefreshScope
@ConfigurationProperties
@Order(1)
public class PropertiesConfig {

	private static Integer sessionExpireTime;
	// 文件的服务地址
	private static String filePhysicsAddress;
	// 上传文件的服务的链接
	private static String fileServerAddress;

	@Value("${session.expire.time}")
	public void setSessionExpireTime(Integer sessionExpireTime) {
		PropertiesConfig.sessionExpireTime = sessionExpireTime;
	}

	public Integer getSessionExpireTime() {
		return PropertiesConfig.sessionExpireTime;
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
