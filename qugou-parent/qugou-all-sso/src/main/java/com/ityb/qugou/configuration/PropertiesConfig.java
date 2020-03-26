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
@Component
@RefreshScope
@ConfigurationProperties
public class PropertiesConfig {

	private static String merchantMainUrl;

	private static String adminMainUrl;

	private static Integer sessionExpireTime;

	private static String shopIndexUrl;

	private static String filePhysicsAddress;

	private static String fileServerAddress;

	@Value("${file.physics.address}")
	public void setFilePhysicsAddress(String filePhysicsAddress) {
		PropertiesConfig.filePhysicsAddress = filePhysicsAddress;
	}

	@Value("${file.server.address}")
	public void setFileServerAddress(String fileServerAddress) {
		PropertiesConfig.fileServerAddress = fileServerAddress;
	}

	@Value("${merchant.main.url}")
	public void setMerchantMainUrl(String merchantMainUrl) {
		PropertiesConfig.merchantMainUrl = merchantMainUrl;
	}

	public String getMerchantMainUrl() {
		return PropertiesConfig.merchantMainUrl;
	}

	@Value("${admin.main.url}")
	public void setAdminMainUrl(String adminMainUrl) {
		PropertiesConfig.adminMainUrl = adminMainUrl;
	}

	@Value("${shop.index.url}")
	public void setShopIndexUrl(String shopIndexUrl) {
		PropertiesConfig.shopIndexUrl = shopIndexUrl;
	}

	public String getAdminMainUrl() {
		return PropertiesConfig.adminMainUrl;
	}

	@Value("${session.expire.time}")
	public void setSessionExpireTime(Integer sessionExpireTime) {
		PropertiesConfig.sessionExpireTime = sessionExpireTime;
	}

	public Integer getSessionExpireTime() {
		return PropertiesConfig.sessionExpireTime;
	}

	public String getShopIndexUrl() {
		return PropertiesConfig.shopIndexUrl;
	}

	public String getFilePhysicsAddress() {
		return PropertiesConfig.filePhysicsAddress;
	}

	public String getFileServerAddress() {
		return PropertiesConfig.fileServerAddress;
	}
}
