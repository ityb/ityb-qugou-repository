package com.ityb.qugou.service.content;

import java.util.List;

import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.dto.manager.DirectSupermarketDto;
import com.ityb.qugou.po.manager.DirectSupermarket;
import com.ityb.qugou.vo.manager.DirectSupermarketVo;

public interface DirectSupermarketService extends BaseService<DirectSupermarket> {

	/**
	 * 获取直通超市列表
	 * @author yangbin
	 * @date 2018年4月15日
	 * @param directSupermarketDto
	 * @return
	 */
	List<DirectSupermarketVo> queryDirectSupermarket(DirectSupermarketDto directSupermarketDto);

}
