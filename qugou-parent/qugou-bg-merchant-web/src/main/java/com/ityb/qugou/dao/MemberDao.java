package com.ityb.qugou.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.dao.BaseDao;
import com.ityb.qugou.base.mapper.BaseMapper;
import com.ityb.qugou.base.utils.DateUtils;
import com.ityb.qugou.base.utils.MapUtils;
import com.ityb.qugou.base.utils.NumberUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.dto.merchant.MemberDto;
import com.ityb.qugou.po.collection.Collection;
import com.ityb.qugou.vo.merchant.MemberVo;

@Repository
public class MemberDao extends BaseDao<Collection> {

	@Autowired
	private BaseMapper baseMapper;

	@Override
	protected BaseMapper getMapper() {
		return baseMapper;
	}

	@Override
	protected Class<?> getModelClass() {
		return Collection.class;
	}

	/**
	 * 查询数会员数据
	 * @author yangbin
	 * @date 2018年4月12日
	 * @param memberDto
	 * @return
	 * @throws Exception 
	 */
	public List<MemberVo> queryMember(MemberDto memberDto) throws Exception {
		String sql="SELECT "
				+ " U.USER_NAME AS userName, "
				+ "	U.ID AS userId,"
				+ " C.CTIME AS collectionDate, "
				+ "	CU.EMAIL AS email,"
				+ " (SELECT COUNT(1) FROM T_P_COLLECTION C1 LEFT  "
				+ " JOIN T_P_PRODUCT_SPECIFICATION PS ON C1.COLLECTION_ID = PS.ID  "
				+ " WHERE C1.TYPE = 1 AND C1.ISVALID=1 AND PS.ISVALID=1"
				+ " AND PS.CREATER = '"+memberDto.getMerchantId()+"') AS collectionProductNumber "
				+ " FROM T_P_COLLECTION C LEFT JOIN T_SYS_USER U ON U.ID = "
				+ " C.CREATER LEFT JOIN T_SYS_SHOP S ON S.ID =  "
				+ " C.COLLECTION_ID AND C.TYPE = 2  "
				+ " LEFT JOIN T_SYS_CUSTOMER CU ON CU.USER_ID=U.ID"
				+ " WHERE S.USER_ID = '"+memberDto.getMerchantId()+"' "
				+ " AND C.ISVALID=1 AND U.ISVALID=1 AND S.ISVALID=1 ";
				StringBuffer sqlBuff=new StringBuffer();
				if(memberDto.getCollectionDate()!=null){
					sqlBuff.append(" AND DATE(C.CTIME)=DATE('"+DateUtils.date2Str(memberDto.getCollectionDate())+"')");
				}
				if(!StringUtils.isBlank(memberDto.getUserName())){
					sqlBuff.append(" AND U.USER_NAME LIKE CONCAT('"+memberDto.getUserName()+"','%')");
				}
				sqlBuff.append(" ORDER BY C.CTIME ");
				if(NumberUtils.isNotNull(memberDto.getPageSize())&&NumberUtils.isNotNull(memberDto.getPageStart())){
					sqlBuff.append(" LIMIT "+memberDto.getPageStart()+","+memberDto.getPageSize());
				}
		@SuppressWarnings("unchecked")
		List<MemberVo> list = (List<MemberVo>) MapUtils.map2PojoList(baseMapper.execSQL(sql+sqlBuff.toString()), MemberVo.class);
		return list;
	}
	
	public int countMember(MemberDto memberDto){
		String sql="SELECT "
				+ " COUNT(1)"
				+ " FROM T_P_COLLECTION C LEFT JOIN T_SYS_USER U ON U.ID = "
				+ " C.CREATER LEFT JOIN T_SYS_SHOP S ON S.ID =  "
				+ " C.COLLECTION_ID AND C.TYPE = 2  AND C.ISVALID=1 AND U.ISVALID=1 AND S.ISVALID=1"
				+ " WHERE S.USER_ID = '"+memberDto.getMerchantId()+"' ";
		StringBuffer sqlBuff=new StringBuffer();
		if(memberDto.getCollectionDate()!=null){
			sqlBuff.append(" AND DATE(C.CTIME)=DATE('"+DateUtils.date2Str(memberDto.getCollectionDate())+"')");
		}
		if(!StringUtils.isBlank(memberDto.getUserName())){
			sqlBuff.append(" AND U.USER_NAME LIKE CONCAT('"+memberDto.getUserName()+"','%')");
		}
		int count = baseMapper.execSQLCount(sql+sqlBuff);
		return count;
	}
}
