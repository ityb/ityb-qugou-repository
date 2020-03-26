package com.ityb.qugou.base.mail;

import java.io.File;
import java.util.List;
import javax.mail.MessagingException;

public interface MailService {
	/**
	 * 发送简单邮件
	 * 开启另外一个线程
	 * @author yangbin
	 * @date 2018年1月29日
	 * @param sendTo 接受人
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 */
	public void sendSimpleMail(String sendTo, String subject, String content);

	/**
	 * 发送带附件的文件
	 * @author yangbin
	 * @date 2018年1月29日
	 * @param sendTo 接收者.
	 * @param subject  邮件主题.
	 * @param content 邮件内容.
	 * @param attrList 文件列表
	 * @throws MessagingException
	 */
	public void sendAttachmentsMail(String sendTo, String subject, String content, List<File> attrList) throws MessagingException;
		

	/**
	 * 发送静态资源邮件
	 * @author yangbin
	 * @date 2018年1月29日
	 * @param sendTo 接收者.
	 * @param subject  邮件主题.
	 * @param content  邮件内容.
	 * @param attrList 文件列表
	 * @throws MessagingException
	 */
	public void sendInlineMail(String sendTo, String subject, String content, List<File> attrList) throws MessagingException;
}
