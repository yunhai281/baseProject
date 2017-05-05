package com.boyuyun.base.sys.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sso.entity.SSOLog;
import com.boyuyun.base.sys.dao.OperateLogDao;
import com.boyuyun.base.sys.entity.OperateLog;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OperateLogDaoImpl extends BaseDAO implements OperateLogDao {

	@Override
	public List<OperateLog> getListPaged(OperateLog operateLog) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select id,moduleName,operateTime,operateType,operateUserId,operateUserName,ip,")
		   .append("operateResult,operateDataId,remark from operate_log where 1=1");
		List param=new ArrayList<>();
		if(!Strings.isNullOrEmpty(operateLog.getOperate()))
		{
			String operate=operateLog.getOperate().trim();
			sql.append(" and (moduleName like ?");
			param.add("%"+operate+"%");
			sql.append(" or operateType like ?");
			param.add("%"+operate+"%");
			sql.append(" or operateUserName like ?");
			param.add("%"+operate+"%");
			sql.append(" or operateResult like ?");
			param.add("%"+operate+"%");
			sql.append(" or operateDataId like ?");
			param.add("%"+operate+"%");
			sql.append(" or ip like ?)");
			param.add("%"+operate+"%");			
		}
		if(!Strings.isNullOrEmpty(operateLog.getBoperateTime()))
		{
			sql.append(" and Date(operateTime) >= ?");
			param.add(operateLog.getBoperateTime());
		}
		if(!Strings.isNullOrEmpty(operateLog.getEoperateTime()))
		{
			sql.append(" and Date(operateTime) <= ?");
			param.add(operateLog.getEoperateTime());
		}
		sql.append(" order by operateTime desc");
		BeanListHandler handler = new BeanListHandler(OperateLog.class);
		return this.executeQueryPage(sql.toString(), operateLog.getBegin(), operateLog.getEnd(), param.toArray(), handler);
	}

	@Override
	public int getListPagedCount(OperateLog operateLog) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from operate_log where 1=1");
		List param=new ArrayList<>();
		if(!Strings.isNullOrEmpty(operateLog.getOperate()))
		{
			String operate=operateLog.getOperate();
			sql.append(" and (moduleName like ?");
			param.add("%"+operate+"%");
			sql.append(" or operateType like ?");
			param.add("%"+operate+"%");
			sql.append(" or operateUserName like ?");
			param.add("%"+operate+"%");
			sql.append(" or operateResult like ?");
			param.add("%"+operate+"%");
			sql.append(" or operateDataId like ?");
			param.add("%"+operate+"%");
			sql.append(" or ip like ?)");
			param.add("%"+operate+"%");	
		}
		if(!Strings.isNullOrEmpty(operateLog.getBoperateTime()))
		{
			sql.append(" and Date(operateTime) >= ?");
			param.add(operateLog.getBoperateTime());
		}
		if(!Strings.isNullOrEmpty(operateLog.getEoperateTime()))
		{
			sql.append(" and Date(operateTime) <= ?");
			param.add(operateLog.getEoperateTime());
		}
		return this.executeCount(sql.toString(), param.toArray());
	}

	
	@Override
	public boolean insert(OperateLog operateLog) throws SQLException {
		String sql = "insert into operate_log (id,moduleName,operateTime,operateType,operateUserId,ip,operateUserName,operateResult,operateDataId,remark)"+
										"values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {
			operateLog.getId(),
			operateLog.getModuleName(),
			operateLog.getOperateTime(),
			operateLog.getOperateType(),
			operateLog.getOperateUserId(),
			operateLog.getIp(),
			operateLog.getOperateUserName(),
			operateLog.getOperateResult(),
			operateLog.getOperateDataId(),
			operateLog.getRemark()
		};
		int i  = this.executeUpdate(sql, params);
		return i>0;
	}
	

}
