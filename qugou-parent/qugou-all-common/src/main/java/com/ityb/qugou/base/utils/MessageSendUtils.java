package com.ityb.qugou.base.utils;

import org.apache.log4j.Logger;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ityb.qugou.base.constant.MessageSendConstant;

/**
 * 利用阿里大鱼发送短信
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class MessageSendUtils {

	private final static Logger LOGGER = Logger.getLogger(MessageSendUtils.class);

	/**
	 * @author yangbin
	 * @date 2018年3月2日
	 * @param telephone 手机号码
	 * @param code 验证码
	 * @return
	 * @throws ClientException
	 */
	public static void sendVerificationCode(String telephone, String code) throws ClientException {
		//开启一个线程去执行
		new Thread(() -> {
			// 设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			// 初始化ascClient需要的几个参数
			final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
			final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
			// 替换成你的AK
			final String accessKeyId = MessageSendConstant.ACCESSKEY_ID;// 你的accessKeyId,参考本文档步骤2
			final String accessKeySecret = MessageSendConstant.ACCESSKEY_SECRET;// 你的accessKeySecret，参考本文档步骤2
			// 初始化ascClient,暂时不支持多region（请勿修改）
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			try {
				DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			} catch (Exception e1) {
				LOGGER.error(e1);
			}
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			request.setPhoneNumbers(telephone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(MessageSendConstant.SIGN_NAME);
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(MessageSendConstant.TEMPLATE_CODE);
			request.setTemplateParam("{\"code\":\"" + code + "\"}");
			SendSmsResponse sendSmsResponse = null;
			try {
				sendSmsResponse = acsClient.getAcsResponse(request);
			} catch (Exception e) {
				LOGGER.error(e);
			}
			LOGGER.error("手机短信发送"+sendSmsResponse.getMessage());
//			if ("OK".equals(sendSmsResponse.getCode())) {
//				// 请求失败
//				
//			}
		}).start();
	}
	public static void main(String[] args) throws ClientException {

		String telephone = "15776842681";
		String code = "1546121";
		MessageSendUtils.sendVerificationCode(telephone, code);
	}
}
