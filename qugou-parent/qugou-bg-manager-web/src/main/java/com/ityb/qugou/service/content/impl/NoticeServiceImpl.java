package com.ityb.qugou.service.content.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.dao.content.NoticeDao;
import com.ityb.qugou.po.manager.Notice;
import com.ityb.qugou.service.content.NoticeService;

@Service
public class NoticeServiceImpl extends BaseServiceImpl<Notice> implements NoticeService{

	@Autowired
	private NoticeDao  noticeDao;
	@Override
	public List<Notice> getListData() {
		return null;
	}

	@Override
	protected BaseDao<Notice> getDao() {
		return noticeDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return Notice.class;
	}

}
