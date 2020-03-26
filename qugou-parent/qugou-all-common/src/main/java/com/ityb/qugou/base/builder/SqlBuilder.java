package com.ityb.qugou.base.builder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.DateUtils;
import com.ityb.qugou.base.utils.ReflectionUtils;
import com.ityb.qugou.base.utils.StringUtils;

public class SqlBuilder {
	/**
	 * 构造sql语句
	 * 
	 * @param clazz
	 * @param sqlCondition
	 * @return
	 * @throws Exception
	 */
	public static MapParams buildFindSQL(Class<?> clazz, SqlCondition sqlCondition) throws Exception {
		MapParams mapParams = new MapParams();
		String tableName = getTableName(clazz);
		mapParams.setTableName(tableName + " " + getSimpleClassName(clazz));
		mapParams.setColumns(getColumns(sqlCondition.getSelectItems(), clazz));
		mapParams.setConnectionExpr(getConnectionCondition(sqlCondition.getFilterRelations(), clazz));
		mapParams.setWhereExpr(getWhereExpr(sqlCondition.getFilterItems(), clazz));
		mapParams.setGroupExpr(getGroupExper(clazz, sqlCondition.getFilterGroups()));
		mapParams.setOrderExpr(getOrderExper(clazz, sqlCondition.getFilterOrders()));
		mapParams.setPageExpr(getPageExper(sqlCondition.getFilterPage()));
		return mapParams;
	}

	/**
	 * 构造更新sql
	 * 
	 * @param clazz
	 * @param sqlCondition
	 * @return
	 * @throws Exception
	 */
	public static MapParams buildUpdateSQL(Class<?> clazz, SqlCondition sqlCondition) throws Exception {
		MapParams mapParams = new MapParams();
		String tableName = getTableName(clazz);
		mapParams.setTableName(tableName);
		mapParams.setUpdateExpr(getUpdateExper(sqlCondition.getFilterUpdates()));
		mapParams.setWhereExpr(getWhereExpr(sqlCondition.getFilterItems(), clazz));
		return mapParams;
	}

	/**
	 * 有条件的更新
	 * 
	 * @param obj
	 * @param sqlCondition
	 * @return
	 * @throws Exception
	 */
	public static MapParams buildUpdateSQL(Object obj, SqlCondition sqlCondition) throws Exception {
		MapParams mapParams = new MapParams();
		Class<?> clazz = obj.getClass();
		String tableName = getTableName(clazz);
		mapParams.setTableName(tableName);
		mapParams.setUpdateExpr(getUpdateExprByClazz(clazz, obj));
		mapParams.setWhereExpr(getWhereExpr(sqlCondition.getFilterItems(), clazz));
		return mapParams;
	}

	/**
	 * 根据id来更新一个数据
	 * 
	 * @param clazz
	 * @param sqlCondition
	 * @return
	 * @throws Exception
	 */
	public static MapParams buildUpdateByIdSQL(Class<?> clazz, SqlCondition sqlCondition) throws Exception {
		MapParams mapParams = new MapParams();
		String tableName = getTableName(clazz);
		mapParams.setTableName(tableName);
		mapParams.setUpdateExpr(getUpdateExper(sqlCondition.getFilterUpdates()));
		mapParams.setId(sqlCondition.getId());
		return mapParams;
	}

	/**
	 * 根据对象来更新一条记录
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static MapParams buildUpdateByIdSQL(Object obj) throws Exception {
		MapParams mapParams = new MapParams();
		Class<?> clazz = obj.getClass();
		String tableName = getTableName(clazz);
		mapParams.setTableName(tableName);
		mapParams.setUpdateExpr(getUpdateExprByClazz(clazz, obj));
		mapParams.setId(getId(clazz, obj));
		return mapParams;
	}

	/**
	 * 构造删除sql
	 * 
	 * @param clazz
	 * @param sqlCondition
	 * @return
	 * @throws Exception
	 */
	public static MapParams buildDeleteSQL(Class<?> clazz, SqlCondition sqlCondition) throws Exception {
		MapParams mapParams = new MapParams();
		String tableName = getTableName(clazz);
		mapParams.setTableName(tableName);
		mapParams.setWhereExpr(getWhereExpr(sqlCondition.getFilterItems(), clazz));
		return mapParams;
	}

