package com.ityb.qugou.dto.search;

import java.io.Serializable;

public class ShopSearchDto implements Serializable{

	/**
	 * serialVersionUID
	 * @date 2018年4月1日
	 */
	private static final long serialVersionUID = 968362598238337681L;
	private String id;
	private String keyWord;
	private String merchantCategoryName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getMerchantCategoryName() {
		return merchantCategoryName;
	}
	public void setMerchantCategoryName(String merchantCategoryName) {
		this.merchantCategoryName = merchantCategoryName;
	}

}
