package com.ityb.qugou.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ityb.qugou.base.builder.SqlCondition;
import com.ityb.qugou.base.constant.BackgroundConstants;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.SqlUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.bo.manager.UserBean;
import com.ityb.qugou.dto.manager.UserDto;
import com.ityb.qugou.po.manager.Customer;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.po.merchant.Merchant;

@Repository
public class UserDao {
	@Autowired
	private BaseMapper baseMapper;

	/**
	 * 查询一个用户
	 * @param user
	 * @return
	 */
	public List<User> findUsers(User user) {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addFilterEqualItem("userName", user.getUserName());
		sqlCondition.addFilterEqualItem("userType", user.getUserType());
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) SqlUtils.executeFind(sqlCondition, baseMapper, User.class);
		return list;
	}

	public List<UserBean> queryUserInfo(UserDto userDto) {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSelectItem("user.userName", "userName");
		sqlCondition.addSelectItem("customer.realName","realName");
		sqlCondition.addSelectItem("customer.email","email");
		sqlCondition.addSelectItem("customer.phone","phone");
		if (!StringUtils.isBlank(userDto.getPhone())) {
			sqlCondition.addFilterEqualItem("customer.phone", userDto.getPhone());
		}
		if(userDto.getUserType()!=null){
			sqlCondition.addFilterEqualItem("user.userType", userDto.getUserType());
		}
		sqlCondition.addRelationItem(Customer.class,User.class);
		@SuppressWarnings("unchecked")
		List<UserBean> list = (List<UserBean>) SqlUtils.executeFind(sqlCondition, baseMapper, Customer.class,UserBean.class);
		return list;
	}
	
	public void insertUser(User user) {
		SqlUtils.executeInsert(baseMapper, user);
	}

	public void insertUserInfo(Customer customer) {
		SqlUtils.executeInsert(baseMapper, customer);
	}

	/**
	 * 查询一条用户信息
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param userDto
	 * @return
	 */
	public List<User> queryUserByDto(UserDto userDto) {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSelectItem("user.userName", "userName");
		sqlCondition.addSelectItem("user.id","id");
		sqlCondition.addSelectItem("user.password","password");
		sqlCondition.addSelectItem("user.state","state");
		sqlCondition.addSelectItem("user.userType","userType");
		if (!StringUtils.isBlank(userDto.getPhone())) {
			sqlCondition.addFilterEqualItem("customer.phone", userDto.getPhone());
		}
		if(!StringUtils.isBlank(userDto.getEmail())){
			sqlCondition.addFilterEqualItem("customer.email", userDto.getEmail());
		}
		if(userDto.getUserType()!=null){
			sqlCondition.addFilterEqualItem("user.userType", userDto.getUserType());
		}
		sqlCondition.addRelationItem(Customer.class,User.class);
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) SqlUtils.executeFind(sqlCondition, baseMapper, Customer.class,User.class);
		return list;
	}

	/**
	 * 获取商家用户
	 * @author yangbin
	 * @date 2018年3月13日
	 * @param userDto
	 * @return
	 */
	public List<User> queryMechantByDto(UserDto userDto) {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSelectItem("user.userName", "userName");
		sqlCondition.addSelectItem("user.id","id");
		sqlCondition.addSelectItem("user.password","password");
		sqlCondition.addSelectItem("user.state","state");
		sqlCondition.addSelectItem("user.userType","userType");
		sqlCondition.addFilterEqualItem("user.userType", BackgroundConstants.USER_TYPE_MERCHANT);
		if (!StringUtils.isBlank(userDto.getPhone())) {
			sqlCondition.addFilterEqualItem("mechant.phone", userDto.getPhone());
		}
		if(!StringUtils.isBlank(userDto.getEmail())){
			sqlCondition.addFilterEqualItem("mechant.email", userDto.getEmail());
		}
		sqlCondition.addRelationItem(Merchant.class,User.class);
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) SqlUtils.executeFind(sqlCondition, baseMapper, Merchant.class,User.class);
		return list;
	}
}