	/**
	 * 根据id删除一条记录
	 * 
	 * @param clazz
	 * @param sqlCondition
	 * @return
	 * @throws Exception
	 */
	public static MapParams buildDeleteByIdSQL(Class<?> clazz, SqlCondition sqlCondition) throws Exception {
		MapParams mapParams = new MapParams();
		String tableName = getTableName(clazz);
		mapParams.setTableName(tableName);
		mapParams.setId(sqlCondition.getId());
		return mapParams;
	}

	/**
	 * 构造插入Sql
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static MapParams buildInsertSQL(Object obj) throws Exception {
		MapParams mapParams = new MapParams();
		Class<?> clazz = obj.getClass();
		String tableName = getTableName(clazz);
		mapParams.setTableName(tableName);
		String[] inserts = getInsertExprByClazz(clazz, obj);
		mapParams.setColumns(inserts[0]);
		mapParams.setValues(inserts[1]);
		return mapParams;
	}

	public static MapParams buildCountSQL(Class<?> clazz, SqlCondition sqlCondition) throws Exception {
		MapParams mapParams = new MapParams();
		String tableName = getTableName(clazz);
		mapParams.setTableName(tableName + " " + getSimpleClassName(clazz));
		mapParams.setWhereExpr(getWhereExpr(sqlCondition.getFilterItems(), clazz));
		return mapParams;
	}

	/**
	 * 得到Id的值
	 * 
	 * @param clazz
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private static String getId(Class<?> clazz, Object obj)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = ReflectionUtils.getMethodByName(clazz, "getId");
		String value = (String) method.invoke(obj);
		return "'" + value + "'";
	}

	/**
	 * 得到插入Sql的表达式
	 * 
	 * @param clazz
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private static String[] getInsertExprByClazz(Class<?> clazz, Object obj) throws Exception {
		StringBuffer iSbf = new StringBuffer("id,");
		StringBuffer vSbf = new StringBuffer("'");
		Object id = null;
		if ((id = ReflectionUtils.getFieldValue(obj, "id")) == null) { // 如果已经给定Id值将需要随机生成记录
			vSbf.append(StringUtils.getRandomStr());
		} else {// 不需要重新生成
			vSbf.append(id);
		}
		vSbf.append("'").append(",");
		Field[] fields = ReflectionUtils.getAllDeclaredFields(clazz);
		for (Field field : fields) {
			String name = field.getName();
			if (!"serialVersionUID".equals(name) && !"tableName".equals(name) && !"id".equals(name)) {
				Method method = ReflectionUtils.getMethodByName(clazz, "get" + StringUtils.toPreUpperCase(name));
				Object value = method.invoke(obj);
				if (value != null) {
					iSbf.append(StringUtils.upperCase2Underline(name)).append(",");
					if (field.getType().equals(String.class)) {// 表示是字符串
						vSbf.append("'").append(value).append("'").append(",");
					} else if (field.getType().equals(Date.class)) {// 表示是日期类型
						vSbf.append("'").append(DateUtils.date2Str((Date) value)).append("'").append(",");
					} else {
						vSbf.append(value).append(",");
					}
				} else {
					if (name.equals("ctime")) {
						iSbf.append(StringUtils.upperCase2Underline(name)).append(",");
						vSbf.append("'").append(DateUtils.date2Str(new Date())).append("'").append(",");

					}
				}
			}
		}
		iSbf.delete(iSbf.length() - 1, iSbf.length());
		vSbf.delete(vSbf.length() - 1, vSbf.length());
		return new String[] { iSbf.toString(), vSbf.toString() };
	}

	/**
	 * 根据class对象来构造更新语句
	 * 
	 * @param clazz
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static String getUpdateExprByClazz(Class<?> clazz, Object obj)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StringBuffer sbf = new StringBuffer();
		Field[] fields = ReflectionUtils.getAllDeclaredFields(clazz);
		for (Field field : fields) {
			String name = field.getName();
			if (!"serialVersionUID".equals(name) && !"tableName".equals(name) && !"id".equals(name)) {
				Method method = ReflectionUtils.getMethodByName(clazz, "get" + StringUtils.toPreUpperCase(name));
				Object value = method.invoke(obj);
				if (value != null) {
					if (field.getType().equals(String.class)) {
						sbf.append(StringUtils.upperCase2Underline(name)).append("=").append("'").append(value)
								.append("'").append(",");
					} else if (field.getType().equals(Date.class)) {
						sbf.append(StringUtils.upperCase2Underline(name)).append("=").append("'")
								.append(DateUtils.date2Str((Date) value)).append("'").append(",");
					} else {
						sbf.append(StringUtils.upperCase2Underline(name)).append("=")
								.append(value).append(",");
					}
				}
			}
		}
		sbf.delete(sbf.length() - 1, sbf.length());
		return sbf.toString();
	}

	/**
	 * 得到表名
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	private static String getTableName(Class<?> clazz) throws Exception {
		Method method = ReflectionUtils.getMethodByName(clazz, "getTableName");
		if (method == null) {
			return null;
		}
		Object object = clazz.newInstance();
		if (object == null) {
			return null;
		}
		StringBuffer sbf = new StringBuffer();
		String tableName = (String) method.invoke(object);
		sbf.append(tableName).append(" ").append(" ");
		return sbf.toString();
	}

	private static String getSimpleClassName(Class<?> clazz) {
		String name = clazz.getName();
		String className = name.substring(name.lastIndexOf(".") + 1);
		return className.toLowerCase();
	}

	/**
	 * 构造投影的列
	 * 
	 * @param list
	 * @return
	 */
	private static String getColumns(List<SelectItem> list, Class<?> clazz) {
		/**
		 * 如果列为空则表示查询所有的列
		 */
		StringBuffer sbf = new StringBuffer();
		if (CollectionUtils.isEmpty(list)) {
			Field[] fields = ReflectionUtils.getAllDeclaredFields(clazz);
			for (Field field : fields) {
				String name = field.getName();
				if (!"serialVersionUID".equals(name) && !"tableName".equals(name)) {
					// 驼峰法命名的都需要变为下划线
					sbf.append(StringUtils.upperCase2Underline(name)).append(" as ").append(name).append(",");
				}
			}
			sbf.delete(sbf.length() - 1, sbf.length()).append(" ");
			return sbf.toString();
		}
		for (SelectItem selectItem : list) {
			String item = selectItem.getItem();
			// 如果带有驼峰法就变为下划线
			String column = item;
			if (!item.contains(".")) {
				column = getSimpleClassName(clazz).toLowerCase() + "." + StringUtils.upperCase2Underline(column);
			} else {
				String columqz = item.substring(0, item.lastIndexOf("."));
				String columhz = StringUtils
						.upperCase2Underline(item.substring(item.lastIndexOf(".") + 1, item.length()));
				column = columqz + "." + columhz;
			}
			sbf.append(column).append(" as ");
			if (!StringUtils.isBlank(selectItem.getAlias())) {
				sbf.append(selectItem.getAlias());
			} else {
				item = item.substring(item.lastIndexOf(".") + 1, item.length());
				sbf.append(item);
			}
			sbf.append(",");
		}
		sbf.delete(sbf.length() - 1, sbf.length()).append(" ");
		return sbf.toString();
	}

