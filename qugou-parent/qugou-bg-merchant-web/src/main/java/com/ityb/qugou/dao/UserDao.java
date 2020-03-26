package com.ityb.qugou.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.SqlUtils;
import com.ityb.qugou.po.manager.User;

@Repository
public class UserDao {
	@Autowired
	private BaseMapper baseMapper;

	/**
	 * 更新操作
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param user
	 */
	public void update(User user) {
		SqlUtils.executeUpdateById(baseMapper, user);
	}

	/**
	 * 获取一个用户
	 * @author yangbin
	 * @date 2018年5月15日
	 * @param user
	 * @return
	 */
	public User getUser(User user) {
		SqlCondition sqlCondition=new SqlCondition();
		sqlCondition.addFilterEqualItem("id",user.getId());
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) SqlUtils.executeFind(sqlCondition, baseMapper, User.class);
		return list.get(0);
	}
	
	
}
