package com.ityb.qugou.common;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ityb.qugou.bo.common.FileBean;
import com.ityb.qugou.configuration.PropertiesConfig;

/**
 * 文件上传工具类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class FileUploadService {
	@Autowired
	private PropertiesConfig propertiesConfig;
	private final static Logger LOGGER = Logger.getLogger(FileUploadService.class);

	/**
	 * 多个文件上传
	 * 
	 * @author yangbin
	 * @date 2018年2月9日
	 * @return
	 */
	public FileBean upload(MultipartFile file) {
		String path = propertiesConfig.getFilePhysicsAddress();
		if (file == null) {
			return null;
		}
		FileBean fileBean = new FileBean();
		String name = file.getOriginalFilename();
		String suffixName = name.substring(name.lastIndexOf("."));
		String hash = Integer.toHexString(new Random().nextInt());
		String fileName = hash + suffixName;
		File tempFile = new File(path, fileName);
		fileBean.setOriginalFileName(name);
		fileBean.setNewFileName(fileName);
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdirs();
		}
		if (tempFile.exists()) {
			tempFile.delete();
		}
		try {
			tempFile.createNewFile();
			file.transferTo(tempFile);
			fileBean.setPath(propertiesConfig.getFileServerAddress() + fileName);
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return fileBean;
	}
}
