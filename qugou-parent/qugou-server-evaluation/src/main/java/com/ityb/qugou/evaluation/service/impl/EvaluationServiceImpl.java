package com.ityb.qugou.evaluation.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ityb.qugou.base.constant.CommonConstants;
import com.ityb.qugou.base.constant.OrderConstants;
import com.ityb.qugou.base.constant.StatusConstants;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.message.JsonResultData;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.bo.evaluation.EvaluationBean;
import com.ityb.qugou.dto.evaluation.EvaluationDto;
import com.ityb.qugou.evaluation.constant.EvaluationConstants;
import com.ityb.qugou.evaluation.mapper.EvaluationMapper;
import com.ityb.qugou.evaluation.remoting.OrderServiceRemoting;
import com.ityb.qugou.evaluation.service.EvaluationService;
import com.ityb.qugou.po.evaluation.Evaluation;
import com.ityb.qugou.po.order.Order;
import com.ityb.qugou.po.order.OrderItem;
import com.ityb.qugou.vo.evaluation.EvaluationFractionVo;
import com.ityb.qugou.vo.evaluation.EvaluationVo;

/**
 * 评论相关服务类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class EvaluationServiceImpl implements EvaluationService{

	@Autowired
	private EvaluationMapper evaluationMapper;
	@Autowired
	private OrderServiceRemoting orderServiceRemoting;
	
	/**
	 * 添加一个商品评论
	 * @author yangbin
	 * @date 2018年4月3日
	 * @param evaluation
	 * @return
	 */
	@Override
	@Transactional
	public void add(Evaluation evaluation) {
		Assert.hasText(evaluation.getOrderId(),"评价的订单Id不能为空");
		Assert.hasText(evaluation.getProductSpecificationId(),"评价的商品规格不能为空");
		Assert.hasText(evaluation.getCreater(),"评价的的用户Id不能为空");
		evaluation.setId(StringUtils.getRandomStr());
		evaluation.setCtime(new Date());
		evaluationMapper.insertEvaluation(evaluation);
		//去判断是否该订单中有为评论的订单项
		OrderItem orderItem=new OrderItem();
		orderItem.setOrderId(evaluation.getOrderId());
		orderItem.setProductSpecificationId(evaluation.getProductSpecificationId());
		orderItem.setIsEvaluation(EvaluationConstants.IS_EVALUATION_FINISH);
		//更新订单项中的字段
		JsonResultData<Boolean> updateItemJsonResult=this.orderServiceRemoting.updateOrderItem(orderItem);
		if(updateItemJsonResult.getStatus()==StatusConstants.STATE_FAIL){
			throw new ServiceException(updateItemJsonResult.getMsg());
		}
		orderItem.setIsEvaluation(EvaluationConstants.IS_EVALUATION_NOT);
		JsonResultData<Integer> countJsonResult=orderServiceRemoting.countOrderItemByEntity(orderItem);
		if(countJsonResult.getStatus()==StatusConstants.STATE_FAIL){
			throw new ServiceException(countJsonResult.getMsg());
		}
		if(countJsonResult.getData()<=0){//表示该订单项中已经没有待评价的商品了
			//进行更新订单状态
			Order order=new Order();
			order.setId(evaluation.getOrderId());
			order.setState(OrderConstants.ORDER_STATE_FINISH);//表示订单已完成
			JsonResultData<Boolean> orederJsonResult=this.orderServiceRemoting.updateOrder(order);
			if(orederJsonResult.getStatus()==StatusConstants.STATE_FAIL){
				throw new ServiceException(orederJsonResult.getMsg());
			}
		}
	}
	
	
	/**
	 * 获取评价列表
	 * @author yangbin
	 * @date 2018年4月4日
	 * @param evaluation
	 * @return
	 */
	@Override
	public List<EvaluationVo> queryEvaluationByDto(EvaluationDto evaluationDto) {
		Assert.isTrue(StringUtils.isNotBlank(evaluationDto.getCreater())||StringUtils.isNotBlank(evaluationDto.getMerchantId()),"评价所属人或者所属商家不能为空");
		if (evaluationDto.getPageIndex() != null && evaluationDto.getPageIndex() <= 0) {
			evaluationDto.setPageIndex(0);
		}
		if (evaluationDto.getPageSize() != null && evaluationDto.getPageSize() <= 0) {
			evaluationDto.setPageSize(CommonConstants.DEFAULT_PAGE_SIZE);
		}
		if (evaluationDto.getPageSize() != null && evaluationDto.getPageIndex() != null) {
			evaluationDto.setPageStart((evaluationDto.getPageIndex() - 1) * evaluationDto.getPageSize());
		}
		return this.evaluationMapper.queryEvaluationByDto(evaluationDto);
	}

	/**
	 * 计算评价列表条数
	 * @author yangbin
	 * @date 2018年4月4日
	 * @param evaluationDto
	 * @return
	 */
	@Override
	public Integer countEvaluationByDto(EvaluationDto evaluationDto) {
		Assert.isTrue(StringUtils.isNotBlank(evaluationDto.getCreater())||StringUtils.isNotBlank(evaluationDto.getMerchantId()),"评价所属人或者所属商家不能为空");
		return this.evaluationMapper.countEvaluationByDto(evaluationDto);
	}

	/**
	 * 获取一个详细评论细节
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluation
	 * @return
	 */
	@Override
	public EvaluationVo getEvaluation(Evaluation evaluation) {
		Assert.hasText(evaluation.getId(), "评论ID不能为空");
		EvaluationDto evaluationDto=new EvaluationDto();
		evaluationDto.setEvaluationId(evaluation.getId());
		List<EvaluationVo> list = this.evaluationMapper.queryEvaluationByDto(evaluationDto);
		Assert.notEmpty(list,"评论ID有误");
		return list.get(0);
	}

	
	/**
	 * 获取评价列表
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluationDto
	 * @return
	 */
	@Override
	public List<EvaluationBean> queryEvaluation(EvaluationDto evaluationDto) {
		return this.evaluationMapper.queryEvaluation(evaluationDto);
	}


	/**
	 * 获取评论分数
	 * @author yangbin
	 * @date 2018年4月6日
	 * @param evaluationDto
	 * @return
	 */
	@Override
	public EvaluationFractionVo getEvaluationFraction(EvaluationDto evaluationDto) {
		EvaluationFractionVo evaluationFractionVo=this.evaluationMapper.getEvaluationFraction(evaluationDto);
		return evaluationFractionVo;
	}

	/**
	 * 更新一个评价
	 * @author yangbin
	 * @date 2018年4月10日
	 * @param evaluation
	 * @return
	 */
	@Override
	public void updateEvaluation(Evaluation evaluation) {
		Assert.hasText(evaluation.getId(), "ID不能为空");
		if(StringUtils.isNotBlank(evaluation.getEvaluationReplyContent())){
			Assert.NumberIsNotNull(evaluation.getType(), "回复的状态不能为空");
			evaluation.setEvaluationReplyTime(new Date());
		}
		evaluation.setMtime(new Date());
		this.evaluationMapper.update(evaluation);
	}
}
