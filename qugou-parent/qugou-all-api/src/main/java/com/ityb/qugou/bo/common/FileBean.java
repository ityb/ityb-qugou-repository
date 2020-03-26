package com.ityb.qugou.bo.common;

import java.io.Serializable;

public class FileBean implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年2月9日
	 */
	private static final long serialVersionUID = 296206514933151448L;
	private String originalFileName; //原始文件名称
	private String newFileName; //新的文件名称
	private String path; //文件存储地址（全路径）
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getNewFileName() {
		return newFileName;
	}
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
