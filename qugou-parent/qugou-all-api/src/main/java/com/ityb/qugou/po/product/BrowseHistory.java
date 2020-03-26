package com.ityb.qugou.po.product;

import com.ityb.qugou.base.entity.BaseEntity;

/**
 * 浏览记录实体
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class BrowseHistory extends BaseEntity{

	/**
	 * serialVersionUID
	 * @date 2018年3月19日
	 */
	private static final long serialVersionUID = -8910886998826971643L;
	private String productId;
	private String shopCategoryName;
	private Integer browseCount;

	public Integer getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getShopCategoryName() {
		return shopCategoryName;
	}

	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}

	@Override
	public String getTableName() {
		return "t_p_browse_history";
	}

}
