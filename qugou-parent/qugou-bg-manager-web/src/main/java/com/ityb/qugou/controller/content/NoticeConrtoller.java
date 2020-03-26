package com.ityb.qugou.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ityb.qugou.base.controller.BaseController;
import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.po.manager.Notice;
import com.ityb.qugou.service.content.NoticeService;

@RequestMapping("/manager/notice")
@Controller
public class NoticeConrtoller extends BaseController<Notice>{

	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 列表显示页面
	 */
	@Override
	protected String getListPage() {
		return "notice-list";
	}

	@Override
	protected BaseService<Notice> getService() {
		return noticeService;
	}
	/**
	 * 跳转到添加页面
	 */
	@Override
	public String add() throws Exception {
		return "notice-add";
	}
	/**
	 * 跳转到更新页面
	 */
	@Override
	public String update(Model model) {
		return "notice-update";
	}

}
