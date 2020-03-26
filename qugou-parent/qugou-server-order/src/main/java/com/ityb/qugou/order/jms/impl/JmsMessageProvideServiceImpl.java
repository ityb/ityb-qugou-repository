package com.ityb.qugou.order.jms.impl;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.order.jms.JmsMessageProvideService;

/**
 * mq消息提供者
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class JmsMessageProvideServiceImpl implements JmsMessageProvideService {

	// 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
	@Autowired 
	private JmsMessagingTemplate jmsTemplate;

	/**
	 * 发送消息
	 * 
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param destination
	 * @param message
	 */
	@Override
	public void sendMessage(Destination destination, final String message) {
		jmsTemplate.convertAndSend(destination, message);
	}

	/**
	 * 发送消息,(目的地名称，目的地消息),queue
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param destination
	 * @param message
	 */
	@Override
	public void sendMessageByQueue(String destinationName, String message) {
		Assert.hasText(destinationName, "目的地名称不能为空");
		Destination destination = new ActiveMQQueue(destinationName);
		sendMessage(destination, message);
	}

	/**
	 * 发送消息,(目的地名称，目的地消息),Topic
	 * 
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param destination
	 * @param message
	 */
	@Override
	public void sendMessageByTopic(String destinationName, String message) {
		Assert.hasText(destinationName, "目的地名称不能为空");
		Destination destination = new ActiveMQTopic(destinationName);
		sendMessage(destination, message);
	}
}
