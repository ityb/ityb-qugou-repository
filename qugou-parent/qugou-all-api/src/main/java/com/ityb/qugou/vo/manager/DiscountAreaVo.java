package com.ityb.qugou.vo.manager;

import java.io.Serializable;
import java.util.List;

import com.ityb.qugou.po.manager.DiscountArea;

public class DiscountAreaVo implements Serializable{
	/**
	 * serialVersionUID
	 * @date 2018年5月1日
	 */
	private static final long serialVersionUID = -3648012746158256594L;
	private DiscountArea discountAreaParent;
	private List<DiscountArea> discountAreaChildren;
	public DiscountArea getDiscountAreaParent() {
		return discountAreaParent;
	}
	public void setDiscountAreaParent(DiscountArea discountAreaParent) {
		this.discountAreaParent = discountAreaParent;
	}
	public List<DiscountArea> getDiscountAreaChildren() {
		return discountAreaChildren;
	}
	public void setDiscountAreaChildren(List<DiscountArea> discountAreaChildren) {
		this.discountAreaChildren = discountAreaChildren;
	}
}
