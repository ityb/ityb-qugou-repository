package com.ityb.qugou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.constant.OrderConstants;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.PaymentUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.common.ShopBaseController;
import com.ityb.qugou.dto.order.OrderDto;
import com.ityb.qugou.dto.order.OrderItemDto;
import com.ityb.qugou.po.order.DeliveryAddress;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.po.order.OrderDelivery;
import com.ityb.qugou.remoting.CartServiceRemoting;
import com.ityb.qugou.remoting.OrderServiceRemoting;
import com.ityb.qugou.service.DeliveryAddressService;
import com.ityb.qugou.service.OrderDeliveryService;
import com.ityb.qugou.vo.cart.CartOrderCheckShowVo;
import com.ityb.qugou.vo.order.OrderShowVo;

@Controller
@RequestMapping("/shop/order")
public class OrderController extends ShopBaseController {

	@Autowired
	private DeliveryAddressService deliveryAddressService;
	
	@Autowired
	private OrderServiceRemoting orderServiceRemoting;
	 
	@Autowired
	private CartServiceRemoting cartServiceRemoting;
	
	@Autowired
	private OrderDeliveryService orderDeliveryService;
	private final static Logger LOGGER = Logger.getLogger(OrderController.class);

	/**
	 * 跳转到订单审核页面
	 * 
	 * @author yangbin
	 * @date 2018年3月27日
	 * @param request
	 * @param response
	 * @param model
	 * @param cartId
	 *            购物车对应的ID，如果有多个购物车ID，中间用逗号隔开
	 * @return
	 */
	@RequestMapping(value = "/checkOrder", method = { RequestMethod.GET, RequestMethod.POST })
	public String checkOrder(HttpServletRequest request, HttpServletResponse response, Model model, String cartId) {
		setSearchHeader(request, response, model);
		if (StringUtils.isBlank(cartId)) {
			new ServiceException("购物车内的商品不能为空");
		}
		/*
		 * 1.获取地址列表
		 */
		DeliveryAddress deliveryAddress = new DeliveryAddress();
		deliveryAddress.setCity(getCity(request));
		deliveryAddress.setCreater(getCurrentUserId(request));
		List<DeliveryAddress> addressList = deliveryAddressService.queryDeliveryAddressByEntity(deliveryAddress);
		model.addAttribute("addressList", addressList);
		/*
		 * 2.获取购物车订单信息
		 */
		JsonResultData<CartOrderCheckShowVo> jsonResultData = cartServiceRemoting.queryCartOrderByCartId(cartId);
		model.addAttribute("cartOrderCheckShowVo", jsonResultData.getData());
		return "/order/order-check";
	}

	/**
	 * 跳转到支付页面
	 * 
	 * @author yangbin
	 * @date 2018年3月27日
	 * @return
	 */
	@RequestMapping(value = "/pay", method = { RequestMethod.GET, RequestMethod.POST })
	public String pay(HttpServletRequest request, HttpServletResponse response, Model model,String orderNumbers) {
		setSearchHeader(request, response, model);
		Assert.hasText(orderNumbers,"订单ID不能为空");
		model.addAttribute("orderNumbers",orderNumbers);
		return "/order/order-pay";
	}

	/**
	 * 订单生成页面
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/generate", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResultData<String> generateOrder(@RequestBody List<OrderItemDto> list) {
		if(CollectionUtils.isEmpty(list)){
			return JsonResultData.error("添加订单失败");
		}
		JsonResultData<String> jsonResultData=this.orderServiceRemoting.addOrder(list);
		return jsonResultData;
	}

	/**
	 * 订单支付
	 * @author yangbin
	 * @date 2018年3月27日
	 * @return
	 */
	@RequestMapping(value = "/doPay", method = { RequestMethod.GET, RequestMethod.POST })
	public String pay(String payBankCode,String orderNumbers) {
		Assert.hasText(orderNumbers,"订单编号不能为空");
		List<Order> orderList=this.orderServiceRemoting.queryOrderByOrderNumber(orderNumbers).getData();
		if(CollectionUtils.isEmpty(orderList)){
			throw new ServiceException("参数错误");		
		}
		Double total=0D;
		for (Order order : orderList) {
			total+=order.getTotal();
		}
		//如果是多个订单号，订单号之间用逗号隔开
		// 得到订单编码
		String orderId = orderNumbers;
		Double money=total;//这里是模拟支付，只支付一分钱 //应该是要取total
		money = 0.01D;
		String result=null;
		String successUrl = "http://www.qugou.com/shop/order/success";
		try {
			result = PaymentUtils.toPay(orderId, money, successUrl, payBankCode);
		} catch (Exception e) {
			throw new ServiceException("支付出错");
		}
		return "redirect:" +result;
	}
	
