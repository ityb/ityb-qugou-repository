package com.ityb.qugou.service;

import java.util.List;

import com.ityb.qugou.dto.merchant.MemberDto;
import com.ityb.qugou.vo.merchant.MemberVo;

public interface MemberService {

	/**
	 * 查询会员用户信息
	 * @author yangbin
	 * @date 2018年4月12日
	 * @param memberDto
	 * @return
	 * @throws Exception 
	 */
	List<MemberVo> queryMember(MemberDto memberDto) throws Exception;

	/**
	 * 计算会员用户的查询数量
	 * @author yangbin
	 * @date 2018年4月12日
	 * @param memberDto
	 * @return
	 */
	int countMember(MemberDto memberDto);

	/**
	 * 邮件发送
	 * @author yangbin
	 * @date 2018年4月13日
	 * @param userId
	 * @param emailContent
	 * @param merchantId 
	 * @throws Exception 
	 */
	void emailSend(String email,String emailTitle,String emailContent, String merchantId) throws Exception;

}
