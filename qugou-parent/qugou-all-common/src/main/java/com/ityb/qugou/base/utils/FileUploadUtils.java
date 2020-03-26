package com.ityb.qugou.base.utils;

import java.io.File;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传工具类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class FileUploadUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtils.class);

	/**
	 * 文件上传工具类
	 * 
	 * @author yangbin
	 * @date 2018年3月15日
	 * @param file 接受的流
	 * @param filePhysicsAddress 文件存储的物理地址
	 * @param fileServerAddress 文件存储的服务器地址
	 * @return
	 */
	public static String upload(MultipartFile file, String filePhysicsAddress, String fileServerAddress) {
		// 原始文件名称
		String oFileName = file.getOriginalFilename();
		// 新文件名称
		String newFileName = UUID.randomUUID().toString() + oFileName.substring(oFileName.lastIndexOf("."));
		// 上传图片
		File uploadFile = new java.io.File(filePhysicsAddress + newFileName);
		try {
			file.transferTo(uploadFile);
		} catch (Exception e) {
			LOGGER.error("文件上传错误", e);
		}
		// 把数据回显到页面需要路径
		String path = fileServerAddress + newFileName;
		return path;
	}
}
