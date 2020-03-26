package com.ityb.qugou.controller.system;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.controller.BaseController;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.base.utils.HttpClientUtils;
import com.ityb.qugou.base.utils.JsonUtils;
import com.ityb.qugou.configuration.PropertiesConfig;
import com.ityb.qugou.po.job.ScheduledJob;
import com.ityb.qugou.service.system.ScheduledJobService;

@RequestMapping("/manager/scheduledJob")
@Controller
public class ScheduledJobController extends BaseController<ScheduledJob>{

	@Autowired
	private PropertiesConfig propertiesConfig;
	@Autowired
	private ScheduledJobService scheduledJobService;
	
	/**
	 * 以http方式调用对应的服务
	 * @author yangbin
	 * @date 2018年3月24日
	 * @param scheduledJob
	 * @return
	 */
	@RequestMapping(value="/execute",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<String> execute(ScheduledJob scheduledJob){
		try {
			String responsStr = HttpClientUtils.doPost(propertiesConfig.getHttpPrefix()+scheduledJob.getUrl());
			@SuppressWarnings("unchecked")
			JsonResultData<String> jsonResultData=JsonUtils.jsonToPojo(responsStr, JsonResultData.class);
			return jsonResultData;
		} catch (Exception e) {
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 列表显示页面
	 */
	@Override
	protected String getListPage() {
		return "scheduledJob-list";
	}

	@Override
	protected BaseService<ScheduledJob> getService() {
		return scheduledJobService;
	}

	/**
	 * 跳转到添加页面
	 */
	@Override
	public String add() throws Exception {
		return "scheduledJob-add";
	}
	/**
	 * 跳转到更新页面
	 */
	@Override
	public String update(Model model) {
		return "scheduledJob-update";
	}


}
