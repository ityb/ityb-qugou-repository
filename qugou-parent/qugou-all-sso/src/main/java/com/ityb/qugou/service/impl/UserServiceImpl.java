package com.ityb.qugou.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ityb.qugou.base.constant.BackgroundConstants;
import com.ityb.qugou.base.entity.bo.PointBean;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.utils.AddressUtils;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.bo.manager.UserBean;
import com.ityb.qugou.dao.MerchantDao;
import com.ityb.qugou.dao.ShopDao;
import com.ityb.qugou.dao.UserDao;
import com.ityb.qugou.dto.manager.MerchantDto;
import com.ityb.qugou.dto.manager.UserDto;
import com.ityb.qugou.po.manager.Customer;
import com.ityb.qugou.po.manager.User;
import com.ityb.qugou.po.merchant.Merchant;
import com.ityb.qugou.po.merchant.Shop;
import com.ityb.qugou.service.UserService;

/**
 * 用户service实现类
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private ShopDao shopDao;
	/**
	 * 获取一个用户
	 * @author yangbin
	 * @date 2018年3月2日
	 * @param user
	 * @return
	 */
	@Override
	public User getUser(User user) {
		Assert.hasText(user.getUserName(),"用户名不能为空");
		Assert.notNull(user.getUserType(),"用户类型不能为控股");
		List<User> users = userDao.findUsers(user);
		Assert.notEmpty(users,"获取用户信息失败");
		return users.get(0);
	}
	/**
	 * 得到用户详细信息
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param userDto
	 * @return
	 */
	@Override
	public UserBean getUserInfo(UserDto userDto) {
		Assert.isPhone(userDto.getPhone(), "手机号码错误");
		List<UserBean> list=this.userDao.queryUserInfo(userDto);
		Assert.notEmpty(list,"获取用户信息失败");
		return list.get(0);
	}
	/**
	 * 添加一个用户
	 * @author yangbin
	 * @date 2018年3月3日
	 * @param userDto
	 */
	@Override
	@Transactional
	public void addUser(UserDto userDto) {
		Assert.isPassword(userDto.getPassword(), "密码由6~20位数字、字符任意组合");
		Assert.isPhone(userDto.getPhone(), "手机号码不正确");
		Assert.isBetweenLength(userDto.getUserName(), 6, 12, "用户名由6-12位的中文、字符、数字组成");
		Assert.notNull(userDto.getUserType(),"客户类型不能为空");
		String userId=StringUtils.getRandomStr();
		User user=new User();
		user.setCtime(new Date());
		user.setId(userId);
		user.setUserName(userDto.getUserName());
		user.setPassword(StringUtils.MD5Encrypt(userDto.getPassword()));
		user.setState(1);
		user.setUserType(userDto.getUserType());
		this.userDao.insertUser(user);
		Customer customer=new Customer();
		customer.setUserId(userId);
		customer.setId(StringUtils.getRandomStr());
		customer.setCtime(new Date());
		customer.setPhone(userDto.getPhone());
		this.userDao.insertUserInfo(customer);
	}
	/**
	 * 获取商家用户
	 * @author yangbin
	 * @date 2018年3月13日
	 * @param userDto
	 * @return
	 */
	@Override
	public User getMerchantUser(UserDto userDto) {
		Assert.isPhone(userDto.getPhone(), "手机号码不正确");
		Assert.notNull(userDto.getUserType(),"参数错误");
		List<User> list=this.userDao.queryMechantByDto(userDto);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 添加一个用户
	 * @author yangbin
	 * @date 2018年3月16日
	 * @param merchantDto
	 */
	@Override
	@Transactional
	public void addMerchant(MerchantDto merchantDto) {
		Assert.isPhone(merchantDto.getPhone(), "电话号码格式不正确");
		Assert.isPassword(merchantDto.getPassword(), "密码格式不正确");
		Assert.hasText(merchantDto.getIdentityCard(), "身份证号码不能为空");
		Assert.hasText(merchantDto.getRealName(), "真实姓名不能为空");
		Assert.hasText(merchantDto.getUserName(), "用户名不能为空");
		Assert.hasText(merchantDto.getShopName(),"商店名称不能为空");
		Assert.hasText(merchantDto.getShopAddress(),"商店地址不能为空");
		Assert.hasText(merchantDto.getIdentityCardPic(), "身份证正反面图片不能为空");
		Assert.hasText(merchantDto.getBusinessLicencePic(), "商家的营业执照不能为空");
		User user=new User();
		String userId=StringUtils.getRandomStr();
		user.setId(userId);
		user.setCtime(new Date());
		user.setState(3);//表示未审核,1表示正常使用，0表示停用 -1 表示未审核 2表示审核未通过
		user.setUserType(BackgroundConstants.USER_TYPE_MERCHANT);//表示是商家用户
		user.setUserName(merchantDto.getUserName());
		user.setPassword(StringUtils.MD5Encrypt(merchantDto.getPassword()));
		//添加商家用户信息
		Merchant merchant=new Merchant();
		merchant.setCtime(new Date());
		merchant.setId(StringUtils.getRandomStr());
		merchant.setPhone(merchantDto.getPhone());
		merchant.setIdentityCardNumber(merchantDto.getIdentityCard());
		merchant.setIdentityCardPic(merchantDto.getIdentityCardPic());
		merchant.setRealName(merchantDto.getRealName());
		merchant.setUserId(userId);
		//商店信息
		Shop shop=new Shop();
		shop.setBusinessLicence(merchantDto.getBusinessLicencePic());
		shop.setUserId(userId);
		shop.setCtime(new Date());
		shop.setShopAddress(merchantDto.getShopAddress());
		shop.setShopName(merchantDto.getShopName());
		shop.setId(StringUtils.getRandomStr());
		//这里需要进行地址转换（转为平面坐标）
		PointBean pointBean = AddressUtils.addressToPoint(merchantDto.getShopAddress());
		shop.setX(pointBean.getX());
		shop.setY(pointBean.getY());
		//进行添加
		this.userDao.insertUser(user);
		this.merchantDao.insertMerchant(merchant);
		this.shopDao.insertShop(shop);
	}
}
