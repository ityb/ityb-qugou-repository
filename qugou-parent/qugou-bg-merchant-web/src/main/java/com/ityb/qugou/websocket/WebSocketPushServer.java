package com.ityb.qugou.websocket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Component;

import com.ityb.qugou.constant.MerchantConstants;
/**
 * websocket消息推送服务
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Component
public class WebSocketPushServer {
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	private final static Logger LOGGER = Logger.getLogger(WebSocketPushServer.class);
	
	/**
	 * 新订单推送消息
	 * @author yangbin
	 * @date 2018年5月14日
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value="/websocket/newOrderCallback",method={RequestMethod.GET,RequestMethod.POST})
	@JmsListener(destination =MerchantConstants.NEW_ORDER_DESTINATION_NAME)  
	@SendTo("/topic/newOrderCallback")
	@SubscribeMapping("/newOrderCallback")
	public Object callback(String text) throws Exception {
		LOGGER.info("newOrderCallback...获取到的数据为："+text);
		try {
			// 调用自身
			messagingTemplate.convertAndSend("/topic/newOrderCallback", text);
			LOGGER.info("newOrderCallback....新订单消息推送成功");
		} catch (Exception e) {
			LOGGER.error("newOrderCallback....新订单推送失败",e);
		}
		return "newOrderCallback";
	}
}
