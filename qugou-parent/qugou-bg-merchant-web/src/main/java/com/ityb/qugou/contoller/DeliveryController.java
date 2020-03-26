package com.ityb.qugou.contoller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.common.MerchantBaseController;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.po.order.OrderDelivery;
import com.ityb.qugou.remoting.OrderServiceRemoting;
import com.ityb.qugou.service.OrderDeliveryService;
import com.ityb.qugou.vo.order.OrderVo;

/**
 * 配送控制器
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/merchant/delivery")
public class DeliveryController extends MerchantBaseController{

	@Autowired
	private OrderServiceRemoting orderServiceRemoting;
	@Autowired
	private OrderDeliveryService orderDeliveryService;
	
	/**
	 * 跳转到订单配送页面
	 * 
	 * @author yangbin
	 * @date 2018年3月30日
	 * @return
	 */
	@RequestMapping(value = "/deliveryOrder/list", method = { RequestMethod.GET })
	public String deliveryOrder() {
		return "delivery-order-list";
	}
	
	/**
	 * 跳转到正在配送页面
	 * 
	 * @author yangbin
	 * @date 2018年3月30日
	 * @return
	 */
	@RequestMapping(value = "/history/list", method = { RequestMethod.GET })
	public String historyDeliveryOrder() {
		return "delivery-now-list";
	}

	/**
	 * 跳转到配送信息添加页面
	 * @author yangbin
	 * @date 2018年3月30日
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String add(Order order,Model model,HttpServletRequest request) {
		order.setCreater(getCurrentUserId(request));
		JsonResultData<OrderVo> jsonResultData = orderServiceRemoting.getOrder(order);
		model.addAttribute("orderDetail",jsonResultData.getData());
		return "delivery-add";
	}
	
	/**
	 * 添加配送信息
	 * @author yangbin
	 * @date 2018年3月30日
	 * @param orderDelivery
	 * @return
	 */
	@RequestMapping(value="/add",method = { RequestMethod.POST })
	@ResponseBody
	public JsonResultData<Boolean> addDeliveryInfo(HttpServletRequest request,OrderDelivery orderDelivery){
		try {
			orderDelivery.setCreater(getCurrentUserId(request));
			orderDeliveryService.addOrderDelivery(orderDelivery);
		} catch (Exception e) {
			return JsonResultData.error(e.getMessage());
		}
		return JsonResultData.success("添加配送信息成功",true);
	}
	
	/**
	 * 跳转到编辑页面
	 * @author yangbin
	 * @date 2018年3月30日
	 * @param orderDelivery
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET })
	public String update(OrderDelivery orderDelivery,Model model,HttpServletRequest request) {
		OrderDelivery orderDeliveryEntity=this.orderDeliveryService.getOrderDeliveryByEntity(orderDelivery);
		model.addAttribute("orderDelivery",orderDeliveryEntity);
		return "delivery-update";
	}
	/**
	 * 跳转到编辑页面
	 * @author yangbin
	 * @date 2018年3月30日
	 * @param orderDelivery
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public JsonResultData<Boolean> updateOrderDelivery(OrderDelivery orderDelivery,Model model,HttpServletRequest request) {
		try {
			this.orderDeliveryService.updateOrderDeliveryByEntity(orderDelivery);
		} catch (Exception e) {
			return JsonResultData.error(e.getMessage());
		}
		return JsonResultData.success(true);
	}
	
	@RequestMapping(value = "/look", method = { RequestMethod.GET })
	public String look(OrderDelivery orderDelivery,Model model,HttpServletRequest request) {
		OrderDelivery orderDeliveryEntity=this.orderDeliveryService.getOrderDeliveryByEntity(orderDelivery);
		model.addAttribute("orderDelivery",orderDeliveryEntity);
		return "delivery-look";
	}

}
