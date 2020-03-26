package com.ityb.qugou.mapper;

import java.util.List;

import com.ityb.qugou.po.manager.DirectSupermarket;
import com.ityb.qugou.vo.manager.DirectSupermarketVo;

public interface DirectSupermarketMapper {

	/**
	 * 获取直通超市列表
	 * @author yangbin
	 * @date 2018年5月1日
	 * @param directSupermarket
	 * @return
	 */
	List<DirectSupermarketVo> queryDirectSupermarket(DirectSupermarket directSupermarket);

}
