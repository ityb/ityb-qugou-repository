package com.ityb.qugou.service;

import java.util.List;

import com.ityb.qugou.dto.collection.CollectionDto;
import com.ityb.qugou.po.collection.Collection;
import com.ityb.qugou.vo.collection.CollectionProductVo;

public interface CollectionService {

	/**
	 * 添加收藏
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param collection
	 */
	void add(Collection collection);

	/**
	 * 查询商品收藏列表
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param collectionDto
	 * @return
	 */
	List<CollectionProductVo> queryProductCollectionByDto(CollectionDto collectionDto);

	/**
	 * 取消收藏
	 * @author yangbin
	 * @date 2018年5月16日
	 * @param request
	 * @param collection
	 * @return
	 */
	void delete(Collection collection);

}
