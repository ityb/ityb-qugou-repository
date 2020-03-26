package com.ityb.qugou.dao.content;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.MapUtils;
import com.ityb.qugou.po.manager.Area;

@Repository
public class AreaDao {

	@Autowired
	private BaseMapper baseMapper;

	/**
	 * 获取区域
	 * @author yangbin
	 * @date 2018年4月15日
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public List<Area> querySecondLevelArea() {
		String sql="SELECT " 
					+" id AS id,"
					+" name AS name,"
					+" parent_id AS parentId,"
					+" short_name AS shortName,"
					+" LEVEL AS level"
				+" FROM"
					+" t_sys_area"
				+" WHERE"
					+" LEVEL = 1"
					+" OR LEVEL = 2";
		try {
			@SuppressWarnings("unchecked")
			List<Area> list = (List<Area>) MapUtils.map2PojoList(baseMapper.execSQL(sql), Area.class);
			return list;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
