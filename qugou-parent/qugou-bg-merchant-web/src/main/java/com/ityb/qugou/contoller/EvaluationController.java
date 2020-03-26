package com.ityb.qugou.contoller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ityb.qugou.base.constant.StatusConstants;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.common.MerchantBaseController;
import com.ityb.qugou.dto.evaluation.EvaluationDto;
import com.ityb.qugou.po.evaluation.Evaluation;
import com.ityb.qugou.remoting.EvaluationServiceRemoting;
import com.ityb.qugou.vo.evaluation.EvaluationVo;
import com.ityb.qugou.vo.merchant.ResultVo;

@Controller
@RequestMapping("/merchant/evaluation")
public class EvaluationController extends MerchantBaseController {

	@Autowired
	private EvaluationServiceRemoting evaluationServiceRemoting;

	/**
	 * 跳转到待回复评论列表
	 * 
	 * @author yangbin
	 * @date 2018年4月8日
	 * @return
	 */
	@RequestMapping(value = "/notReply/list", method = { RequestMethod.GET })
	public String listNotReplayEvaluation(HttpServletRequest request, EvaluationDto evaluationDto) {
		return "evaluation-notReply-list";
	}

	/**
	 * 获取待回复评论的数据
	 * 
	 * @author yangbin
	 * @date 2018年4月9日
	 * @param request
	 * @param evaluationDto
	 * @return
	 */
	@RequestMapping(value = "/notReply/list", method = { RequestMethod.POST })
	@ResponseBody
	public ResultVo<EvaluationVo> queryNotReplayEvaluation(HttpServletRequest request, EvaluationDto evaluationDto) {
		evaluationDto.setMerchantId(getCurrentUserId(request));
		evaluationDto.setType(1);
		JsonResultData<List<EvaluationVo>> jsonResultDataList = this.evaluationServiceRemoting
				.queryEvaluationByDto(evaluationDto);
		JsonResultData<Integer> jsonResultDataCount = this.evaluationServiceRemoting
				.countEvaluationByDto(evaluationDto);
		if (jsonResultDataList.getStatus() == StatusConstants.STATE_FAIL
				|| jsonResultDataCount.getStatus() == StatusConstants.STATE_FAIL) {
			return ResultVo.error(jsonResultDataList.getMsg());
		}
		return ResultVo.success(jsonResultDataList.getData(), jsonResultDataCount.getData());
	}

	/**
	 * 跳转到评论详细页面
	 * 
	 * @author yangbin
	 * @date 2018年4月10日
	 * @return
	 */
	@RequestMapping(value = "/look", method = { RequestMethod.GET, RequestMethod.POST })
	public String look(Model model, Evaluation evaluation) {
		JsonResultData<EvaluationVo> jsonResultData = this.evaluationServiceRemoting.getEvaluation(evaluation);
		model.addAttribute("evaluation", jsonResultData.getData());
		return "evaluation-look";
	}
	
	/**
	 * 跳转到回复页面
	 * @author yangbin
	 * @date 2018年4月10日
	 * @param model
	 * @param evaluation
	 * @return
	 */
	@RequestMapping(value="/reply",method = {RequestMethod.GET })
	public String reply(Model model, Evaluation evaluation){
		JsonResultData<EvaluationVo> jsonResultData = this.evaluationServiceRemoting.getEvaluation(evaluation);
		model.addAttribute("evaluation", jsonResultData.getData());
		return "evaluation-reply";
	}
	
	/**
	 * 更新一个评价
	 * @author yangbin
	 * @date 2018年4月10日
	 * @param evaluation
	 * @return
	 */
	@RequestMapping(value="/reply",method = {RequestMethod.POST })
	@ResponseBody
	public JsonResultData<Boolean> reply(Evaluation evaluation){
		evaluation.setType(2);
		JsonResultData<Boolean> jsonResultData = this.evaluationServiceRemoting.updateEvaluation(evaluation);
		return jsonResultData;
	}
	
	/**
	 * 获取历史评价
	 * @author yangbin
	 * @date 2018年4月12日
	 * @param model
	 * @param evaluation
	 * @return
	 */
	@RequestMapping(value = "/history/list", method = { RequestMethod.GET})
	public String historyEvaluationList(Model model, Evaluation evaluation) {
		return "evaluation-history-list";
	}
	
	/**
	 * 查询历史评价
	 * @author yangbin
	 * @date 2018年4月12日
	 * @param request
	 * @param evaluationDto
	 * @return
	 */
	@RequestMapping(value = "/history/list", method = { RequestMethod.POST })
	@ResponseBody
	public ResultVo<EvaluationVo> queryHistoryEvaluation(HttpServletRequest request, EvaluationDto evaluationDto) {
		evaluationDto.setMerchantId(getCurrentUserId(request));
		JsonResultData<List<EvaluationVo>> jsonResultDataList = this.evaluationServiceRemoting
				.queryEvaluationByDto(evaluationDto);
		JsonResultData<Integer> jsonResultDataCount = this.evaluationServiceRemoting
				.countEvaluationByDto(evaluationDto);
		if (jsonResultDataList.getStatus() == StatusConstants.STATE_FAIL
				|| jsonResultDataCount.getStatus() == StatusConstants.STATE_FAIL) {
			return ResultVo.error(jsonResultDataList.getMsg());
		}
		return ResultVo.success(jsonResultDataList.getData(), jsonResultDataCount.getData());
	}
}
