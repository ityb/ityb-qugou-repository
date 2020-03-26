package com.ityb.qugou.service.content.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.service.impl.BaseServiceImpl;
import com.ityb.qugou.dao.content.SlideshowDao;
import com.ityb.qugou.po.manager.Slideshow;
import com.ityb.qugou.service.content.SlideshowService;

@Service
public class SlideshowServiceImpl extends BaseServiceImpl<Slideshow> implements SlideshowService {

	@Autowired
	private SlideshowDao slideshowDao;

	@Override
	public List<Slideshow> getListData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BaseDao<Slideshow> getDao() {
		return slideshowDao;
	}

	@Override
	protected Class<?> getModelClass() {
		return Slideshow.class;
	}
}
