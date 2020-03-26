package com.ityb.qugou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.bo.common.FileBean;
import com.ityb.qugou.bo.common.UploadResultBean;
import com.ityb.qugou.bo.evaluation.EvaluationBean;
import com.ityb.qugou.common.FileUploadService;
import com.ityb.qugou.common.ShopBaseController;
import com.ityb.qugou.constant.OrderConstants;
import com.ityb.qugou.dto.evaluation.EvaluationDto;
import com.ityb.qugou.dto.order.OrderDto;
import com.ityb.qugou.po.evaluation.Evaluation;
import com.ityb.qugou.remoting.EvaluationServiceRemoting;
import com.ityb.qugou.remoting.OrderServiceRemoting;
import com.ityb.qugou.vo.evaluation.EvaluationVo;
import com.ityb.qugou.vo.order.OrderShowVo;

/**
 * 商品评价相关控制器
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Controller
@RequestMapping("/shop/evaluation")
public class EvaluationController extends ShopBaseController {

	@Autowired
	private OrderServiceRemoting orderServiceRemoting;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private EvaluationServiceRemoting evaluationServiceRemoting;

	/**
	 * 跳转到商品评价列表页面
	 * 
	 * @author yangbin
	 * @date 2018年4月2日
	 * @return
	 */
	@RequestMapping(value = "/notEvaluation/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String listNotEvaluation(HttpServletRequest request, HttpServletResponse response, Model model, Evaluation evaluation) {
		setSearchHeader(request, response, model);
		OrderDto orderDto = new OrderDto();
		orderDto.setUserId(getCurrentUserId(request));
		orderDto.setState(OrderConstants.ORDER_STATE_EVALUATION);
		orderDto.setOrderId(evaluation.getOrderId());
		orderDto.setIsEvaluation(OrderConstants.ORDER_ISVALUATION_NOT);//
		List<OrderShowVo> orderList = this.orderServiceRemoting.queryOrderInfoByOrderDto(orderDto).getData();
		model.addAttribute("orderList", orderList);
		JsonResultData<Integer> totalRecordJsonResult = this.orderServiceRemoting.countOrderInfoByOrderDto(orderDto);
		model.addAttribute("totalRecord", totalRecordJsonResult.getData());
		model.addAttribute("evaluation", evaluation);
		return "/evaluation/evaluation-not-list";
	}

	/**
	 * 上传评价图片
	 * 
	 * @author yangbin
	 * @date 2018年4月3日
	 * @return
	 */
	@RequestMapping(value = "/upload", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public UploadResultBean upload(HttpServletRequest request) {
		MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
		FileBean fileBean = fileUploadService.upload(file);
		UploadResultBean uploadResultBean = null;
		if (fileBean == null) {
			uploadResultBean = new UploadResultBean();
			uploadResultBean.setCode(404);// 表示成功
			uploadResultBean.setMsg("上传文件失败");
			return uploadResultBean;
		}
		uploadResultBean = new UploadResultBean();
		uploadResultBean.setCode(200);// 表示成功
		uploadResultBean.setSrc(fileBean.getPath());
		uploadResultBean.setMsg("上传文件成功");
		return uploadResultBean;
	}

	/**
	 * 添加一个订单评价
	 * 
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param evaluation
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResultData<Boolean> add(HttpServletRequest request,Evaluation evaluation) {
		evaluation.setCreater(getCurrentUserId(request));
		JsonResultData<Boolean> jsonResultData = this.evaluationServiceRemoting.add(evaluation);
		return jsonResultData;
	}
	
	
	/**
	 * 跳转到已评论的订单详情
	 * @author yangbin
	 * @date 2018年4月4日
	 * @param request
	 * @param response
	 * @param model
	 * @param evaluation
	 * @return
	 */
	@RequestMapping(value = "/finishEvaluation/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String listFinishEvaluation (HttpServletRequest request, HttpServletResponse response, Model model, EvaluationDto evaluationDto) {
		setSearchHeader(request, response, model);
		evaluationDto.setCreater(getCurrentUserId(request));
		JsonResultData<List<EvaluationVo>> evaluationJsonResult=this.evaluationServiceRemoting.queryEvaluationByDto(evaluationDto);
		model.addAttribute("evaluationList",evaluationJsonResult.getData());
		JsonResultData<Integer> countJsonResult=this.evaluationServiceRemoting.countEvaluationByDto(evaluationDto);
		model.addAttribute("totalRecord",countJsonResult.getData());
		return "/evaluation/evaluation-finish-list";
	}
	
	/**
	 * 获取一个评价细节
	 * @author yangbin
	 * @date 2018年4月4日
	 * @param request
	 * @param response
	 * @param model
	 * @param evaluation
	 * @return
	 */
	@RequestMapping(value = "/getEvaluation", method = { RequestMethod.POST, RequestMethod.GET })
	public String getEvaluation (HttpServletRequest request, HttpServletResponse response, Model model, Evaluation evaluation) {
		setSearchHeader(request, response, model);
		JsonResultData<EvaluationVo> jsonResultData=this.evaluationServiceRemoting.getEvaluation(evaluation);
		model.addAttribute("evaluation",jsonResultData.getData());
		return "/evaluation/evaluation-detail";
	}
	
	/**
	 * 获取对应的评论信息
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluationDto
	 * @return
	 */
	@RequestMapping(value = "/queryEvaluation", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResultData<List<EvaluationBean>> queryEvaluation(EvaluationDto evaluationDto){
		JsonResultData<List<EvaluationBean>> jsonResultData=this.evaluationServiceRemoting.queryEvaluation(evaluationDto);
		return jsonResultData;
	}
}
