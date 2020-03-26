package com.ityb.qugou.service;

import java.util.List;

import com.ityb.qugou.po.manager.Notice;

/**
 * 公告管理service
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface NoticeService {

	/**
	 * 获取公告列表
	 * @author yangbin
	 * @date 2018年2月14日
	 * @param notice
	 * @return
	 */
	List<Notice> queryNotice(Notice notice);

}
