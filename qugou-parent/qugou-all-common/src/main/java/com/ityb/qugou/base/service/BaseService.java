package com.ityb.qugou.base.service;

import java.util.List;
import java.util.Map;

import com.ityb.qugou.base.core.CoreEntity;

public interface BaseService<T extends CoreEntity> {
	/**
	 * 根据class对象加载对应的数据列表
	 * 
	 * @param modelClass
	 * @return
	 */
	public List<T> getListData();

	/**
	 * 保存一条记录
	 * 
	 * @param dto
	 */
	public void save(T dto);

	/**
	 * 保存更新
	 * @param dto
	 */
	public Integer saveUpdate(T dto);

	/**
	 * 删除一条记录
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 获取数据h-ui前端
	 * @param params
	 * @return
	 */
	public List<T> query(Map<String, Object> params);

	/**
	 * 获取条数
	 * @param params
	 * @return
	 */
	public Integer count(Map<String, Object> params);

	/**
	 * 根据一个id 得到一条记录
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月24日
	 * @param id
	 * @return
	 */
	public T getOneById(String id);

	/**
	 * 递归删除
	 * @author yangbin
	 * @date 2017年12月30日
	 * @param id
	 */
	public void deleteCycle(String id);
}
