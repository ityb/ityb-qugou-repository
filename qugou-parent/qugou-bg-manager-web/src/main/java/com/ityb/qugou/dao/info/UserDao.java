package com.ityb.qugou.dao.info;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.enums.FilterOrderEnum;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.po.manager.User;

@Repository
public class UserDao extends BaseDao<User> {

	@Autowired
	private BaseMapper baseMapper;

	@Override
	protected BaseMapper getMapper() {
		return baseMapper;
	}

	@Override
	protected Class<?> getModelClass() {
		return User.class;
	}

	@Override
	public SqlCondition getCondition(SqlCondition sqlCondition, Map<String, Object> params) {
		sqlCondition.addOrderItem("ctime",FilterOrderEnum.DESC);
		sqlCondition.addOrderItem("userName",FilterOrderEnum.ASC);
		return sqlCondition;
	}
}
