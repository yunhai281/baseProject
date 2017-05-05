package com.boyuyun.base.org.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.org.dao.GovernmentDao;
import com.boyuyun.base.org.entity.Government;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GovernmentDaoImpl extends BaseDAO implements GovernmentDao{

	@Override
	public Government get(String id) throws SQLException {
		String sql = "select * from ORG_GOVERNMENT where id=?";
		ResultSetHandler handler = new BeanHandler(Government.class);
		Government g = (Government) this.executeQueryObject(sql, new String[]{id}, handler);
		return g;
	}

	@Override
	public List<Government> selectGovRoot() throws SQLException {
		StringBuffer sql = new StringBuffer("select * from ORG_GOVERNMENT where PARENTID is null order by SEQ ");
		ResultSetHandler handler = new BeanListHandler(Government.class);
		return this.executeQuery(sql.toString(), handler);
	}

	@Override
	public List<Government> selectByParentId(String id)  throws SQLException{
		StringBuffer sql = new StringBuffer("select ");

		sql.append(" t1.ID, t1.CODE, t1.LEVELTYPE, t1.NAME, t1.SHORTNAME, t1.AREAID, t1.PARENTID, t1.SEQ,t2.NAME as PARENTNAME,");
		sql.append(" (select count(1) from ORG_GOVERNMENT t3 where t3.parentid=t1.id) as CHILDNUM, ");
		sql.append(" area.mergerName as AREANAME "); 
		
		sql.append("from ORG_GOVERNMENT t1 ");
		sql.append(" left join ORG_GOVERNMENT t2 on t1.parentid=t2.id ");
		sql.append(" left join sys_area area on area.id=t1.areaid ");
		sql.append(" where t1.PARENTID = ?");
		sql.append(" order by t1.SEQ");
		 
		ResultSetHandler handler = new BeanListHandler(Government.class);
		return this.executeQuery(sql.toString(),new String[]{id},  handler);
	}

	@Override
	public List<Government> validateGov(Government government)  throws SQLException{
		StringBuffer sql = new StringBuffer("select * from ORG_GOVERNMENT where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(government.getId())){
			sql.append(" and id=? ");
			param.add(government.getId());
		}
		if(!Strings.isNullOrEmpty(government.getAreaId())){
			sql.append(" and AREAID=? ");
			param.add(government.getAreaId());
		}
		ResultSetHandler handler = new BeanListHandler(Government.class);
		return this.executeQuery(sql.toString(),param.toArray(),  handler);
	}

	@Override
	public Government selectFullById(String id) throws SQLException {
		StringBuffer sql = new StringBuffer("select ");

		sql.append(" t1.ID, t1.CODE, t1.LEVELTYPE, t1.NAME, t1.SHORTNAME, t1.AREAID, t1.PARENTID, t1.SEQ,t2.NAME as PARENTNAME,");
		sql.append(" (select count(1) from ORG_GOVERNMENT t3 where t3.parentid=t1.id) as childNum, ");
		sql.append(" area.mergerName as AREANAME "); 
		
		sql.append(" from ORG_GOVERNMENT t1 ");
		sql.append(" left join ORG_GOVERNMENT t2 on t1.PARENTID=t2.id ");
		sql.append(" left join sys_area area on area.id=t1.areaid ");
		sql.append(" where t1.id = ?");
		sql.append(" order by t1.SEQ");
		ResultSetHandler handler = new BeanHandler(Government.class);
		return (Government) this.executeQueryObject(sql.toString(), new String[]{id}, handler);
	}

	@Override
	public List<Government> selectGovByName(String name) throws SQLException {
		StringBuffer sql = new StringBuffer("select * from ORG_GOVERNMENT where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(name)){
			sql.append(" and name like concat(concat('%',?),'%') ");
			param.add(name);
		} 
		sql.append("  order by SEQ ");
		ResultSetHandler handler = new BeanListHandler(Government.class);
		return this.executeQuery(sql.toString(),param.toArray(),  handler);
	}
	
	@Override
	public List<Government> getGovByName(String name) throws SQLException {
		StringBuffer sql = new StringBuffer("select * from ORG_GOVERNMENT where 1=1 ");
		List param = new ArrayList();
		 
		if(!Strings.isNullOrEmpty(name)){
			sql.append(" and name =? ");
			param.add(name);		
		} 
		ResultSetHandler handler = new BeanListHandler(Government.class);
		return this.executeQuery(sql.toString(),param.toArray(),  handler);
	}
	
	@Override
	public int getGovByCode(String code) throws SQLException {
		StringBuffer sql = new StringBuffer("select count(*) from ORG_GOVERNMENT where 1=1 ");
		List param = new ArrayList();
		 
		if(!Strings.isNullOrEmpty(code)){
			sql.append(" and code =? ");
			param.add(code);		
		} 
		return this.executeCount(sql.toString(), param.toArray());
	}

	@Override
	public boolean insert(Government gov) throws SQLException {
		String sql="insert into ORG_GOVERNMENT (ID, CODE, LEVELTYPE, NAME, SHORTNAME, AREAID, PARENTID, SEQ) values (?, ?, ?, ?, ?,?, ?,?)";
		Object [] param = new Object[]{
				gov.getId(),
				gov.getCode(),
				gov.getLevelType(),
				gov.getName(),
				gov.getShortName(),
				gov.getAreaId(),
				gov.getParentId(),
				gov.getSeq()
		};
		int i=this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean update(Government gov) throws SQLException {
		String sql="update ORG_GOVERNMENT set CODE = ?,LEVELTYPE = ?,NAME = ?,SHORTNAME = ?, AREAID =?,PARENTID =?,SEQ = ? where ID = ?";
		Object [] param = new Object[]{
				gov.getCode(),
				gov.getLevelType(),
				gov.getName(),
				gov.getShortName(),
				gov.getAreaId(),
				gov.getParentId(),
				gov.getSeq(),

				gov.getId(),
		};
		int i=this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean delete(String id) throws SQLException {
		String sql = "delete from ORG_GOVERNMENT where id=?";
		int i = this.executeUpdate(sql, new String[]{id});
		return i>0;
	}

}