	/**
	 * 得到连接表达表达式
	 * 
	 * @param list
	 * @param clazz
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	private static String getConnectionCondition(List<FilterRelation> list, Class<?> clazz)
			throws ClassNotFoundException, Exception {
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}
		StringBuffer sbf = new StringBuffer();
		for (FilterRelation filterRelation : list) {
			// 表示自关联
			if (filterRelation.getForeignClazz().equals(filterRelation.getPrimaryClazz())) {
				getSelfConnection(filterRelation, sbf);
			} else {
				if (StringUtils.isNotBlank(filterRelation.getAlias())) {
					System.err.println("非自关联不能加别名");
					throw new RuntimeException();
				}
				String primaryPackageName = filterRelation.getPrimaryClazz().getName();
				// 得到主键的类名
				String primaryClassName = primaryPackageName
						.substring(primaryPackageName.lastIndexOf(".") + 1, primaryPackageName.length()).toLowerCase();
				String fName = null;
				if ((fName = isContainForeignKey(filterRelation.getForeignClazz(), primaryClassName)) != null) {
					String foreignPackageName = filterRelation.getForeignClazz().getName();
					// 得到外键的类名
					String foreignClassName = foreignPackageName
							.substring(foreignPackageName.lastIndexOf(".") + 1, foreignPackageName.length())
							.toLowerCase();
					sbf.append(filterRelation.getConnectionType()).append(" ").append("join").append(" ");
					// 得到表名称
					sbf.append(getTableName(filterRelation.getPrimaryClazz()));
					if (StringUtils.isBlank(filterRelation.getAlias())) {
						sbf.append(getSimpleClassName(filterRelation.getPrimaryClazz())).append(" ");
					} else {
						primaryClassName = filterRelation.getAlias();
						sbf.append(primaryClassName).append(" ");
					}
					sbf.append("on").append(" ");
					// 驼峰法变为下划线
					sbf.append(foreignClassName).append(".").append(StringUtils.upperCase2Underline(fName)).append("=")
							.append(primaryClassName).append(".").append("id").append(" ");
					sbf.append("and ").append(primaryClassName).append(".isvalid=").append(1).append(" ");
				} else {
					System.err.println("未找到" + primaryClassName + "id外键");
					throw new RuntimeException();
				}
			}

		}
		return sbf.toString();
	}

	private static StringBuffer getSelfConnection(FilterRelation filterRelation, StringBuffer sbf) throws Exception {
		if (StringUtils.isBlank(filterRelation.getAlias())) {
			System.err.println("自关联需要设置别名");
			throw new RuntimeException();
		}
		Class<?> foreignClazz = filterRelation.getForeignClazz();
		if (!ReflectionUtils.isContainField(foreignClazz, "parentId")) {
			System.err.println("未找到" + "parentId" + "外键");
			throw new RuntimeException();
		}
		String name = getSimpleClassName(foreignClazz);
		String tableName = getTableName(foreignClazz);
		String alias = filterRelation.getAlias();
		sbf.append(filterRelation.getConnectionType()).append(" ").append("join").append(" ").append(tableName)
				.append(" ");
		sbf.append(alias).append(" ").append("on").append(" ");
		sbf.append(name).append(".").append("parent_id").append("=").append(alias).append(".").append("id").append(" ");
		sbf.append("and ").append(alias).append(".isvalid=").append(1).append(" ");
		return sbf;
	}

	/**
	 * 判断是都含有外键 外面命名以produdId和customer_id
	 * 
	 * @param clazz
	 * @param key
	 * @return
	 */
	private static String isContainForeignKey(Class<?> clazz, String key) {
		Field[] fields = ReflectionUtils.getAllDeclaredFields(clazz);
		for (Field field : fields) {
			String name = field.getName().toLowerCase();
			if (name.startsWith(key) && name.endsWith("id") && (key.length() + "id".length() <= name.length() + 1)) {
				return field.getName();
			}
		}
		return null;
	}

