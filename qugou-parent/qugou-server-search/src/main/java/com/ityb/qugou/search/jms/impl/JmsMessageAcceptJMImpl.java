package com.ityb.qugou.search.jms.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.ityb.qugou.search.constant.Constants;
import com.ityb.qugou.search.facade.SearchServiceFacade;
import com.ityb.qugou.search.jms.JmsMessageAcceptJM;

/**
 * jsm消息接受者
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Component
public class JmsMessageAcceptJMImpl implements JmsMessageAcceptJM{
	
	@Autowired
	private SearchServiceFacade searchServiceFacade;
	private final static Logger LOGGER = Logger.getLogger(JmsMessageAcceptJMImpl.class);
	/**
	 * 商品添加之后发送的通知
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 */
	@Override
	@JmsListener(destination = Constants.ADD_PRODUCT_DESTINATION_NAME)  
	public void acceptProductToAdd(String text) {
		try {
			Thread.sleep(Constants.SLEEP_MILLIS);
			LOGGER.info( Constants.ADD_PRODUCT_DESTINATION_NAME+"收到的信息为"+text);
			this.searchServiceFacade.addProductToSearchByProductId(text);
		} catch (InterruptedException e) {
			LOGGER.error("acceptProductToAdd....添加到solr服务器失败");
		}
	}
	/**
	 * 商品更新之后发送的通知
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param productId
	 * @throws InterruptedException 
	 */
	@Override
	@JmsListener(destination = Constants.UPDATE_PRODUCT_DESTINATION_NAME)
	public void acceptProductToUpdate(String text) {
		try {
			Thread.sleep(Constants.SLEEP_MILLIS);
			LOGGER.info( Constants.UPDATE_PRODUCT_DESTINATION_NAME+"收到的信息为"+text);
			this.searchServiceFacade.updateProductToSearchByProductId(text);
		} catch (InterruptedException e) {
			LOGGER.error("acceptProductToUpdate....修改商品信息到solr服务器失败");
		}
	}
	/**
	 * 商品删除之后发送的通知
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 * @throws InterruptedException 
	 */
	@Override
	@JmsListener(destination = Constants.DELETE_PRODUCT_DESTINATION_NAME)
	public void acceptProductToDelete(String text) {
		try {
			Thread.sleep(Constants.SLEEP_MILLIS);
			LOGGER.info( Constants.DELETE_PRODUCT_DESTINATION_NAME+"收到的信息为"+text);
			this.searchServiceFacade.deleteProductToSearchByProductId(text);
		} catch (InterruptedException e) {
			LOGGER.error("acceptProductToDelte....从solr服务器中删除商品信息失败");
		}
	}  
}
