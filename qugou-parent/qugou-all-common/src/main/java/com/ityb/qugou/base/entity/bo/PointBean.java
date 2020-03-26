package com.ityb.qugou.base.entity.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 平面坐标bean
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
public class PointBean implements Serializable {

	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月21日
	 */
	private static final long serialVersionUID = 4516503200914236958L;
	private BigDecimal y;// 平面y轴
	private BigDecimal x;// 平面z轴

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}

	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	@Override
	public String toString() {
		return "PointBean [y=" + y + ", x=" + x + "]";
	}
}