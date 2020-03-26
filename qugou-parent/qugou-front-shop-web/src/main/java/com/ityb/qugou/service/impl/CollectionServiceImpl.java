package com.ityb.qugou.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.constant.CommonConstants;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.dto.collection.CollectionDto;
import com.ityb.qugou.mapper.CollectionMapper;
import com.ityb.qugou.po.collection.Collection;
import com.ityb.qugou.service.CollectionService;
import com.ityb.qugou.vo.collection.CollectionProductVo;

/**
 * 收藏相关服务类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class CollectionServiceImpl implements CollectionService{

	@Autowired
	private CollectionMapper collectionMapper;
	/**
	 * 添加收藏
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param collection
	 */
	@Override
	public void add(Collection collection) {
		Assert.hasText(collection.getCollectionId(),"收藏的商品或者商店不能为空");
		Assert.notNull(collection.getType(),"收藏的类型不能为空");
		//首先判断是这个collection是多条记录还是一条记录
		String collectionIds[]=collection.getCollectionId().split("\\s*,\\s*");
		Collection collectionItem=null;
		for (String collectionId : collectionIds) {
			collectionItem=new Collection();
			collectionItem.setCollectionId(collectionId);
			collectionItem.setType(collection.getType());
			collectionItem.setCreater(collection.getCreater());
			//首先去判断判断是否已经收藏过了 
			Collection collectionDb=this.collectionMapper.getCollection(collectionItem);
			if(collectionDb==null){
				collectionItem.setCtime(new Date());
				collectionItem.setId(StringUtils.getRandomStr());
				this.collectionMapper.insertCollection(collectionItem);
			}else{
				collectionDb.setCtime(new Date());
				this.collectionMapper.updateCollection(collectionDb);
			}
		}
	}
	
	/**
	 * 查询商品收藏列表
	 * @author yangbin
	 * @date 2018年4月7日
	 * @param collectionDto
	 * @return
	 */
	@Override
	public List<CollectionProductVo> queryProductCollectionByDto(CollectionDto collectionDto) {
		Assert.hasText(collectionDto.getCreater(),"收藏所属人不能为空");
		if (collectionDto.getPageIndex() != null && collectionDto.getPageIndex() <= 0) {
			collectionDto.setPageIndex(0);
		}
		if (collectionDto.getPageSize() != null && collectionDto.getPageSize() <= 0) {
			collectionDto.setPageSize(CommonConstants.DEFAULT_PAGE_SIZE);
		}
		if (collectionDto.getPageSize() != null && collectionDto.getPageIndex() != null) {
			collectionDto.setPageStart((collectionDto.getPageIndex() - 1) * collectionDto.getPageSize());
		}
		return this.collectionMapper.queryProductCollectionByDto(collectionDto);
	}

	/**
	 * 取消收藏
	 * @author yangbin
	 * @date 2018年5月16日
	 * @param request
	 * @param collection
	 * @return
	 */
	@Override
	public void delete(Collection collection) {
		Assert.hasText(collection.getId(),"收藏的ID不能为空");
		this.collectionMapper.delete(collection);
	}

}
