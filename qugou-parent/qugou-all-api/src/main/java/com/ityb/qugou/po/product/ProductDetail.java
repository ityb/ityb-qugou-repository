package com.ityb.qugou.po.product;

import com.ityb.qugou.base.entity.BaseEntity;

/**
 * 商品实体类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class ProductDetail extends BaseEntity {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月21日
	 */
	private static final long serialVersionUID = -1343295144242086584L;
	// 商品标题
	private String title;
	// 卖点
	private String sellPoint;
	// 指向商家商品分类
	private String merchantCategoryId;
	// 指向商城商品分类
	private String shopCategoryId;
	// 商品图片Url集合中间用逗号隔开
	private String picUrl;
	// 商品状态，0表示未上架，1表示已上架
	//private Integer state;
	//商品Id
	private String productId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMerchantCategoryId() {
		return merchantCategoryId;
	}

	public void setMerchantCategoryId(String merchantCategoryId) {
		this.merchantCategoryId = merchantCategoryId;
	}

	public String getShopCategoryId() {
		return shopCategoryId;
	}

	public void setShopCategoryId(String shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	/*public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}*/

	@Override
	public String getTableName() {
		return "t_p_product_detail";
	}

}
