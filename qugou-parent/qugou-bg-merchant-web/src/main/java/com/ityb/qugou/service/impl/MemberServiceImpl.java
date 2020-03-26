package com.ityb.qugou.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.mail.MailService;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.constant.MerchantConstants;
import com.ityb.qugou.dao.MemberDao;
import com.ityb.qugou.dto.merchant.MemberDto;
import com.ityb.qugou.service.MemberService;
import com.ityb.qugou.vo.merchant.MemberVo;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MailService mailService;
	/**
	 * 查询会员用户信息
	 * @author yangbin
	 * @date 2018年4月12日
	 * @param memberDto
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<MemberVo> queryMember(MemberDto memberDto) throws Exception {
		Assert.hasText(memberDto.getMerchantId(),"商家ID不能为空");
		if (memberDto.getPageIndex() == null || memberDto.getPageIndex() <= 0) {
			memberDto.setPageIndex(MerchantConstants.DEFAULT_PAGE_NOW);
		}
		if (memberDto.getPageSize() == null || memberDto.getPageSize() <= 0) {
			memberDto.setPageSize(MerchantConstants.DEFAULT_PAGE_SIZE);
		}
		memberDto.setPageStart((memberDto.getPageIndex() - 1) * memberDto.getPageSize());
		return memberDao.queryMember(memberDto);
	}

	/**
	 * 计算会员用户的查询数量
	 * @author yangbin
	 * @date 2018年4月12日
	 * @param memberDto
	 * @return
	 */
	@Override
	public int countMember(MemberDto memberDto) {
		return this.memberDao.countMember(memberDto);
	}
	
	/**
	 * 邮件发送
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param userId
	 * @param emailContent
	 * @throws Exception 
	 */
	@Override
	public void emailSend(String email,String emailTitle, String emailContent,String merchantId) throws Exception {
		Assert.hasText(emailContent,"邮件内容不能为空");
		if(StringUtils.isBlank(email)){
			MemberDto memberDto = new MemberDto();
			memberDto.setMerchantId(merchantId);
			List<MemberVo> list = this.memberDao.queryMember(memberDto);
			StringBuffer emailBuffer=new StringBuffer();
			for (MemberVo memberVo : list) {
				if(StringUtils.isNotBlank(memberVo.getEmail())){
					mailService.sendSimpleMail(memberVo.getEmail(), emailTitle, emailContent);
				}
			}
			if(StringUtils.isNotBlank(emailBuffer.toString())){
				email=emailBuffer.delete(emailBuffer.length()-1, emailBuffer.length()).toString();
			}else{//说明没有会员绑定邮箱
				return;
			}
		}
		List<String> emailList=Arrays.asList(email.split("\\s*,\\s*"));
		for (String emailStr : emailList) {
			mailService.sendSimpleMail(emailStr, emailTitle, emailContent);
		}
	}

}
