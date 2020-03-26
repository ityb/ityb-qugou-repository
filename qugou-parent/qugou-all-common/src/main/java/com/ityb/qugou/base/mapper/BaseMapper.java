/**
 * 
 */
package com.ityb.qugou.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.builder.MapParams;

/**
 * 
 * @author yangbin
 *
 */
@Repository
public interface BaseMapper {
	/**
	 * @param params
	 * @return
	 */
	public int insert(MapParams params);

	/**
	 * @param params
	 * @return
	 */
	public int update(MapParams params);

	/**
	 * @param params
	 * @return
	 */
	public int updateById(MapParams params);

	/**
	 * @param params
	 * @return
	 */
	public int delete(MapParams params);

	public int deleteById(MapParams params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> find(MapParams params);

	/**
	 * @param params
	 * @return
	 */
	public int count(MapParams params);

	/**
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> execSQL(@Param(value = "sql") String sql);

	/**
	 * @param sql
	 * @return
	 */
	public int execSQLCount(@Param(value = "sql") String sql);
}
