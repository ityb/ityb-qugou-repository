package com.ityb.qugou.base.builder;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ityb.qugou.base.enums.FilterConnectionEnum;
import com.ityb.qugou.base.enums.FilterOrderEnum;
import com.ityb.qugou.base.enums.FilterSymbolEnum;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.EnumUtils;

public class SqlCondition {
	private String id;
	private List<SelectItem> selectItems;
	private List<FilterItem> filterItems;
	private List<FilterRelation> filterRelations;
	private List<FilterGroup> filterGroups;
	private List<FilterOrder> filterOrders;
	private List<UpdateItem> filterUpdates;
	private FilterPage filterPage;
	private Lock lock = new ReentrantLock();

	/**
	 * select项
	 * 
	 * @param item
	 * @return
	 */
	public SqlCondition addSelectItem(String item) {
		SelectItem selectItem = new SelectItem(item);
		if (this.selectItems == null) {
			lock.lock();
			try {
				if (CollectionUtils.isEmpty(selectItems)) {
					selectItems = new CopyOnWriteArrayList<SelectItem>();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		this.selectItems.add(selectItem);
		return this;
	}

	/**
	 * 
	 * @param item
	 *            select项
	 * @param alias
	 *            别名
	 * @return
	 */
	public SqlCondition addSelectItem(String item, String alias) {
		SelectItem selectItem = new SelectItem(item, alias);
		if (this.selectItems == null) {
			lock.lock();
			try {
				if (CollectionUtils.isEmpty(selectItems)) {
					selectItems = new CopyOnWriteArrayList<SelectItem>();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		this.selectItems.add(selectItem);
		return this;
	}

	/**
	 * 向条件里面添加筛选项
	 * 
	 * @param item
	 *            需要筛选的列
	 * @param symbol
	 *            筛选条件
	 * @param value
	 *            筛选的参考值
	 * @return
	 */
	public SqlCondition addFilterItem(String item, FilterSymbolEnum symbol, Object value) {
		FilterItem filterItem = new FilterItem();
		if (this.filterItems == null) {
			lock.lock();
			try {
				if (CollectionUtils.isEmpty(filterItems)) {
					filterItems = new CopyOnWriteArrayList<FilterItem>();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		if (symbol.getName().contains("in")) {
			StringBuffer sbf = new StringBuffer("(");
			if (value.getClass().isArray()) {
				Object[] obj = (Object[]) value;
				for (Object object : obj) {
					if (object instanceof String) {
						sbf.append("'").append(object).append("'").append(",");
					} else if (object instanceof Date) {
						sbf.append("'").append(object).append("'").append(",");
					} else {
						sbf.append(object).append(",");
					}
				}
			}
			if (value instanceof List) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) value;
				for (Object object : list) {
					if (object instanceof String) {
						sbf.append("'").append(object).append("'").append(",");
					} else {
						sbf.append(object).append(",");
					}
				}
			}
			if (value instanceof Set) {
				@SuppressWarnings("unchecked")
				Set<Object> set = (Set<Object>) value;
				for (Object object : set) {
					if (object instanceof String) {
						sbf.append("'").append(object).append("'").append(",");
					} else {
						sbf.append(object).append(",");
					}
				}
			}
			value = sbf.delete(sbf.length() - 1, sbf.length()).append(")");
		}
		filterItem.setItem(item);
		filterItem.setSymbol(symbol.getName().toLowerCase());
		filterItem.setValue(value);
		this.filterItems.add(filterItem);

		return this;
	}

	public SqlCondition addFilterEqualItem(String item, Object value) {
		this.addFilterItem(item, FilterSymbolEnum.EQUAL, value);
		return this;
	}

	/**
	 * 主要是用于连接（默认是左连接）
	 * 
	 * @param primaryKey
	 *            主键
	 * @param foreignKey
	 *            外键
	 * @return
	 */

	public SqlCondition addRelationItem(Class<?> foreignClazz, Class<?> primaryClazz, String alias) {
		FilterRelation filterRelation = new FilterRelation(foreignClazz, primaryClazz,
				FilterConnectionEnum.LEFT_JOIN.getName());
		if (this.filterRelations == null) {
			lock.lock();
			try {
				if (CollectionUtils.isEmpty(filterRelations)) {
					filterRelations = new CopyOnWriteArrayList<FilterRelation>();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		filterRelation.setAlias(alias);
		this.filterRelations.add(filterRelation);
		return this;
	}

	/**
	 * 不加别名的连接条件
	 * 
	 * @param foreignClazz
	 * @param primaryClazz
	 * @return
	 */
	public SqlCondition addRelationItem(Class<?> foreignClazz, Class<?> primaryClazz) {
		FilterRelation filterRelation = new FilterRelation(foreignClazz, primaryClazz,
				FilterConnectionEnum.LEFT_JOIN.getName());
		if (this.filterRelations == null) {
			lock.lock();
			try {
				if (CollectionUtils.isEmpty(filterRelations)) {
					filterRelations = new CopyOnWriteArrayList<FilterRelation>();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		this.filterRelations.add(filterRelation);
		return this;
	}

	/**
	 * 不加别名的连接条件
	 * 
	 * @param foreignClazz
	 * @param primaryClazz
	 * @return
	 */
	public SqlCondition addRelationItem(Class<?> foreignClazz, Class<?> primaryClazz, String alias,
			FilterConnectionEnum type) {
		FilterRelation filterRelation = new FilterRelation(foreignClazz, primaryClazz, type.getName());
		if (this.filterRelations == null) {
			lock.lock();
			try {
				if (CollectionUtils.isEmpty(filterRelations)) {
					filterRelations = new CopyOnWriteArrayList<FilterRelation>();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		filterRelation.setAlias(alias);
		this.filterRelations.add(filterRelation);
		return this;
	}

	/**
	 * 用于连接（指定形式的连接）
	 * 
	 * @param primaryKey
	 *            主键
	 * @param foreignKey
	 *            外键
	 * @param type
	 *            类型
	 * @return
	 */
	public SqlCondition addRelationItem2Type(Class<?> foreignClazz, Class<?> primaryClazz,
			FilterConnectionEnum type) {
		if (!EnumUtils.isConnectionEnum(type.getName().toLowerCase())) {
			return this;
		}
		FilterRelation filterRelation = new FilterRelation(foreignClazz, primaryClazz, type.getName());
		if (this.filterRelations == null) {
			lock.lock();
			try {
				if (CollectionUtils.isEmpty(filterRelations)) {
					filterRelations = new CopyOnWriteArrayList<FilterRelation>();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		this.filterRelations.add(filterRelation);

		return this;
	}

	/**
	 * 添加排序项（默认是升序）
	 * 
	 * @param item
	 *            需要排序项
	 * @return
	 */
	public SqlCondition addOrderItem(String item) {
		FilterOrder filterOrder = new FilterOrder(item, FilterOrderEnum.ASC.getName());
		if (this.filterOrders == null) {
			lock.lock();
			try {
				if (CollectionUtils.isEmpty(filterOrders)) {
					filterOrders = new CopyOnWriteArrayList<FilterOrder>();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		this.filterOrders.add(filterOrder);
		return this;
	}

	/**
	 * 指定顺序排序
	 * 
	 * @param item
	 *            排序项
	 * @param type
	 *            排序类型
	 * @return
	 */
	public SqlCondition addOrderItem(String item, FilterOrderEnum type) {
		if (type == null) {
			return this;
		}
		if (this.filterOrders == null) {
			lock.lock();
			try {
				if (CollectionUtils.isEmpty(filterOrders)) {
					filterOrders = new CopyOnWriteArrayList<FilterOrder>();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		FilterOrder filterOrder = new FilterOrder();
		filterOrder.setOrderType(type.getName());
		filterOrder.setItem(item);
		this.filterOrders.add(filterOrder);

		return this;
	}

	/**
	 * 添加分组项
	 * 
	 * @param item
	 * @return
	 */
	public SqlCondition addGroupItem(String item) {
		FilterGroup filterGroup = new FilterGroup(item.toLowerCase());
		if (this.filterGroups == null) {
			lock.lock();
			try {
				if (CollectionUtils.isEmpty(filterGroups)) {
					filterGroups = new CopyOnWriteArrayList<FilterGroup>();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		this.filterGroups.add(filterGroup);
		return this;
	}

	/**
	 * 更新
	 * 
	 * @param item
	 *            更新项
	 * @param value
	 *            新值
	 * @return
	 */
	public SqlCondition addUpdateItem(String item, String value) {
		UpdateItem filterUpdate = new UpdateItem(item.toLowerCase(), value.toLowerCase());
		if (this.filterUpdates == null) {
			lock.lock();
			try {
				if (CollectionUtils.isEmpty(filterUpdates)) {
					filterUpdates = new CopyOnWriteArrayList<UpdateItem>();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		this.filterUpdates.add(filterUpdate);
		return this;
	}

	/**
	 * 
	 * @param start
	 *            开始页
	 * @param pageSize
	 *            页面大小
	 * @return
	 */
	@SuppressWarnings("unused")
	public SqlCondition addPageItem(Integer pageNumber, Integer pageSize) {
		FilterPage filterPage = new FilterPage(pageNumber, pageSize);
		if (this.filterPage == null) {
			lock.lock();
			try {
				if (this.filterPage == null) {
					this.filterPage = new FilterPage();
				}
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}
		}
		this.filterPage.setPageNumber(pageNumber);
		this.filterPage.setPageSize(pageSize);
		return this;
	}

	public List<UpdateItem> getFilterUpdates() {
		return filterUpdates;
	}

	public void setFilterUpdates(List<UpdateItem> filterUpdates) {
		this.filterUpdates = filterUpdates;
	}

	public List<FilterRelation> getFilterRelations() {
		return filterRelations;
	}

	public void setFilterRelations(List<FilterRelation> filterRelations) {
		this.filterRelations = filterRelations;
	}

	public List<FilterGroup> getFilterGroups() {
		return filterGroups;
	}

	public void setFilterGroups(List<FilterGroup> filterGroups) {
		this.filterGroups = filterGroups;
	}

	public List<FilterOrder> getFilterOrders() {
		return filterOrders;
	}

	public void setFilterOrders(List<FilterOrder> filterOrders) {
		this.filterOrders = filterOrders;
	}

	public List<FilterItem> getFilterItems() {
		return filterItems;
	}

	public void setFilterItems(List<FilterItem> filterItems) {
		this.filterItems = filterItems;
	}

	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

	public FilterPage getFilterPage() {
		return filterPage;
	}

	public void setFilterPage(FilterPage filterPage) {
		this.filterPage = filterPage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		StringBuffer sbf=new StringBuffer();
		id=sbf.append("'").append(id).append("'").toString();
		this.id = id;
	}
}
