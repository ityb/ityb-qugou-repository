package com.ityb.qugou.remoting;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.bo.evaluation.EvaluationBean;
import com.ityb.qugou.dto.evaluation.EvaluationDto;
import com.ityb.qugou.po.evaluation.Evaluation;
import com.ityb.qugou.vo.evaluation.EvaluationFractionVo;
import com.ityb.qugou.vo.evaluation.EvaluationVo;

@FeignClient(value = "evaluation-service")
public interface EvaluationServiceRemoting {

	/**
	 * 添加一条评价
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param evaluation
	 * @return
	 */
	@PostMapping(value="/evaluation/add")
	JsonResultData<Boolean> add(@RequestBody Evaluation evaluation);

	/**
	 * 获取评价列表
	 * @author yangbin
	 * @date 2018年4月4日
	 * @param evaluation
	 * @return
	 */
	@PostMapping(value="/evaluation/queryEvaluationByDto")
	JsonResultData<List<EvaluationVo>> queryEvaluationByDto(@RequestBody EvaluationDto evaluationDto);


	/**
	 * 计算评价列表条数
	 * @author yangbin
	 * @date 2018年4月4日
	 * @param evaluation
	 * @return
	 */
	@PostMapping(value="/evaluation/countEvaluationByDto")
	JsonResultData<Integer> countEvaluationByDto(@RequestBody EvaluationDto evaluationDto);
	
	/**
	 * 获取一个评论详细
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluation
	 * @return
	 */
	@PostMapping(value="/evaluation/getEvaluation")
	JsonResultData<EvaluationVo> getEvaluation(@RequestBody Evaluation evaluation);

	/**
	 * 获取评价列表
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluationDto
	 * @return
	 */
	@PostMapping(value="/evaluation/queryEvaluation")
	JsonResultData<List<EvaluationBean>> queryEvaluation(@RequestBody EvaluationDto evaluationDto);

	/**
	 * 获取评论分数
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluationDto
	 * @return
	 */
	@PostMapping(value="/evaluation/getEvaluationFraction")
	JsonResultData<EvaluationFractionVo> getEvaluationFraction(@RequestBody EvaluationDto evaluationDto);

	/**
	 * 更新一个评价
	 * @author yangbin
	 * @date 2018年4月10日
	 * @param evaluation
	 * @return
	 */
	@PostMapping(value="/evaluation/update")
	JsonResultData<Boolean> updateEvaluation(@RequestBody Evaluation evaluation);
	
}
