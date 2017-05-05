package com.boyuyun.base.sso.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sso.dao.SSOApplicationDao;
import com.boyuyun.base.sso.entity.SSOApplication;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SSOApplicationDaoImpl extends BaseDAO implements SSOApplicationDao {

	public int getNextId()throws SQLException{
		String sql = "select ifnull(max(id),0)+1 from sso_services";
		return this.executeCount(sql);
	}
	@Override
	public boolean insert(SSOApplication app)throws SQLException {
		String sql = "insert into sso_services (ID, EXPRESSION_TYPE, ALLOWEDTOPROXY, ANONYMOUSACCESS, DESCRIPTION,"
				+ " ENABLED,IGNOREATTRIBUTES, NAME,applicationType,SERVICEID, SSOENABLED, THEME,evaluation_order,manufacturer,wicketed,icon,URL) "+
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		Object[] param = new Object[]{
			app.getId(),
			app.getExpressionType(),
			app.isAllowedtoproxy(),
			app.isAnonymousaccess(),
			app.getDescription(),
			app.isEnabled(),
			app.isIgnoreattributes(),
			app.getName(),
			app.getApplicationType(),
			app.getServiceid(),
			app.isSsoenabled(),
			app.getTheme(),
			app.getEvaluation_order(),
			app.getManufacturer(),
			app.isWicketed(),
			app.getIcon(),
			app.getUrl()
		};
		int i = this.executeUpdate(sql, param);
		return i>0;
	}
	public boolean insertAttributes(Integer appId,String attr,int aid)throws SQLException {
		String sql = "insert into sso_rs_attributes (RegisteredServiceImpl_id,a_name,a_id) "+
				     "       values(?,?,?);";
		Object[] param = new Object[]{
				appId,
				attr,
				aid
		};
		int i = this.executeUpdate(sql, param);
		return i>0;
	}
	
	@Override
	public SSOApplication get(int id) throws SQLException{
		String sql = "select * from sso_services where id=? order by evaluation_order";
		ResultSetHandler handler = new BeanHandler(SSOApplication.class);
		SSOApplication app = (SSOApplication) this.executeQueryObject(sql, new Object[]{id}, handler);
		return app;
	}
	
	public List getAttributes(int id)throws SQLException{
		String sql = "select a_name from sso_rs_attributes where RegisteredServiceImpl_id=? order by a_id";
		ResultSetHandler handler = new MapListHandler();
		//TODO zhuanhuan
		List list = this.executeQuery(sql.toString(), new Object[]{id}, handler);	
		List list2 = new ArrayList();
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			map.get("a_name");
			list2.add(map.get("a_name"));
		}
		return list2;
	}

	@Override
	public boolean update(SSOApplication app)throws SQLException {
		String sql = "update sso_services set EXPRESSION_TYPE = ?,ALLOWEDTOPROXY = ?,ANONYMOUSACCESS = ?, DESCRIPTION = ?,"+
				"ENABLED = ?,IGNOREATTRIBUTES = ?,NAME = ?,applicationType = ?,SERVICEID = ?,SSOENABLED =?,THEME = ?,evaluation_order=?,manufacturer=?,wicketed=?,icon=?,URL=?  where ID = ?";
		Object[] param = new Object[]{
			app.getExpressionType(),
			app.isAllowedtoproxy(),
			app.isAnonymousaccess(),
			app.getDescription(),
			app.isEnabled(),
			app.isIgnoreattributes(),
			app.getName(),
			app.getApplicationType(),
			app.getServiceid(),
			app.isSsoenabled(),
			app.getTheme(),
			app.getEvaluation_order(),
			app.getManufacturer(),
			app.isWicketed(),
			app.getIcon(),
			app.getUrl(),
			app.getId()
		};
		int i = this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean delete(SSOApplication app) throws SQLException{
		String sql = "delete from sso_services where id=?";
		int i = this.executeUpdate(sql, new Object[]{app.getId()});
		return i>0;
	}

	/**
	 * 删除关联属性
	 * @param appId
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteAttributes(Integer appId) throws SQLException{
		String sql = "delete from sso_rs_attributes where RegisteredServiceImpl_id=?";
		int i = this.executeUpdate(sql, new Object[]{appId});
		return i>0;
	}
	
	
	@Override
	public List<SSOApplication> getListPaged(SSOApplication app) throws SQLException{
		StringBuffer sql = new StringBuffer("select t1.ID,t1.EXPRESSION_TYPE, t1.ALLOWEDTOPROXY, t1.ANONYMOUSACCESS, t1.DESCRIPTION,"+
	                           "t1.ENABLED,t1.IGNOREATTRIBUTES, t1.NAME,t1.applicationType, t1.SERVICEID, t1.SSOENABLED, "+
				               "t1.THEME,t1.evaluation_order,t1.manufacturer,t1.wicketed,t1.icon,t1.URL,sum(t2.`used`) as usecount "+
	                           "from sso_services t1 LEFT JOIN sso_app_usage_stat t2 ON t1.ID = t2.appid where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(app.getName())){
			sql.append(" and name like concat(concat('%',?),'%') ");
			param.add(app.getName());
		}
		sql.append("GROUP BY t1.ID,t1.EXPRESSION_TYPE, t1.ALLOWEDTOPROXY, t1.ANONYMOUSACCESS, t1.DESCRIPTION,"+
		           "t1.ENABLED,t1.IGNOREATTRIBUTES, t1.NAME,t1.applicationType, t1.SERVICEID, t1.SSOENABLED, "+
				   "t1.THEME,t1.evaluation_order,t1.manufacturer,t1.wicketed,t1.icon,t1.URL   ");
		sql.append("order by t1.id asc");
		ResultSetHandler handler = new BeanListHandler(SSOApplication.class);
		List list = this.executeQueryPage(sql.toString(), app.getBegin(), app.getEnd(), param.toArray(), handler);
		return list;
	}

	@Override
	public int getListPagedCount(SSOApplication app) throws SQLException{
		StringBuffer sql = new StringBuffer("select count(1) from sso_services where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(app.getName())){
			sql.append(" and name like concat(concat('%',?),'%') ");
			param.add(app.getName());
		}
		return this.executeCount(sql.toString(), param.toArray());
	}
	@Override
	public List<SSOApplication> getAllEnabledAppList() throws Exception {
		String sql="select * from sso_services where enabled= true ";
		ResultSetHandler handler = new BeanListHandler(SSOApplication.class);
		List list = this.executeQuery(sql.toString(),  handler);
		return list;
	}

	@Override
	public int getMaxSortNum() throws SQLException {
		String sql = "select case  when max(evaluation_order) IS NULL then '1' else max(evaluation_order)+1 end from sso_services";
		return executeCount(sql);
	}
	
	@Override
	public int getMaxOrder() throws SQLException{
		StringBuffer sql = new StringBuffer("select max(evaluation_order) from sso_services where 1=1 ");
		return this.executeCount(sql.toString());
	}
}
