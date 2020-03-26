package com.ityb.qugou.base.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ityb.qugou.base.builder.MapParams;
import com.ityb.qugou.base.builder.SqlBuilder;
import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.core.CoreEntity;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.MapUtils;
import com.ityb.qugou.base.utils.SqlUtils;

public abstract class BaseDao<T extends CoreEntity> {
	protected abstract BaseMapper getMapper();

	protected abstract Class<?> getModelClass();

	/**
	 * 删除一条数据
	 * 
	 * @param id
	 */
	public void delete(String id) {
		try {
			SqlCondition sqlCondition = new SqlCondition();
			sqlCondition.setId(id);
			SqlUtils.executeDeleteById(sqlCondition, getMapper(),getModelClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存一条数据
	 * 
	 * @param dto
	 */
	public void insert(T dto) {
		MapParams params = null;
		try {
			params = SqlBuilder.buildInsertSQL(dto);
			BaseMapper baseMapper = getMapper();
			baseMapper.insert(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer update(T dto) {
		try {
			int updateLine = SqlUtils.executeUpdateById(getMapper(), dto);
			return updateLine;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取单个数据
	 * 
	 * @param params
	 * @return
	 */
	public List<T> find(Map<String, Object> params) {
		SqlCondition sqlCondition = new SqlCondition();
		if (!MapUtils.isEmpty(params)) {
			for (String key : params.keySet()) {
				sqlCondition.addFilterEqualItem(key, params.get(key));
			}
		}
		sqlCondition = getCondition(sqlCondition, params);
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) SqlUtils.executeFind(sqlCondition, getMapper(), getModelClass());
		return list;
	}

	public SqlCondition getCondition(SqlCondition sqlCondition, Map<String, Object> params) {
		return sqlCondition;
	}

	/**
	 * 获取列表
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月24日
	 * @param params
	 * @return
	 */
	public Integer count(Map<String, Object> params) {
		SqlCondition sqlCondition = new SqlCondition();
		if (!MapUtils.isEmpty(params)) {
			for (String key : params.keySet()) {
				sqlCondition.addFilterEqualItem(key, params.get(key));
			}
		}
		sqlCondition = getCondition(sqlCondition, params);
		int count = SqlUtils.executCount(sqlCondition, getMapper(), getModelClass());
		return count;
	}

	/**
	 * 获取一个对象
	 * @AUTHOR 杨彬
	 * @DATE 2017年12月24日
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getOneById(String id) {
		SqlCondition sqlCondition=new SqlCondition();
		sqlCondition.addFilterEqualItem("id", id);
		List<T> list = null;
		try {
			list=(List<T>) SqlUtils.executeFind(sqlCondition, getMapper(), getModelClass());
			if(!CollectionUtils.isEmpty(list)){
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @author yangbin
	 * @date 2017年12月30日
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryByParentId(String id) {
		SqlCondition sqlCondition=new SqlCondition();
		sqlCondition.addFilterEqualItem("parentId", id);
		List<T> list = new CopyOnWriteArrayList<>();
		try {
			list=(List<T>) SqlUtils.executeFind(sqlCondition, getMapper(), getModelClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
