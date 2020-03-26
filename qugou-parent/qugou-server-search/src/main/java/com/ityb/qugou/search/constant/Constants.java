package com.ityb.qugou.search.constant;

/**
 * 常量池
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class Constants {

	/*
	 *商品添加jsm消息目的地
	 */
	public static final String ADD_PRODUCT_DESTINATION_NAME = "add.product.destinationName";
	/*
	 * 商品更新，jms消息目的地名称
	 */
	public static final String UPDATE_PRODUCT_DESTINATION_NAME = "update.product.destinationName";
	/*
	 * 商品删除，jms消息目的地名称
	 */
	public static final String DELETE_PRODUCT_DESTINATION_NAME = "delete.product.destinationName";
	//休眠豪秒数
	public static final long SLEEP_MILLIS = 1000;
	
	/*
	 * 默认初始页
	 */
	public static final int DEFAULT_PAGE_NOW=1;
	/*
	 * 默认一夜显示多少条数
	 */
	public static final int DEFAULT_PAGE_SIZE=50;

}
