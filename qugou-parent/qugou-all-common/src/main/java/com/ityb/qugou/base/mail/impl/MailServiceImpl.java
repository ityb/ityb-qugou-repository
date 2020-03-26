package com.ityb.qugou.base.mail.impl;

import java.io.File;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.exception.code.impl.ServiceExceptionEnum;
import com.ityb.qugou.base.mail.MailService;
import com.ityb.qugou.base.utils.CollectionUtils;

@Service
public class MailServiceImpl implements MailService{
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String sendEmailFrom;
	
	private final static Logger LOGGER = Logger.getLogger(MailServiceImpl.class);
	/**
	 * 发送简单邮件
	 * 开启另外一个线程
	 * @author yangbin
	 * @date 2018年1月29日
	 * @param sendTo 接受人
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 */
	@Override
	public void sendSimpleMail(String sendTo, String subject, String content) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					SimpleMailMessage message = new SimpleMailMessage();
					message.setFrom(sendEmailFrom);// 发送者.
					message.setTo(sendTo);// 接收者.
					message.setSubject(subject);// 邮件主题.
					message.setText(content);// 邮件内容.
					mailSender.send(message);// 发送邮件
					LOGGER.info("sendSimpleMail....邮件发送成功.....");
				} catch (Exception e) {
					LOGGER.error("sendSimpleMail...邮件发送失败",e);
					throw new ServiceException(ServiceExceptionEnum.SEND_EXCETION.getErrorCode(),"发送邮件失败");
				}
			}
		}).start();
	}

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
	@Override
	public void sendAttachmentsMail(String sendTo, String subject, String content, List<File> attrList)
			throws MessagingException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
					// 基本设置.
					helper.setFrom(sendEmailFrom);// 发送者.
					helper.setTo(sendTo);// 接收者.
					helper.setSubject(subject);// 邮件主题.
					helper.setText(content);// 邮件内容.
					if (!CollectionUtils.isEmpty(attrList)) {
						attrList.forEach(file -> {
							FileSystemResource fileSystemResource = new FileSystemResource(file);
							try {
								helper.addAttachment(file.getName(), fileSystemResource);
							} catch (Exception e) {
								LOGGER.error("sendAttachmentsMail...邮件发送失败",e);
							}
						});
					}
					mailSender.send(mimeMessage);
					LOGGER.info("sendAttachmentsMail....邮件发送成功.....");
				} catch (Exception e) {
					LOGGER.error("sendAttachmentsMail...邮件发送失败",e);
					throw new ServiceException(ServiceExceptionEnum.SEND_EXCETION.getErrorCode(),"发送邮件失败");
				}
			}
		}).start();
	}

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
	@Override
	public void sendInlineMail(String sendTo, String subject, String content, List<File> attrList)
			throws MessagingException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
					// 基本设置.
					helper.setFrom(sendEmailFrom);// 发送者.
					helper.setTo(sendTo);// 接收者.
					helper.setSubject(subject);// 邮件主题.
					helper.setText(content, true);// 邮件内容.
					if (!CollectionUtils.isEmpty(attrList)) {
						attrList.forEach(file -> {
							FileSystemResource fileSystemResource = new FileSystemResource(file);
							try {
								helper.addAttachment(file.getName(), fileSystemResource);
							} catch (Exception e) {
								LOGGER.error("sendInlineMail...邮件发送失败",e);
							}
						});
					}
					mailSender.send(mimeMessage);
					LOGGER.info("sendInlineMail....邮件发送成功.....");
				} catch (Exception e) {
					LOGGER.error("sendInlineMail...邮件发送失败",e);
					throw new ServiceException(ServiceExceptionEnum.SEND_EXCETION.getErrorCode(),"发送邮件失败");
				}
			}
		}).start();
	}
}
