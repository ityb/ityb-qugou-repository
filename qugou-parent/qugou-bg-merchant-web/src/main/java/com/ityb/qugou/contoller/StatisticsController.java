package com.ityb.qugou.contoller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.DateUtils;
import com.ityb.qugou.base.utils.ExcelUtils;
import com.ityb.qugou.base.utils.PdfUtils;
import com.ityb.qugou.bo.manager.StatisticsDayBean;
import com.ityb.qugou.bo.manager.StatisticsMonthBean;
import com.ityb.qugou.bo.manager.StatisticsSeasonBean;
import com.ityb.qugou.bo.manager.StatisticsYearBean;
import com.ityb.qugou.common.MerchantBaseController;
import com.ityb.qugou.configuration.PropertiesConfig;
import com.ityb.qugou.dto.manager.StatisticsDto;
import com.ityb.qugou.facade.StatisticsServiceFacade;

/**
 * 商家统计模块
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping(value = "/merchant/statistics")
public class StatisticsController extends MerchantBaseController{

	@Autowired
	private StatisticsServiceFacade statisticsServiceFacade;
	@Autowired
	private PropertiesConfig propertiesConfig;
	
	/**
	 * 跳转到年销售统计页面
	 * @author yangbin
	 * @date 2018年5月11日
	 * @return
	 */
	@RequestMapping(value="/year",method={RequestMethod.GET,RequestMethod.POST})
	public String statisticsYear(StatisticsDto statisticsDto,Model model,HttpServletRequest request){
		statisticsDto.setMerchantId(getCurrentUserId(request));
		List<Integer> yearList=this.statisticsServiceFacade.queryStatisticsYear(statisticsDto.getMerchantId());
		model.addAttribute("yearList",yearList);
		return "statistics-year";
	}
	
	/**
	 * 跳转到月销售统计页面
	 * @author yangbin
	 * @date 2018年5月11日
	 * @return
	 */
	@RequestMapping(value="/month",method={RequestMethod.GET,RequestMethod.POST})
	public String statisticsMonth(StatisticsDto statisticsDto,Model model,HttpServletRequest request){
		statisticsDto.setMerchantId(getCurrentUserId(request));
		List<Integer> yearList=this.statisticsServiceFacade.queryStatisticsYear(statisticsDto.getMerchantId());
		model.addAttribute("yearList",yearList);
		List<Integer> monthList=this.statisticsServiceFacade.queryStatisticsMonth();
		model.addAttribute("monthList",monthList);
		return "statistics-month";
	}
	
	/**
	 * 获取需要统计的报表
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param statisticsDto
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/season",method={RequestMethod.GET,RequestMethod.POST})
	public String statisticsSeason(StatisticsDto statisticsDto,Model model,HttpServletRequest request){
		statisticsDto.setMerchantId(getCurrentUserId(request));
		List<Integer> yearList=this.statisticsServiceFacade.queryStatisticsYear(statisticsDto.getMerchantId());
		model.addAttribute("yearList",yearList);
		List<Integer> seasonList=this.statisticsServiceFacade.queryStatisticsSeason();
		model.addAttribute("seasonList",seasonList);
		return "statistics-season";
	}
	
	/**
	 * 统计当天销售
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param statisticsDto
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/day",method={RequestMethod.GET,RequestMethod.POST})
	public String statisticsDay(StatisticsDto statisticsDto,Model model,HttpServletRequest request){
		return "statistics-day";
	}
	
	/**
	 * 获取年销售统计数据
	 * @author yangbin
	 * @date 2018年5月11日
	 * @param request
	 * @param model
	 * @param statisticsDto
	 * @return
	 */
	@RequestMapping(value = "/saleYear", method = { RequestMethod.POST })
	@ResponseBody
	public JsonResultData<List<StatisticsYearBean>> saleYear(HttpServletRequest request, Model model, StatisticsDto statisticsDto) {
		statisticsDto.setMerchantId(getCurrentUserId(request));
		try {
			List<StatisticsYearBean> saleList=this.statisticsServiceFacade.queryYearSale(statisticsDto);
			return JsonResultData.success(saleList);
		} catch (Exception e) {
			return JsonResultData.error(e.getMessage());
		}
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
	@RequestMapping(value = "/saleMonth", method = { RequestMethod.POST })
	@ResponseBody
	public JsonResultData<List<StatisticsMonthBean>> saleMonth(HttpServletRequest request, Model model, StatisticsDto statisticsDto) {
		statisticsDto.setMerchantId(getCurrentUserId(request));
		try {
			List<StatisticsMonthBean> saleList=this.statisticsServiceFacade.queryMonthSale(statisticsDto);
			return JsonResultData.success(saleList);
		} catch (Exception e) {
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 统计日销售
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param request
	 * @param model
	 * @param statisticsDto
	 * @return
	 */
	@RequestMapping(value = "/saleDay", method = { RequestMethod.POST })
	@ResponseBody
	public JsonResultData<List<StatisticsDayBean>> saleDay(HttpServletRequest request, Model model, StatisticsDto statisticsDto) {
		statisticsDto.setMerchantId(getCurrentUserId(request));
		statisticsDto.setDay(new Date());
		try {
			List<StatisticsDayBean> saleList=this.statisticsServiceFacade.queryDaySale(statisticsDto);
			if(CollectionUtils.isNotEmpty(saleList)&&saleList.size()>10){
				List<StatisticsDayBean> resultList=new ArrayList<>();
				for(int i=0;i<10;i++){
					resultList.add(saleList.get(i));
				}
				return JsonResultData.success(resultList);
			}
			return JsonResultData.success(saleList);
		} catch (Exception e) {
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取季度销售
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param request
	 * @param model
	 * @param statisticsDto
	 * @return
	 */
	@RequestMapping(value = "/saleSeason", method = { RequestMethod.POST })
	@ResponseBody
	public JsonResultData<List<StatisticsSeasonBean>> saleSeason(HttpServletRequest request, Model model, StatisticsDto statisticsDto) {
		statisticsDto.setMerchantId(getCurrentUserId(request));
		try {
			List<StatisticsSeasonBean> saleList=this.statisticsServiceFacade.querySeasonSale(statisticsDto);
			return JsonResultData.success(saleList);
		} catch (Exception e) {
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 年度报表导出
	 * @author yangbin
	 * @date 2018年5月11日
	 * @param request
	 * @param response
	 * @param statisticsDto
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/year", method = { RequestMethod.POST ,RequestMethod.GET})
	public void exportYear(HttpServletRequest request, HttpServletResponse response, StatisticsDto statisticsDto) throws Exception {
		statisticsDto.setMerchantId(getCurrentUserId(request));
		String title=statisticsDto.getYear()+"年度销售报表";
		String rowName[]={"序号","年份","月份","销售额","销售量"};
		String fileName=statisticsDto.getYear()+"年度销售报表";
		List<Object[]> list=new ArrayList<>();
		Object[] item=null;
		List<StatisticsYearBean> yearSaleList = this.statisticsServiceFacade.queryYearSale(statisticsDto);
		StatisticsYearBean statisticsYearBean=null;
		for(int i=0;i<yearSaleList.size();i++){
			item=new Object[rowName.length];
			statisticsYearBean=yearSaleList.get(i);
			item[0]=i+1;
			item[1]=statisticsDto.getYear();
			item[2]=statisticsYearBean.getMonth();
			item[3]=statisticsYearBean.getTotalMoney();
			item[4]=statisticsYearBean.getTotalNum();
			list.add(item);
		}
		if(statisticsDto.getExportType()==1){//导出到excel
			ExcelUtils.export(request, response, title, rowName, list, fileName);
			
		}else if(statisticsDto.getExportType()==2){//表示导出到pdf
			String textWaterMark="趣购网";
			PdfUtils.exportTableContent(request, response, textWaterMark, title, propertiesConfig.getFontPath(), rowName, list, fileName);
		}
	}
	
	/**
	 * 月销售报表
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param request
	 * @param response
	 * @param statisticsDto
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/month", method = { RequestMethod.POST ,RequestMethod.GET})
	public void exportMonth(HttpServletRequest request, HttpServletResponse response, StatisticsDto statisticsDto) throws Exception {
		statisticsDto.setMerchantId(getCurrentUserId(request));
		String title=statisticsDto.getYear()+"年"+statisticsDto.getMonth()+"月销售报表";
		String rowName[]={"序号","年份","月份","商品名称","销售额","销售量"};
		String fileName=statisticsDto.getYear()+"年"+statisticsDto.getMonth()+"月销售报表";
		List<Object[]> list=new ArrayList<>();
		Object[] item=null;
		List<StatisticsMonthBean> monthSaleList = this.statisticsServiceFacade.queryMonthSale(statisticsDto);
		StatisticsMonthBean statisticsMonthBean=null;
		for(int i=0;i<monthSaleList.size();i++){
			item=new Object[rowName.length];
			statisticsMonthBean=monthSaleList.get(i);
			item[0]=i+1;
			item[1]=statisticsDto.getYear();
			item[2]=statisticsDto.getMonth();
			item[3]=statisticsMonthBean.getProductName();
			item[4]=statisticsMonthBean.getTotalMoney();
			item[5]=statisticsMonthBean.getTotalNum();
			list.add(item);
		}
		if(statisticsDto.getExportType()==1){//导出到excel
			ExcelUtils.export(request, response, title, rowName, list, fileName);
			
		}else if(statisticsDto.getExportType()==2){//表示导出到pdf
			String textWaterMark="趣购网";
			PdfUtils.exportTableContent(request, response, textWaterMark, title, propertiesConfig.getFontPath(), rowName, list, fileName);
		}
	}
	
	/**
	 * 导出日销售情况
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param request
	 * @param response
	 * @param statisticsDto
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/day", method = { RequestMethod.POST ,RequestMethod.GET})
	public void exportDay(HttpServletRequest request, HttpServletResponse response, StatisticsDto statisticsDto) throws Exception {
		statisticsDto.setMerchantId(getCurrentUserId(request));
		Date nowDate=new Date();
		statisticsDto.setDay(nowDate);
		String dateStr=DateUtils.date2Str(nowDate,"yyyy-MM-dd");
		String title=dateStr+"销售报表";
		String rowName[]={"序号","日期","商品名称","销售额","销售量"};
		String fileName=dateStr+"销售报表";
		List<Object[]> list=new ArrayList<>();
		Object[] item=null;
		List<StatisticsDayBean> daySaleList = this.statisticsServiceFacade.queryDaySale(statisticsDto);
		StatisticsDayBean statisticsDayBean=null;
		for(int i=0;i<daySaleList.size();i++){
			item=new Object[rowName.length];
			statisticsDayBean=daySaleList.get(i);
			item[0]=i+1;
			item[1]=dateStr;
			item[2]=statisticsDayBean.getProductName();
			item[3]=statisticsDayBean.getTotalMoney();
			item[4]=statisticsDayBean.getTotalNum();
			list.add(item);
		}
		if(statisticsDto.getExportType()==1){//导出到excel
			ExcelUtils.export(request, response, title, rowName, list, fileName);
			
		}else if(statisticsDto.getExportType()==2){//表示导出到pdf
			String textWaterMark="趣购网";
			PdfUtils.exportTableContent(request, response, textWaterMark, title, propertiesConfig.getFontPath(), rowName, list, fileName);
		}
	}
	
	/**
	 * 季度销售报表导出
	 * @author yangbin
	 * @date 2018年5月12日
	 * @param request
	 * @param response
	 * @param statisticsDto
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/season", method = { RequestMethod.POST ,RequestMethod.GET})
	public void exportSeason(HttpServletRequest request, HttpServletResponse response, StatisticsDto statisticsDto) throws Exception {
		statisticsDto.setMerchantId(getCurrentUserId(request));
		String title=statisticsDto.getYear()+"年度第"+statisticsDto.getSeason()+"季度销售报表";
		String rowName[]={"序号","年份","月份","销售额","销售量"};
		String fileName=statisticsDto.getYear()+"年度第"+statisticsDto.getSeason()+"季度销售报表";
		List<Object[]> list=new ArrayList<>();
		Object[] item=null;
		List<StatisticsSeasonBean> seasonSaleList = this.statisticsServiceFacade.querySeasonSale(statisticsDto);
		StatisticsSeasonBean statisticsSeasonBean=null;
		for(int i=0;i<seasonSaleList.size();i++){
			item=new Object[rowName.length];
			statisticsSeasonBean=seasonSaleList.get(i);
			item[0]=i+1;
			item[1]=statisticsDto.getYear();
			item[2]=statisticsSeasonBean.getMonth();
			item[3]=statisticsSeasonBean.getTotalMoney();
			item[4]=statisticsSeasonBean.getTotalNum();
			list.add(item);
		}
		if(statisticsDto.getExportType()==1){//导出到excel
			ExcelUtils.export(request, response, title, rowName, list, fileName);
			
		}else if(statisticsDto.getExportType()==2){//表示导出到pdf
			String textWaterMark="趣购网";
			PdfUtils.exportTableContent(request, response, textWaterMark, title, propertiesConfig.getFontPath(), rowName, list, fileName);
		}
	}
}
