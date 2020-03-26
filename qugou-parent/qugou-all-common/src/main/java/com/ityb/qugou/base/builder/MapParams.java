package com.ityb.qugou.base.builder;

/**
 * 
 * @author Administrator
 *
 */
public class MapParams extends java.util.concurrent.ConcurrentHashMap<String, String> {

	private static final long serialVersionUID = 1L;
	private static final String ID = "id";
	private static final String TABLE_NAME = "tableName";
	private static final String COLUMNS = "columns";
	private static final String VALUES = "values";
	private static final String UPDATE_EXPR = "updateExpr";
	private static final String WHERE_EXPR = "whereExpr";
	private static final String ORDER_EXPR = "orderExpr";
	private static final String PAGE_EXPR = "pageExpr";
	private static final String GROUP_EXPR = "groupExpr";
	private static final String CONNECTION_EXPR = "connectionExpr";

	public void setId(String id) {
		put(ID, id);
	}

	public String getId() {
		return get(ID);
	}

	public void setTableName(String tableName) {
		put(TABLE_NAME, tableName);
	}

	public String getTableName() {
		return get(TABLE_NAME);
	}

	public void setColumns(String columns) {
		put(COLUMNS, columns);
	}

	public String getColumns() {
		return get(COLUMNS);
	}

	public void setValues(String columns) {
		put(VALUES, columns);
	}

	public String getValues() {
		return get(VALUES);
	}

	public void setUpdateExpr(String updateExpr) {
		put(UPDATE_EXPR, updateExpr);
	}

	public String getUpdateExpr() {
		return get(UPDATE_EXPR);
	}

	public void setWhereExpr(String whereExpr) {
		put(WHERE_EXPR, whereExpr);
	}

	public String getWhereExpr() {
		return get(WHERE_EXPR);
	}

	public void setOrderExpr(String orderExpr) {
		put(ORDER_EXPR, orderExpr);
	}

	public String getOrderExpr() {
		return get(ORDER_EXPR);
	}

	public void setPageExpr(String pageExpr) {
		put(PAGE_EXPR, pageExpr);
	}

	public String getPageExpr() {
		return get(PAGE_EXPR);
	}

	public void setConnectionExpr(String connectionExpr) {
		put(CONNECTION_EXPR, connectionExpr);
	}

	public String getConnectionExpr() {
		return get(CONNECTION_EXPR);
	}

	public void setGroupExpr(String groupExpr) {
		put(GROUP_EXPR, groupExpr);
	}

	public String getGroupExpr() {
		return get(GROUP_EXPR);
	}

	public String select() {
		return "select " + getColumns() + " from " + getTableName() + " " + getConnectionExpr() + " " + getWhereExpr()
				+ " " + getGroupExpr() + " " + getOrderExpr()+" "+getPageExpr();
	}

	public String save() {
		return "insert into " + getTableName() + " (" + " " + getColumns() + ") values (" + getValues() + ")";
	}
	
	public String update(){
		return "update "+getTableName()+" set "+getUpdateExpr() +" where id="+getId();
	}
	

}
