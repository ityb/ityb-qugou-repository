package com.ityb.qugou.vo.merchant;

import java.io.Serializable;
import java.util.List;
/**
 * 商家平台相关result
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 * @param <T>
 */
public class ResultVo<T> implements Serializable {
	/**
	 * serialVersionUID
	 * 
	 * @date 2018年1月25日
	 */
	private static final long serialVersionUID = -7283054900980396141L;
	private Boolean rel;
	private String msg;
	private List<T> list;
	private Integer count;

	public ResultVo() {

	}

	public static <T> ResultVo<T> success(String msg, List<T> list, Integer count) {
		return new ResultVo<T>(true, msg, list, count);
	}

	public static <T> ResultVo<T> success(List<T> list, Integer count) {
		return new ResultVo<T>(true, "获取数据成功", list, count);
	}

	public static <T> ResultVo<T> error(String msg) {
		return new ResultVo<T>(false, msg, null, null);
	}

	public static <T> ResultVo<T> error() {
		return new ResultVo<T>(false, "获取数据失败", null, null);
	}

	public ResultVo(Boolean rel, String msg, List<T> list, Integer count) {
		this.rel = rel;
		this.msg = msg;
		this.list = list;
		this.count = count;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Boolean getRel() {
		return rel;
	}

	public void setRel(Boolean rel) {
		this.rel = rel;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