	/**
	 * 跳转到下单成功页面
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param payBankCode
	 * @param r2_TrxId 易付宝交易的流水号
	 * @return
	 */
	@RequestMapping(value = "/success", method = { RequestMethod.GET, RequestMethod.POST })
	public String paySuccess(HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam("r6_Order")String orderNumbers,@RequestParam("r2_TrxId") String serialNumber) {
		if(StringUtils.isNotBlank(serialNumber)){//表示是在线交易
			//修改订单状态
			Assert.hasText(orderNumbers, "订单编号不能为空");
			Order order=new Order();
			order.setState(OrderConstants.ORDER_STATE_PAID);
			order.setNumber(orderNumbers);
			order.setSerialNumber(serialNumber);
			JsonResultData<Boolean> jsonResult=this.orderServiceRemoting.updateOrderByOrderNumber(order);
			if(!jsonResult.getData()){
				return "/order/order-fail";
			}
		}
		setSearchHeader(request, response, model);
		return "/order/order-success";
	}
	
	/**
	 * 货到付款下单成功页面
	 * @author yangbin
	 * @date 2018年5月14日
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/orderSuccess", method = { RequestMethod.GET, RequestMethod.POST })
	public String orderSuccess(HttpServletRequest request, HttpServletResponse response, Model model) {
		setSearchHeader(request, response, model);
		return "/order/order-success";
	}
	
	/**
	 * 跳转到订单列表
	 * @author yangbin
	 * @date 2018年3月28日
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method={RequestMethod.POST,RequestMethod.GET})
	public String orderList(HttpServletRequest request, HttpServletResponse response, Model model,OrderDto orderDto){
		setSearchHeader(request, response, model);
		orderDto.setUserId(getCurrentUserId(request));
		List<OrderShowVo> orderList=this.orderServiceRemoting.queryOrderInfoByOrderDto(orderDto).getData();
		model.addAttribute("orderList",orderList);
		JsonResultData<Integer> totalRecordJsonResult=this.orderServiceRemoting.countOrderInfoByOrderDto(orderDto);
		model.addAttribute("totalRecord",totalRecordJsonResult.getData());
		model.addAttribute("orderDto",orderDto);
		return "/order/order-list";
	}
	
	/**
	 * 申请退款
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/refund", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResultData<Boolean> refund(Order order) {
		//修改订单状态
		if(StringUtils.isBlank(order.getNumber())){
			return JsonResultData.error("订单编号不能为空");
		}
		order.setState(OrderConstants.ORDER_APPLY_REFUND);
		JsonResultData<Boolean> jsonResult=this.orderServiceRemoting.updateOrderByOrderNumber(order);
		return jsonResult;
	}
	
	/**
	 * 确认收货
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/finish", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResultData<Boolean> finish(Order order) {
		//修改订单状态
		if(StringUtils.isBlank(order.getNumber())){
			return JsonResultData.error("订单编号不能为空");
		}
		order.setState(OrderConstants.ORDER_NOT_EVALUATION);
		JsonResultData<Boolean> jsonResult=this.orderServiceRemoting.updateOrderByOrderNumber(order);
		return jsonResult;
	}
	
	/**
	 * 删除一个订单号
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResultData<Boolean> delete(Order order) {
		//修改订单状态
		if(StringUtils.isBlank(order.getNumber())){
			return JsonResultData.error("订单编号不能为空");
		}
		order.setState(OrderConstants.ORDER_NOT_EVALUATION);
		JsonResultData<Boolean> jsonResult=this.orderServiceRemoting.deleteOrderByOrderNumber(order);
		return jsonResult;
	}
	
	/**
	 * 获取配送地址信息
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param orderDelivery
	 * @return
	 */
	@RequestMapping(value = "/getOrderDeliveryInfo", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResultData<OrderDelivery> getOrderDeliveryInfo(OrderDelivery orderDelivery) {
		//修改订单状态
		//Assert.hasText(orderDelivery.getOrderId(), "订单ID不能为空");
		try {
			OrderDelivery orderDeliveryEntity=orderDeliveryService.getOrderDeliveryInfoByEntity(orderDelivery);
			return JsonResultData.success(orderDeliveryEntity);
		} catch (Exception e) {
			LOGGER.error("getOrderDeliveryInfo.....获取订单配送信息失败",e);
			return JsonResultData.error(e.getMessage());
		}
	}
}
