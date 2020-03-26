package com.ityb.qugou.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.SqlUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.po.merchant.Merchant;

@Repository
public class MerchantDao {

	@Autowired
	private BaseMapper baseMapper;

	/**
	 * 进行查询用户
	 * 
	 * @param user
	 * @param merchant
	 * @return
	 */
	public List<User> findUsers(User user, Merchant merchant) {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSelectItem("user.userName", "userName");
		sqlCondition.addSelectItem("user.password", "password");
		sqlCondition.addFilterEqualItem("user.userType", user.getUserType());
		if (!StringUtils.isBlank(merchant.getPhone())) {
			sqlCondition.addFilterEqualItem("phone", merchant.getPhone());
		}
		if (!StringUtils.isBlank(merchant.getEmail())) {
			sqlCondition.addFilterEqualItem("email", merchant.getEmail());
		}
		sqlCondition.addRelationItem(Merchant.class, User.class);
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) SqlUtils.executeFind(sqlCondition, baseMapper, Merchant.class, User.class);
		return list;
	}

	public void insertMerchant(Merchant merchant) {
		SqlUtils.executeInsert(baseMapper, merchant);
	}
}
