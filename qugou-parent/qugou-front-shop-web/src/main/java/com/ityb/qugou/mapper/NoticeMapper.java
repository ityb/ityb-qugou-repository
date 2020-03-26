package com.ityb.qugou.mapper;

import java.util.List;

import com.ityb.qugou.po.manager.Notice;

public interface NoticeMapper {

	/**
	 * 获取公告列表
	 * @author yangbin
	 * @date 2018年2月14日
	 * @param notice
	 * @return
	 */
	List<Notice> queryNotice(Notice notice);

}
