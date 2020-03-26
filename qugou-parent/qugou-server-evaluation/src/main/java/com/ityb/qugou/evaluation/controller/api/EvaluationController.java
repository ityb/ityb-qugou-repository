package com.ityb.qugou.evaluation.controller.api;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.bo.evaluation.EvaluationBean;
import com.ityb.qugou.dto.evaluation.EvaluationDto;
import com.ityb.qugou.evaluation.service.EvaluationService;
import com.ityb.qugou.po.evaluation.Evaluation;
import com.ityb.qugou.vo.evaluation.EvaluationFractionVo;
import com.ityb.qugou.vo.evaluation.EvaluationVo;

@RestController
@RequestMapping(value="/evaluation")
public class EvaluationController {
	
	@Autowired
	private EvaluationService evaluationService;
	private final static Logger LOGGER = Logger.getLogger(EvaluationController.class);
	
	/**
	 * 添加一个商品评论
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param evaluation
	 * @return
	 */
	@RequestMapping(value="/add",method={RequestMethod.GET,RequestMethod.POST})
	public JsonResultData<Boolean> add(@RequestBody Evaluation evaluation){
		try {
			evaluationService.add(evaluation);
		} catch (Exception e) {
			LOGGER.error("add....添加商品评价失败",e);
			return JsonResultData.error(e.getMessage());
		}
		return JsonResultData.success(true);
	}
	
	/**
	 * 获取评价列表
	 * @author yangbin
	 * @date 2018年4月4日
	 * @param evaluation
	 * @return
	 */
	@RequestMapping(value="/queryEvaluationByDto",method={RequestMethod.GET,RequestMethod.POST})
	public JsonResultData<List<EvaluationVo>> queryEvaluationByDto(@RequestBody EvaluationDto evaluationDto){
		try {
			List<EvaluationVo> evaluationList=evaluationService.queryEvaluationByDto(evaluationDto);
			return JsonResultData.success(evaluationList);
		} catch (Exception e) {
			LOGGER.error("queryEvaluation....获取评价列表失败",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 计算评价列表条数
	 * @author yangbin
	 * @date 2018年4月4日
	 * @param evaluationDto
	 * @return
	 */
	@RequestMapping(value="/countEvaluationByDto",method={RequestMethod.GET,RequestMethod.POST})
	public JsonResultData<Integer> countEvaluationByDto(@RequestBody EvaluationDto evaluationDto){
		try {
			Integer count=evaluationService.countEvaluationByDto(evaluationDto);
			return JsonResultData.success(count);
		} catch (Exception e) {
			LOGGER.error("countEvaluationByDto....计算评价列表条数失败",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取一个详细评论细节
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluation
	 * @return
	 */
	@RequestMapping(value="/getEvaluation",method={RequestMethod.GET,RequestMethod.POST})
	public JsonResultData<EvaluationVo> getEvaluation(@RequestBody Evaluation evaluation){
		try {
			EvaluationVo evaluationVo=evaluationService.getEvaluation(evaluation);
			return JsonResultData.success(evaluationVo);
		} catch (Exception e) {
			LOGGER.error("getEvaluation....获取评论详细",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取评价列表
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluationDto
	 * @return
	 */
	@RequestMapping(value="/queryEvaluation",method={RequestMethod.GET,RequestMethod.POST})
	public JsonResultData<List<EvaluationBean>> queryEvaluation(@RequestBody EvaluationDto evaluationDto){
		try {
			List<EvaluationBean> list=evaluationService.queryEvaluation(evaluationDto);
			return JsonResultData.success(list);
		} catch (Exception e) {
			LOGGER.error("queryEvaluation....获取评论列表失败",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 获取评论分数失败
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluationDto
	 * @return
	 */
	@RequestMapping(value="/getEvaluationFraction",method={RequestMethod.GET,RequestMethod.POST})
	public JsonResultData<EvaluationFractionVo> getEvaluationFraction(@RequestBody EvaluationDto evaluationDto){
		try {
			EvaluationFractionVo evaluationFraction=evaluationService.getEvaluationFraction(evaluationDto);
			return JsonResultData.success(evaluationFraction);
		} catch (Exception e) {
			LOGGER.error("getEvaluationFraction....获取评论分数失败",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
	/**
	 * 更新一个评价
	 * @author yangbin
	 * @date 2018年4月10日
	 * @param evaluation
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.GET,RequestMethod.POST})
	public JsonResultData<Boolean> updateEvaluation(@RequestBody Evaluation evaluation){
		try {
			evaluationService.updateEvaluation(evaluation);
			return JsonResultData.success(true);
		} catch (Exception e) {
			LOGGER.error("updateEvaluation....更新数据失败",e);
			return JsonResultData.error(e.getMessage());
		}
	}
	
}
