package com.ityb.qugou.evaluation.service;

import java.util.List;

import com.ityb.qugou.bo.evaluation.EvaluationBean;
import com.ityb.qugou.dto.evaluation.EvaluationDto;
import com.ityb.qugou.po.evaluation.Evaluation;
import com.ityb.qugou.vo.evaluation.EvaluationFractionVo;
import com.ityb.qugou.vo.evaluation.EvaluationVo;

public interface EvaluationService {

	/**
	 * 添加一个商品评论
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param evaluation
	 * @return
	 */
	void add(Evaluation evaluation);

	/**
	 * 获取评价列表
	 * @author yangbin
	 * @date 2018年4月4日
	 * @param evaluation
	 * @return
	 */
	List<EvaluationVo> queryEvaluationByDto(EvaluationDto evaluationDto);

	/**
	 * 计算评价列表条数
	 * @author yangbin
	 * @date 2018年4月4日
	 * @param evaluationDto
	 * @return
	 */
	Integer countEvaluationByDto(EvaluationDto evaluationDto);

	/**
	 * 获取一个详细评论细节
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluation
	 * @return
	 */
	EvaluationVo getEvaluation(Evaluation evaluation);

	/**
	 * 获取评价列表
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluationDto
	 * @return
	 */
	List<EvaluationBean> queryEvaluation(EvaluationDto evaluationDto);

	/**
	 * 获取评论分数
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluationDto
	 * @return
	 */
	EvaluationFractionVo getEvaluationFraction(EvaluationDto evaluationDto);

	/**
	 * 更新一个评价
	 * @author yangbin
	 * @date 2018年4月10日
	 * @param evaluation
	 * @return
	 */
	void updateEvaluation(Evaluation evaluation);

}
