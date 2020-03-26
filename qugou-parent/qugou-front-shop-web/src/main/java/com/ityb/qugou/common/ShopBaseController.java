package com.ityb.qugou.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.ityb.qugou.base.cache.CacheService;
import com.ityb.qugou.base.constant.RedisConstantKey;
import com.ityb.qugou.base.entity.bo.AddressBean;
import com.ityb.qugou.base.entity.bo.PointBean;
import com.ityb.qugou.base.utils.AddressUtils;
import com.ityb.qugou.base.utils.CookieUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.constant.CookieConstants;
import com.ityb.qugou.constant.RedisConstants;
import com.ityb.qugou.po.manager.SearchKeyword;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.service.SearchKeywordService;

/**
 * 通用的controller
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
public class ShopBaseController {
	@Autowired
	private CacheService cacheService;
	@Autowired
	private SearchKeywordService searchKeywordService;

	/**
	 * 获得登录用户信息
	 * @author yangbin
	 * @date 2018年3月7日
	 * @param request
	 * @return
	 */
	public User getUser(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, RedisConstantKey.SHOP_SESSION_ID);
		User user = (User) cacheService.get(RedisConstantKey.SHOP_SESSION_ID + "_" + cookieValue);
		return user;
	}
	/**
	 * 
	 * @author yangbin
	 * @date 2018年3月22日
	 * @param request
	 * @return
	 */
	public String getCurrentUserId(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, RedisConstantKey.SHOP_SESSION_ID);
		User user = (User) cacheService.get(RedisConstantKey.SHOP_SESSION_ID + "_" + cookieValue);
		return user==null?"":user.getId();
	}
	
	/**
	 * 得到地址相关的信息
	 * @author yangbin
	 * @date 2018年3月18日
	 * @param request
	 * @return
	 */
	public AddressBean getAddress(HttpServletRequest request){
		//第一次登录
		String cookieValue = CookieUtils.getCookieValue(request, CookieConstants.CUSTOMER_PRIMARY_ID_KEY);
		boolean getAddressflag=false;
		boolean cacheAddressFlag=false;
		if(StringUtils.isBlank(cookieValue)){
			getAddressflag=true;
		}else if(cacheService.isKeyExists(RedisConstants.CUSTOMER_ADDRESS_KEY+"_"+cookieValue)){
			return (AddressBean) cacheService.get(RedisConstants.CUSTOMER_ADDRESS_KEY+"_"+cookieValue);
		}else{
			getAddressflag=true;
			cacheAddressFlag=true;
		}
		AddressBean address=null;
		if(getAddressflag){
			//获取IP地址
			String requestIp=request.getRemoteAddr();
			//判断是否是本地地址
			if("127.0.0.1".equals(requestIp)){//本机地址访问
				requestIp=AddressUtils.getV4IP().trim();
			}
			address= AddressUtils.ipToAddress(requestIp);
			if(cacheAddressFlag&&address!=null){
				//设置半个小时过期
				cacheService.put(RedisConstants.CUSTOMER_ADDRESS_KEY+"_"+cookieValue, address,30*60);
			}
		}
		return address;
	}
	
	/**
	 * 得到经纬度坐标
	 * @author yangbin
	 * @date 2018年3月18日
	 * @param request
	 * @return
	 */
	public PointBean getPoint(HttpServletRequest request){
		String requestIp=request.getRemoteAddr();
		if("127.0.0.1".equals(requestIp)){//本机地址访问
			requestIp=AddressUtils.getV4IP().trim();
		}
		AddressBean ipToAddress = AddressUtils.ipToAddress(requestIp);
		return ipToAddress.getPointBean();
	}
	
	/**
	 * 得到对应所在的城市
	 * @author yangbin
	 * @date 2018年3月27日
	 * @param request
	 * @return
	 */
	public String getCity(HttpServletRequest request){
		String city = CookieUtils.getCookieValue(request, CookieConstants.CUSTOMER_CITY_KEY, true);
		return city;
	}
	
	public void setSearchHeader(HttpServletRequest request,HttpServletResponse response, Model model){
		// 获取搜索关键字
		SearchKeyword searchKeyword = new SearchKeyword();
		List<SearchKeyword> searchKeywordList = searchKeywordService.querySearchKeyword(searchKeyword);
		model.addAttribute("searchKeywordList", searchKeywordList);
		//获取是否登录的数据
		User user = getUser(request);
		model.addAttribute("user", user);
		//将数据存入model中
		AddressBean addressBean= getAddress(request);
		if(addressBean!=null){
			if(StringUtils.isBlank(CookieUtils.getCookieValue(request, CookieConstants.CUSTOMER_PRIMARY_ID_KEY))){
				String primaryKey=StringUtils.getRandomStr();
				CookieUtils.setCookie(response, CookieConstants.CUSTOMER_PRIMARY_ID_KEY,primaryKey);
				//设置半个小时过期
				cacheService.put(RedisConstants.CUSTOMER_ADDRESS_KEY+"_"+primaryKey, addressBean,30*60);
			}
			String city=StringUtils.isBlank(addressBean.getCity())?addressBean.getProvince():addressBean.getCity();
			model.addAttribute("city",city);
			//将信息存入cookie中
			CookieUtils.setCookie(response, CookieConstants.CUSTOMER_CITY_KEY, city,true);
			//获取经纬度
			CookieUtils.setCookie(response, CookieConstants.CUSTOMER_ADDRESS_KEY,AddressUtils.addressBeanToStr(addressBean, false, false, false) ,true);
			//设置经坐标
			CookieUtils.setCookie(response, CookieConstants.CUSTOMER_ADDRESS_POINT_X_KEY, addressBean.getPointBean().getX().toEngineeringString());
			CookieUtils.setCookie(response, CookieConstants.CUSTOMER_ADDRESS_POINT_Y_KEY, addressBean.getPointBean().getY().toEngineeringString());
		}
	}
}