	/**
	 * 得到where条件表达式
	 * 
	 * @param list
	 * @param clazz
	 * @return
	 */
	private static String getWhereExpr(List<FilterItem> list, Class<?> clazz) {
		StringBuffer sbf = new StringBuffer("where ");
		String simpleClassName = getSimpleClassName(clazz);
		if (!CollectionUtils.isEmpty(list)) {
			for (FilterItem filterItem : list) {
				String item = filterItem.getItem();
				if (!item.contains(".")) {
					sbf.append(simpleClassName + ".");
				}
				// 驼峰法变为下划线
				sbf.append(StringUtils.upperCase2Underline(item)).append(" ");
				String symbol = filterItem.getSymbol();
				if ((symbol.contains("%like") || symbol.contains("like%"))) {
					symbol = symbol.replaceAll("like", filterItem.getValue().toString());
					sbf.append("like").append(" ").append("'");
					sbf.append(symbol).append("'").append(" ").append("and").append(" ");
				} else if (symbol.contains("in")) {
					sbf.append(filterItem.getSymbol()).append(" ");
					sbf.append(filterItem.getValue());
					sbf.append(" ").append("and").append(" ");
				} else {
					sbf.append(filterItem.getSymbol()).append(" ");
					Object value = filterItem.getValue();
					if (value.getClass().equals(String.class)) {
						sbf.append("'").append(filterItem.getValue()).append("'");
					} else if (value.getClass().equals(Date.class)) {
						sbf.append("'").append(DateUtils.date2Str((Date) value)).append("'");
					} else {
						sbf.append(value);
					}
					sbf.append(" ").append("and").append(" ");
				}
			}
		}
		sbf.append(simpleClassName).append(".").append("isvalid=").append(1);
		// String result = sbf.toString().substring(0,
		// sbf.toString().lastIndexOf("and") - 1);
		return sbf.toString();
	}

