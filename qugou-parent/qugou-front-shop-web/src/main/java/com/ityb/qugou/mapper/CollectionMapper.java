package com.ityb.qugou.mapper;

import java.util.List;

import com.ityb.qugou.dto.collection.CollectionDto;
import com.ityb.qugou.po.collection.Collection;
import com.ityb.qugou.vo.collection.CollectionProductVo;

public interface CollectionMapper {

	/**
	 * 得到一个收藏
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param collection
	 * @return
	 */
	Collection getCollection(Collection collection);

	/**
	 * 插入一条记录
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param collection
	 */
	void insertCollection(Collection collection);

	/**
	 * 更新一条记录
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param collectionDb
	 */
	void updateCollection(Collection collectionDb);

	
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
