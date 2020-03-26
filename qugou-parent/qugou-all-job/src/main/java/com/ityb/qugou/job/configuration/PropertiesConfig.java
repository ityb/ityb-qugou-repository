package com.ityb.qugou.job.configuration;

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

}
