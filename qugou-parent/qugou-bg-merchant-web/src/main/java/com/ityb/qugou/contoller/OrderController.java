package com.ityb.qugou.contoller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.constant.OrderConstants;
import com.ityb.qugou.base.constant.StatusConstants;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.common.MerchantBaseController;
import com.ityb.qugou.dto.order.OrderDto;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.remoting.OrderServiceRemoting;
import com.ityb.qugou.vo.merchant.ResultVo;
import com.ityb.qugou.vo.order.OrderStatisticsVo;
import com.ityb.qugou.vo.order.OrderVo;
/**
 * 订单模块控制器
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping(value="/merchant/order")
public class OrderController extends MerchantBaseController{
	
	@Autowired
	private OrderServiceRemoting orderServiceRemoting;
	/**
	 * 跳转到订单列表界面
	 * 
	 * @author yangbin
	 * @date 2018年2月5日
	 * @return
	 */
	@RequestMapping(value = "/new/list", method = { RequestMethod.GET })
	public String newOrderList(HttpServletRequest request, Model model) {
		return "order-new-list";
	}
	
	/**
	 * 获取订单数据
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param request
	 * @param model
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultVo<OrderVo> list(HttpServletRequest request, Model model, OrderDto orderDto) {
		orderDto.setMerchantId(getCurrentUserId(request));
		// 向远程服务端去获取数据
		JsonResultData<List<OrderVo>> jsonResultDataList = this.orderServiceRemoting.queryOrder(orderDto);
		// 向远程服务端区获取商品查询数量
		JsonResultData<Integer> jsonResultDataCount = this.orderServiceRemoting.countOrder(orderDto);
		if (jsonResultDataList.getStatus() == StatusConstants.STATE_FAIL
				|| jsonResultDataCount.getStatus() == StatusConstants.STATE_FAIL) {
			return ResultVo.error(jsonResultDataList.getMsg());
		}
		return ResultVo.success(jsonResultDataList.getData(), jsonResultDataCount.getData());
	}
	
	/**
	 * 跳转到浏览商品信息页面
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param request
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/lookOrder", method = { RequestMethod.GET})
	public String lookOrder(HttpServletRequest request, String orderId,Model model) {
	 Order order=new Order();
	 order.setId(orderId);
	 JsonResultData<OrderVo> jsonResultData=this.orderServiceRemoting.getOrder(order);
	 model.addAttribute("orderDetail",jsonResultData.getData());
	 return "order-look";
	}
	
	
	/**
	 * 跳转到更新商品信息页面
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param request
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/updateOrder", method = { RequestMethod.GET})
	public String updateOrder(HttpServletRequest request, String orderId,Model model) {
		Order order=new Order();
		order.setId(orderId);
		JsonResultData<OrderVo> jsonResultData=this.orderServiceRemoting.getOrder(order);
		model.addAttribute("orderDetail",jsonResultData.getData());
		return "order-update";
	}
	
	/**
	 * 更新订单信息
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param request
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/updateOrder", method = { RequestMethod.POST})
	@ResponseBody
	public JsonResultData<Boolean> updateOrder(HttpServletRequest request, Order order) {
		if(StringUtils.isBlank(order.getId())){
			return JsonResultData.error("商品ID不能为空");
		}
		order.setMtime(new Date());
		order.setUpdater(getCurrentUserId(request));
		JsonResultData<Boolean> jsonResultData=this.orderServiceRemoting.updateOrder(order);
		return jsonResultData;
	}
	
	/**
	 * 
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param request
	 * @param order
	 * @return
	 */
	@RequestMapping(value="/updateState",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public JsonResultData<Boolean> updateState(HttpServletRequest request, Order order) {
		if(StringUtils.isBlank(order.getId())){
			return JsonResultData.error("商品ID不能为空");
		}
		if(order.getState()!=null&&(order.getState()<-2||order.getState()>7)){
			return JsonResultData.error("订单状态不正确");	
		}
		order.setMtime(new Date());
		order.setUpdater(getCurrentUserId(request));
		JsonResultData<Boolean> jsonResultData=this.orderServiceRemoting.updateOrder(order);
		return jsonResultData;
	}
	
	/**
	 * 批量更新订单
	 * @author yangbin
	 * @date 2018年2月13日
	 * @param request
	 * @param order
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/updateProductBatch",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public JsonResultData<Boolean> updateProductBatch(HttpServletRequest request, Order order,String ids) {
		if(StringUtils.isBlank(ids)){
			return JsonResultData.error("商品ID不能为空");
		}
		if(order.getState()!=null&&(order.getState()<-1||order.getState()>5)){
			return JsonResultData.error("订单状态不正确");	
		}
		order.setMtime(new Date());
		order.setUpdater(getCurrentUserId(request));
		JsonResultData<Boolean> jsonResultData=this.orderServiceRemoting.updateProductBatch(order,ids);
		return jsonResultData;
	}
	
	/**
	 * 跳转到已发送订单列表界面
	 * @author yangbin
	 * @date 2018年2月5日
	 * @return
	 */
	@RequestMapping(value = "/history/list", method = { RequestMethod.GET })
	public String historyOrderList(HttpServletRequest request, Model model) {
		return "order-history-list";
	}
	
	/**
	 * 获取今日成交额
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param order
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/todayTrade",method={ RequestMethod.GET,RequestMethod.POST })
	public String todayTrade(Order order,HttpServletRequest request,Model model){
		order.setMerchantId(getCurrentUserId(request));
		order.setCtime(new Date());
		order.setState(OrderConstants.ORDER_FINISH_SIGN);
		JsonResultData<OrderStatisticsVo> jsonResultData=this.orderServiceRemoting.getTodayTrade(order);
		OrderStatisticsVo orderStatisticsVo = jsonResultData.getData();
		if(orderStatisticsVo!=null){
			orderStatisticsVo.setStatisticsTime(new Date());
		}
		model.addAttribute("orderStatisticsVo",orderStatisticsVo);
		return "today-trade";
	}
}
