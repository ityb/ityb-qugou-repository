package com.ityb.qugou.base.constant;

public class BackgroundConstants {
	public static final String BACKGROUND_USER = "background_user";
	// 表示后台管理员类型
	public static final Integer USER_TYPE_BACKGOUND = 1;
	// 表示商家类型
	public static final Integer USER_TYPE_MERCHANT = 2;
	// 表示顾客类型
	public static final Integer USER_TYPE_CUSTOMER = 3;
	//表示账号被限制
	public static final Integer STATE_LIMIT = 0;
	//表示未审核
	public static final Integer STATE_UNREVIEWED = -1;
	//表示审核未通过
	public static final Integer STATE_UNREVIEWED_UNPASS = 2;
}
