package com.ityb.qugou.service;

import java.util.List;

import com.ityb.qugou.po.manager.DirectSupermarket;
import com.ityb.qugou.vo.manager.DirectSupermarketVo;

/**
 * 直通超市服务类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public interface DirectSupermarketService {

	/**
	 * 获取直通超市列表
	 * @author yangbin
	 * @date 2018年5月1日
	 * @param directSupermarket
	 * @return
	 */
	List<DirectSupermarketVo> queryDirectSupermarket(DirectSupermarket directSupermarket);

}
