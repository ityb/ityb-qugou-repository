package com.ityb.qugou.base.service.impl;

import java.util.List;
import java.util.Map;
import javax.management.RuntimeErrorException;
import com.ityb.qugou.base.core.CoreEntity;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.service.BaseService;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.StringUtils;

public abstract class BaseServiceImpl<T extends CoreEntity> implements BaseService<T> {
	/**
	 * 获取全部数据
	 */
	public List<T> query(Map<String, Object> params) {
		List<T> list = getDao().find(params);
		return list;
	}

	public void delete(String id) {
		getDao().delete(id);
	}

	public Integer saveUpdate(T dto) {
		return  getDao().update(dto);
	}

	/**
	 * 递归删除
	 * @author yangbin
	 * @date 2017年12月30日
	 * @param id
	 */
	public void deleteCycle(String id){
		if(StringUtils.isBlank(id)){
			throw new RuntimeErrorException(null, "id null...");
		}
		List<T> list=getDao().queryByParentId(id);
		if(!CollectionUtils.isEmpty(list)){
			for (T t : list) {
				deleteCycle(t.getId());
			}
		}
		this.delete(id);
	}
	
	public void save(T dto) {
		getDao().insert(dto);
	}
	
	public  Integer count(Map<String, Object> params){
		return getDao().count(params);
	}

	public T getOneById(String id){
		return getDao().getOneById(id);
	}
	protected abstract BaseDao<T> getDao();

	protected abstract Class<?> getModelClass();
}
