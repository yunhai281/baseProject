package com.boyuyun.base.sso.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sso.dao.SSOLogDao;
import com.boyuyun.base.sso.entity.SSOLog;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SSOLogDaoImpl extends BaseDAO implements SSOLogDao {

	@Override
	public List<SSOLog> getListPaged(SSOLog log) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from sso_login_log where 1=1");
		List param=new ArrayList<>();
		if(!Strings.isNullOrEmpty(log.getUserName()))
		{
			sql.append(" and username like ?");
			param.add("%"+log.getUserName()+"%");
		}
		if(log.getLoginResult()!=0)
		{
			sql.append(" and loginResult = ?");
			param.add(log.getLoginResult());
		}
		if(!Strings.isNullOrEmpty(log.getBloginTime()))
		{
			sql.append(" and Date(loginTime) >= ?");
			param.add(log.getBloginTime());
		}
		if(!Strings.isNullOrEmpty(log.getEloginTime()))
		{
			sql.append(" and Date(loginTime) <= ?");
			param.add(log.getEloginTime());
		}
		sql.append(" order by loginTime desc");
		BeanListHandler handler = new BeanListHandler(SSOLog.class);
		return this.executeQueryPage(sql.toString(), log.getBegin(), log.getEnd(), param.toArray(), handler);
	}

	@Override
	public int getListPagedCount(SSOLog log) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from sso_login_log where 1=1");
		List param=new ArrayList<>();
		if(!Strings.isNullOrEmpty(log.getUserName()))
		{
			sql.append(" and username like ?");
			param.add("%"+log.getUserName()+"%");
		}
		if(log.getLoginResult()!=0)
		{
			sql.append(" and loginResult = ?");
			param.add(log.getLoginResult());
		}
		if(!Strings.isNullOrEmpty(log.getBloginTime()))
		{
			sql.append(" and Date(loginTime) >= ?");
			param.add(log.getBloginTime());
		}
		if(!Strings.isNullOrEmpty(log.getEloginTime()))
		{
			sql.append(" and Date(loginTime) <= ?");
			param.add(log.getEloginTime());
		}
		return this.executeCount(sql.toString(), param.toArray());
	}

}
