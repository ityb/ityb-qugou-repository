package com.ityb.qugou.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadUtils {

	public static void setDownLoadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
		// 构建一个PDF文档输出流程

		String headStr = "attachment;filename=\"" + DownloadUtils.fileName2Chinese(request, fileName) + "\"";
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", headStr);
	}

	public static String fileName2Chinese(HttpServletRequest request,String fileName){
		if(StringUtils.isEmpty(fileName)){
			return fileName;
		}
		String agent=request.getHeader("User-Agent");
		try {
			if(!StringUtils.isEmpty(agent)) {
				if(agent.toLowerCase().indexOf("firefox")>0) {
					fileName="?UTF-8?B?"+new String(org.apache.commons.codec.binary.Base64.encodeBase64(fileName.getBytes("UTF-8")))+"?=";
				}else if(agent.toUpperCase().indexOf("MSIE")>0) {
					
						fileName=URLEncoder.encode(fileName,"UTF-8");
					fileName=fileName.replace("%29", "");
					fileName=fileName.replace(" ", "%20");
				}else if(-1!=agent.indexOf("Chrome")) {
					fileName=new String(fileName.getBytes(),"ISO8859-1"); 
				}else {
					fileName=URLEncoder.encode(fileName,"UTF-8");
				}
			}else {
				fileName=URLEncoder.encode(fileName,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fileName;
	}
}
