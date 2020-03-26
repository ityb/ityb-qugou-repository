package com.ityb.qugou.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.DateUtils;
import com.ityb.qugou.bo.manager.StatisticsDayBean;
import com.ityb.qugou.bo.manager.StatisticsMonthBean;
import com.ityb.qugou.bo.manager.StatisticsSeasonBean;
import com.ityb.qugou.bo.manager.StatisticsYearBean;
import com.ityb.qugou.dto.manager.StatisticsDto;
import com.ityb.qugou.facade.StatisticsServiceFacade;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.remoting.OrderServiceRemoting;
import com.ityb.qugou.service.ShopService;

/**
 * 统计分析类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class StatisticsServiceFacadeImpl implements StatisticsServiceFacade {

	@Autowired
	private OrderServiceRemoting orderServiceRemoting;
	@Autowired
	private ShopService shopService;

	/**
	 * 统计年销售
	 * @author yangbin
	 * @date 2018年5月11日
	 * @param statisticsDto
	 * @return
	 */
	@Override
	public List<StatisticsYearBean> queryYearSale(StatisticsDto statisticsDto) {
		List<StatisticsYearBean> yearSaleList = orderServiceRemoting.queryYearSale(statisticsDto).getData();
		Map<Integer,StatisticsYearBean> yearSaleMap=new TreeMap<>();
		if(CollectionUtils.isNotEmpty(yearSaleList)){
			for (StatisticsYearBean statisticsYearBean : yearSaleList) {
				if(yearSaleMap.get(statisticsYearBean.getMonth())==null){
					yearSaleMap.put(statisticsYearBean.getMonth(),statisticsYearBean);
				}
			}
		}
		List<StatisticsYearBean> result=new ArrayList<>(12);
		StatisticsYearBean statisticsYearBean=null;
		for (int i = 1; i <=12; i++) {
			if(yearSaleMap.get(i)==null){
				statisticsYearBean=new StatisticsYearBean();
				statisticsYearBean.setMonth(i);
				statisticsYearBean.setTotalMoney(0D);
				statisticsYearBean.setTotalNum(0D);
				result.add(statisticsYearBean);
			}else{
				result.add(yearSaleMap.get(i));
			}
		}
		return result;
	}

	/**
	 * 获取待统计的年年份
	 * @author yangbin
	 * @date 2018年5月11日
	 * @param merchantId
	 * @return
	 */
	@Override
	public List<Integer> queryStatisticsYear(String merchantId) {
		Shop shop=new Shop();
		shop.setUserId(merchantId);
		Shop shopInfo = shopService.getShopInfo(shop);
		List<Integer> list=new ArrayList<>();
		if(shopInfo!=null&&shopInfo.getCtime()!=null){
			Integer year = Integer.parseInt(DateUtils.getYear(shopInfo.getCtime()));
			Integer nowYear= Integer.parseInt(DateUtils.getYear(new Date()));
			for (int i = nowYear; i >= year; i--) {
				list.add(i);
			}	
		}
		return list;
	}

	/**
	 * 获取统计的月数
	 * @author yangbin
	 * @date 2018年5月12日
	 * @return
	 */
	@Override
	public List<Integer> queryStatisticsMonth() {
		Integer month=DateUtils.getMonth(new Date());
		List<Integer> monthList=new ArrayList<>();
		for (int i = month; i >=1; i--) {
			monthList.add(i);
		}
		return monthList;
	}


	/**
	 * 获取月销售信息
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param request
	 * @param model
	 * @param statisticsDto
	 * @return
	 */
	@Override
	public List<StatisticsMonthBean> queryMonthSale(StatisticsDto statisticsDto) {
		Assert.hasText(statisticsDto.getMerchantId(),"商家ID不能为空");
		Assert.NumberIsNotNull(statisticsDto.getYear(), "年份不能为空");
		Assert.NumberIsNotNull(statisticsDto.getMonth(), "月份不能为空");
		List<StatisticsMonthBean> list=this.orderServiceRemoting.queryMonthSale(statisticsDto).getData();
		return list;
	}

	/**
	 * 获取需要统计的季度
	 * @author yangbin
	 * @date 2018年5月12日
	 * @return
	 */
	@Override
	public List<Integer> queryStatisticsSeason() {
		Integer season=DateUtils.getSeason(new Date());
		List<Integer> list=new ArrayList<>();
		for (int i = season; i >= 1; i--) {
			list.add(i);
		}
		return list;
	}

	/**
	 * 获取季度销售情况
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param statisticsDto
	 * @return
	 */
	@Override
	public List<StatisticsSeasonBean> querySeasonSale(StatisticsDto statisticsDto) {
		Assert.hasText(statisticsDto.getMerchantId(),"商家ID不能为空");
		Assert.NumberIsNotNull(statisticsDto.getYear(),"年份不能为空");
		Assert.NumberIsNotNull(statisticsDto.getSeason(),"季度不能为空");
		statisticsDto.setMonthList(DateUtils.getMonthsBySeason(statisticsDto.getSeason()));
		List<StatisticsSeasonBean> list=this.orderServiceRemoting.querySeasonSale(statisticsDto).getData();
		Map<Integer,StatisticsSeasonBean> seasonSaleMap=new TreeMap<>();
		if(CollectionUtils.isNotEmpty(list)){
			for (StatisticsSeasonBean statisticsSeasonBean : list) {
				if(seasonSaleMap.get(statisticsSeasonBean.getMonth())==null){
					seasonSaleMap.put(statisticsSeasonBean.getMonth(),statisticsSeasonBean);
				}
			}
		}
		List<StatisticsSeasonBean> result=new ArrayList<>(3);
		StatisticsSeasonBean statisticsSeasonBean=null;
		for (Integer month:statisticsDto.getMonthList()) {
			if(seasonSaleMap.get(month)==null){
				statisticsSeasonBean=new StatisticsSeasonBean();
				statisticsSeasonBean.setMonth(month);
				statisticsSeasonBean.setTotalMoney(0D);
				statisticsSeasonBean.setTotalNum(0D);
				result.add(statisticsSeasonBean);
			}else{
				result.add(seasonSaleMap.get(month));
			}
		}
		return result;
	}

	/**
	 * 统计日销售
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param statisticsDto
	 * @return
	 */
	@Override
	public List<StatisticsDayBean> queryDaySale(StatisticsDto statisticsDto) {
		Assert.hasText(statisticsDto.getMerchantId(),"商家ID不能为空");
		return this.orderServiceRemoting.queryDaySale(statisticsDto).getData();
	}
}
