package com.ityb.qugou.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ityb.qugou.base.entity.bo.AddressBean;
import com.ityb.qugou.base.entity.bo.LngLatBean;
import com.ityb.qugou.base.entity.bo.PointBean;

public class AddressUtils {

	private final static String AK = "dIVQhQMZPBMePQLqlEFQQE46VTWHOw9u";
	private final static Logger LOGGER = Logger.getLogger(AddressUtils.class);
	private final static String DEFAULT_IP_HAERBIN="1.58.80.140";

	/**
	 * 获取外网Ip
	 * 
	 * @author yangbin
	 * @date 2018年1月19日
	 * @return
	 */
	public static String getWebIP() {
		try {
			String webContent = HttpClientUtils.doPost("http://city.ip138.com/ip2city.asp");
			if (StringUtils.isNotBlank(webContent)) {
				int start = webContent.indexOf("[") + 1;
				int end = webContent.indexOf("]");
				// 获取网页中 当前 的 外网IP
				webContent = webContent.substring(start, end);
				return webContent;
			} else {
				LOGGER.debug("ip is null");
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("getWebIP...获取ip失败", e);
			return null;
		}
	}

	/**
	 * 得到外网V4I
	 * 
	 * @author yangbin
	 * @date 2018年3月18日
	 * @return
	 */
	public static String getV4IP() {
		String ip = "";
		String chinaz = "http://ip.chinaz.com";
		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		try {
			url = new URL(chinaz);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			while ((read = in.readLine()) != null) {
				inputLine.append(read + "\r\n");
			}
		} catch (Exception e) {
			LOGGER.error("getV4IP-1..获取外网IP地址失败..." + e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LOGGER.error("getV4IP-2..获取外网IP地址失败..." + e);
				}
			}
		}
		Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
		Matcher m = p.matcher(inputLine.toString());
		if (m.find()) {
			String ipstr = m.group(1);
			ip = ipstr;
		}
		if(StringUtils.isBlank(ip)){
			ip=DEFAULT_IP_HAERBIN;
		}
		return ip;
	}

	/**
	 * 获取内网Ip
	 * 
	 * @author yangbin
	 * @date 2018年1月19日
	 * @return
	 * @throws Exception
	 */
	public static String getLocalIP() throws Exception {
		String localIP = "";
		InetAddress addr = (InetAddress) InetAddress.getLocalHost();
		// 获取本机IP
		localIP = addr.getHostAddress().toString();
		return localIP;
	}

	/**
	 * 调用百度地图的api去获取省份
	 * 
	 * @author yangbin
	 * @date 2018年1月21日
	 * @param ip
	 * @return
	 */
	public static AddressBean ipToAddress(String ip) {
		if (StringUtils.isBlank(ip)) {
			return null;
		}
		String url = "http://api.map.baidu.com/location/ip";
		Map<String, String> param = new HashMap<>();
		param.put("ip", ip);
		param.put("ak", AK);
		String resultJson = HttpClientUtils.doPost(url, param);
		JSONObject dataJson = JSONObject.parseObject(resultJson);
		String status = dataJson.getString("status");
		AddressBean addressBean = new AddressBean();
		if (StringUtils.isBlank(status)) {
			LOGGER.error("ipToAddress... ip转地址失败");
			return addressBean;
		}
		if (Integer.parseInt(status) != 0) {
			LOGGER.error("ipToAddress... ip错误");
			return addressBean;
		}
		JSONObject content = dataJson.getJSONObject("content");
		if (content != null) {
			JSONObject addressDetail = content.getJSONObject("address_detail");
			addressBean.setProvince(addressDetail.getString("province"));
			addressBean.setCity(addressDetail.getString("city"));
			addressBean.setDistrict(addressDetail.getString("district"));
			addressBean.setStreet(addressDetail.getString("street"));
			addressBean.setStreetNumber(addressDetail.getString("street_number"));
			JSONObject point = content.getJSONObject("point");
			PointBean pointBean = new PointBean();
			pointBean.setY(StringUtils.isBlank(point.getString("y")) ? null : new BigDecimal(point.getString("y")));
			pointBean.setX(StringUtils.isBlank(point.getString("x")) ? null : new BigDecimal(point.getString("x")));
			addressBean.setPointBean(pointBean);
		}
		return addressBean;
	}

