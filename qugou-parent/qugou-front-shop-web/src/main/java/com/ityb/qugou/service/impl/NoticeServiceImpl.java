package com.ityb.qugou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.annotation.RedisCache;
import com.ityb.qugou.base.enums.CacheType;
import com.ityb.qugou.constant.RedisConstants;
import com.ityb.qugou.mapper.NoticeMapper;
import com.ityb.qugou.po.manager.Notice;
import com.ityb.qugou.service.NoticeService;

/**
 * 公告管理service
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;
	/**
	 * 首先先从缓存中获取，缓存中获取不到就从数据库中获取
	 * 获取公告列表
	 * @author yangbin
	 * @date 2018年2月14日
	 * @param notice
	 * @return
	 */
	@Override
	@RedisCache(cacheType=CacheType.KeyValue,keyName=RedisConstants.NOTICE_KEY,expireSeconds=RedisConstants.EXPIRESECONDS)
	public List<Notice> queryNotice(Notice notice) {
		List<Notice> list = noticeMapper.queryNotice(notice);
		return list;
	}

}
