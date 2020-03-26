package com.ityb.qugou.contoller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.common.MerchantBaseController;
import com.ityb.qugou.dto.merchant.MemberDto;
import com.ityb.qugou.service.MemberService;
import com.ityb.qugou.vo.merchant.MemberVo;
import com.ityb.qugou.vo.merchant.ResultVo;

/**
 * 会员控制器
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping(value = "/merchant/member")
public class MemberController extends MerchantBaseController {
	
	private final static Logger LOGGER = Logger.getLogger(MemberController.class);
	@Autowired
	private MemberService memberService;
	
	/**
	 * 跳转到会员用户列表
	 * 
	 * @author yangbin
	 * @date 2018年4月12日
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String member() {
		return "member-list";
	}
	
	/**
	 * 跳转到编辑邮件页面
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/editEmail",method={RequestMethod.GET})
	public String editEmail(String email,Model model){
		model.addAttribute("email",email);
		return "edit-email";
	}
	
	/**
	 * 发送邮件给会员
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param userId
	 * @param emailContent
	 * @return
	 */
	@RequestMapping(value="/emailSend",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> emailSend(HttpServletRequest request,String email, String emailTitle,String emailContent){
		try {
			this.memberService.emailSend(email,emailTitle,emailContent,getCurrentUserId(request));
		} catch (Exception e) {
			LOGGER.error("emailSend...发送邮件给客户失败...",e);
			return JsonResultData.error(e.getMessage());
		}
		return JsonResultData.success(true);
	}
	
	/**
	 * 获取会员数据
	 * @author yangbin
	 * @date 2018年4月12日
	 * @param request
	 * @param memberDto
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	@ResponseBody
	public ResultVo<MemberVo> queryMember(HttpServletRequest request, MemberDto memberDto) {
		memberDto.setMerchantId(getCurrentUserId(request));
		try {
			List<MemberVo> list=memberService.queryMember(memberDto);
			int count=memberService.countMember(memberDto);
			return ResultVo.success(list, count);
		} catch (Exception e) {
			return ResultVo.error(e.getMessage());
		}
	}
}