	/**
	 * 地址转对应的经纬度
	 * @author yangbin
	 * @date 2018年1月21日
	 * @param address
	 * @return
	 */
	public static PointBean addressToPoint(String address) {
		PointBean pointBean = new PointBean();
		LngLatBean lngLatBean = new LngLatBean();
		String url = "http://api.map.baidu.com/geocoder/v2/";
		Map<String, String> param = new HashMap<>();
		param.put("address", address);
		param.put("output", "json");
		param.put("ak", AK);
		String resultJson = HttpClientUtils.doPost(url, param);
		JSONObject dataJson = JSONObject.parseObject(resultJson);
		String status = dataJson.getString("status");
		if (StringUtils.isBlank(status)) {
			LOGGER.error("addressToPoint... 地址转经纬度失败");
			return pointBean;
		}
		if (Integer.parseInt(status) != 0) {
			LOGGER.error("addressToPoint... address错误");
			return pointBean;
		}
		JSONObject result = dataJson.getJSONObject("result");
		if (result != null) {
			JSONObject location = result.getJSONObject("location");
			if (location != null) {
				lngLatBean.setLng(StringUtils.isBlank(location.getString("lng")) ? null
						: new BigDecimal(location.getString("lng")));
				lngLatBean.setLat(StringUtils.isBlank(location.getString("lat")) ? null
						: new BigDecimal(location.getString("lat")));
				pointBean = lngLatToPoint(lngLatBean);
			}
		}
		return pointBean;
	}

	/***
	 * 经纬度转平面坐标
	 * @author yangbin
	 * @date 2018年1月21日
	 * @param lngLatBean
	 * @return
	 */
	public static PointBean lngLatToPoint(LngLatBean lngLatBean) {
		PointBean pointBean = new PointBean();
		if (lngLatBean == null || lngLatBean.getLat() == null || lngLatBean.getLng() == null) {
			LOGGER.error("lngLatToPoint...经纬度转参数错误");
			return pointBean;
		}
		String url = "http://api.map.baidu.com/geoconv/v1/?from=1&to=6";
		String coords = lngLatBean.getLng() + "," + lngLatBean.getLat();
		Map<String, String> param = new HashMap<>();
		param.put("coords", coords);
		param.put("ak", AK);
		String resultJson = HttpClientUtils.doGet(url, param);
		System.out.println("lngLatToPoint"+resultJson);
		JSONObject dataJson = JSONObject.parseObject(resultJson);
		String status = dataJson.getString("status");
		if (StringUtils.isBlank(status)) {
			LOGGER.error("lngLatToPoint...经纬度转平面坐标失败");
			return pointBean;
		}
		if (Integer.parseInt(status) != 0) {
			LOGGER.error("经纬度失败... ip错误");
			return pointBean;
		}
		JSONArray pointArray = dataJson.getJSONArray("result");
		if (pointArray != null) {
			BigDecimal y = (BigDecimal)pointArray.getJSONObject(0).get("y");
			BigDecimal x = (BigDecimal)pointArray.getJSONObject(0).get("x");
			pointBean.setY(y);
			pointBean.setX(x);
		}
		return pointBean;
	}

	/**
	 * 地址bean转为字符串
	 * 
	 * @author yangbin
	 * @date 2018年3月17日
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static String getAddressByIp(String ip, boolean isDistrict, boolean isStreet, boolean isStreetNumber) {
		AddressBean address = ipToAddress(ip);
		if (address == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		result.append(StringUtils.isBlank(address.getProvince()) ? "" : address.getProvince());
		result.append(StringUtils.isBlank(address.getCity()) ? "" : address.getCity());
		if (isDistrict) {
			result.append(StringUtils.isBlank(address.getDistrict()) ? "" : address.getDistrict());
		}
		if (isDistrict) {
			result.append(StringUtils.isBlank(address.getStreet()) ? "" : address.getStreet());
		}
		if (isStreetNumber) {
			result.append(StringUtils.isBlank(address.getStreetNumber()) ? "" : address.getStreetNumber());
		}
		return result.toString();
	}

	/**
	 * 地址bean转地址字符串
	 * 
	 * @author yangbin
	 * @date 2018年3月18日
	 * @param address
	 * @param isDistrict
	 * @param isStreet
	 * @param isStreetNumber
	 * @return
	 */
	public static String addressBeanToStr(AddressBean address, boolean isDistrict, boolean isStreet,
			boolean isStreetNumber) {
		StringBuffer result = new StringBuffer();
		result.append(StringUtils.isBlank(address.getProvince()) ? "" : address.getProvince());
		result.append(StringUtils.isBlank(address.getCity()) ? "" : address.getCity());
		if (isDistrict) {
			result.append(StringUtils.isBlank(address.getDistrict()) ? "" : address.getDistrict());
		}
		if (isDistrict) {
			result.append(StringUtils.isBlank(address.getStreet()) ? "" : address.getStreet());
		}
		if (isStreetNumber) {
			result.append(StringUtils.isBlank(address.getStreetNumber()) ? "" : address.getStreetNumber());
		}
		return result.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(getV4IP());
		String ip = "183.134.160.221";
		System.out.println(ipToAddress(ip));
		String address = "浙江省丽水市莲都区水阁街道";
		System.out.println(addressToPoint(address));
	}
}
