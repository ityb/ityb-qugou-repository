package com.ityb.qugou.vo.browseHistory;

import java.io.Serializable;
import java.util.Date;

public class BrowseHistoryVo implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年4月7日
	 */
	private static final long serialVersionUID = -4381213843559866476L;
	private String productId;
	private String productTitle;
	private String productPic;
	private String sellPoint;
	private String browseHistoryId;
	private Integer browseCount;
	private Date browseTime;
	private String shopCategoryName;
	public String getShopCategoryName() {
		return shopCategoryName;
	}
	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}
	public String getProductPic() {
		return productPic;
	}
	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public String getBrowseHistoryId() {
		return browseHistoryId;
	}
	public void setBrowseHistoryId(String browseHistoryId) {
		this.browseHistoryId = browseHistoryId;
	}
	public Integer getBrowseCount() {
		return browseCount;
	}
	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}
	public Date getBrowseTime() {
		return browseTime;
	}
	public void setBrowseTime(Date browseTime) {
		this.browseTime = browseTime;
	}

}
