package com.ityb.qugou.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * websocket 相关配置
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		/*// 配置接受订阅消息地址前缀为topic的消息
		config.enableSimpleBroker("/topic");
		// Broker接收消息地址前缀
		config.setApplicationDestinationPrefixes("/app");*/
		
		// 订阅Broker名称
		config.enableSimpleBroker("/queue", "/topic");
        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
		// 添加服务端点，可以理解为某一服务的唯一key值，对应前端 var socket = new SockJS('/chatApp');
		//stompEndpointRegistry.addEndpoint("/merchantWebSocket").withSockJS();
		// 当浏览器支持sockjs时执行该配置
		stompEndpointRegistry.addEndpoint("/webSocketServer").setAllowedOrigins("*").withSockJS();
	}
}