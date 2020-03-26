package com.ityb.qugou.order.constant;

/**
 * 常量池
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class Constants {

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
	
	/*
	 *订单状态-拒单
	 */
	public static final Integer ORDER_STATE_REFUSE = -2;
	public static final String ORDER_TRADE_WAY_HDFK = "货到付款";
	public static final String ORDER_TRADE_WAY_ZXZF = "在线支付";
	
	public static final Integer ORDER_TRADE_WAY_HDFK_FLAG = 1;
	
	public static final Integer ORDER_TRADE_WAY_ZXZF_FLAG = 2;
	public static final String NEW_ORDER_DESTINATION_NAME = "new.order.destinationName";

}