	/**
	 * 得到更新表达式
	 * 
	 * @param list
	 * @return
	 */
	private static String getUpdateExper(List<UpdateItem> list) {
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}
		StringBuffer sbf = new StringBuffer();
		for (UpdateItem updateItem : list) {
			sbf.append(StringUtils.upperCase2Underline(updateItem.getUpdateItem())).append("=");
			if (String.class.equals(updateItem.getValue().getClass())) {
				sbf.append("'").append(updateItem.getValue()).append("'").append(",");
			} else if (Date.class.equals(updateItem.getValue().getClass())) {
				sbf.append("'").append(DateUtils.date2Str((Date) updateItem.getValue())).append("'").append(",");
			} else {
				sbf.append(updateItem.getValue()).append(",");
			}
		}
		sbf.delete(sbf.length() - 1, sbf.length());
		return sbf.toString();
	}

	/**
	 * 得到order by表示式
	 * 
	 * @param list
	 * @return
	 */
	private static String getOrderExper(Class<?> clazz, List<FilterOrder> list) {
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}
		StringBuffer sbf = new StringBuffer("order by ");
		for (FilterOrder filterOrder : list) {
			String item = filterOrder.getItem();
			if (!item.contains(".")) {
				String simpleClassName = getSimpleClassName(clazz);
				sbf.append(simpleClassName + ".");
				sbf.append(StringUtils.upperCase2Underline(item));
			} else {
				String qz = item.substring(0, item.lastIndexOf("."));
				String hz = item.substring(item.lastIndexOf(".") + 1, item.length());
				sbf.append(qz).append(".").append(StringUtils.upperCase2Underline(hz));
			}
			sbf.append(" ").append(filterOrder.getOrderType()).append(",");
		}
		sbf.delete(sbf.length() - 1, sbf.length());
		return sbf.toString();
	}

	/**
	 * 得到分页表达式
	 * 
	 * @param filterPage
	 * @return
	 */
	private static String getPageExper(FilterPage filterPage) {
		if (filterPage == null) {
			return "";
		}
		StringBuffer sbf = new StringBuffer("limit ");
		int m = (filterPage.getPageNumber() - 1) * filterPage.getPageSize() + 1;
		int n = m + filterPage.getPageSize();
		sbf.append(m).append(",").append(n).append(" ");
		return sbf.toString();
	}

	/**
	 * 得到分组表达式
	 * 
	 * @param list
	 * @return
	 */
	private static String getGroupExper(Class<?> clazz, List<FilterGroup> list) {
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}
		StringBuffer sbf = new StringBuffer("group by ");
		for (FilterGroup filterGroup : list) {
			String item = filterGroup.getItem();
			if (!item.contains(".")) {
				String simpleClassName = getSimpleClassName(clazz);
				sbf.append(simpleClassName + ".");
				sbf.append(StringUtils.upperCase2Underline(item));
			} else {
				// 得到.之前的
				String qz = item.substring(0, item.lastIndexOf("."));
				// 得到.之后的
				String hz = item.substring(item.lastIndexOf(".") + 1, item.length());
				sbf.append(qz).append(".").append(StringUtils.upperCase2Underline(hz));
			}
			sbf.append(" ").append(" ").append(",");
		}
		sbf.delete(sbf.length() - 1, sbf.length());
		return sbf.toString();
	}

}
