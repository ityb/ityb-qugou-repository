package com.ityb.qugou.search.jms;

/**
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface JmsMessageAcceptJM {
	
	/**
	 * 商品添加之后发送的通知
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 * @throws InterruptedException 
	 */
	public void acceptProductToAdd(String productId) ;
	/**
	 * 商品更新之后发送的通知
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 * @throws InterruptedException 
	 */
	public void acceptProductToUpdate(String productId);
	/**
	 * 商品删除之后发送的通知
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 * @throws InterruptedException 
	 */
	public void acceptProductToDelete(String productId);
}
