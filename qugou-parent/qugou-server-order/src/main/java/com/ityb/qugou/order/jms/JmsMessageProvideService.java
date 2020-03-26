package com.ityb.qugou.order.jms;

import javax.jms.Destination;

public interface JmsMessageProvideService {
	/**
	 * 发送消息
	 * 
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param destination
	 * @param message
	 */
	public void sendMessage(Destination destination, final String message);
	
	/**
	 * 发送消息,(目的地名称，目的地消息),Queue
	 * 
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param destination
	 * @param message
	 */
	public void sendMessageByQueue(String destinationName, final String message);
	/**
	 * 发送消息,(目的地名称，目的地消息),Topic
	 * 
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param destination
	 * @param message
	 */
	public void sendMessageByTopic(String destinationName, final String message);
}
