package com.boyuyun.base.sys.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sys.dao.GradeDao;
import com.boyuyun.base.sys.entity.Grade;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GradeDaoImpl extends BaseDAO implements GradeDao {

	@Override
	public boolean insert(Grade grade) throws SQLException {
		String sql ="insert into SYS_GRADE (ID, NAME, SHORTNAME, SORTNUM, STAGE)"+
				    "			    values (?,?,?,?,?)";
		Object[] param = {
				grade.getId(),
				grade.getName(),
				grade.getShortName(),
				grade.getSortNum(),
				grade.getStage()
		};		
		return this.executeUpdate(sql, param)>0;
	}

	@Override
	public boolean update(Grade grade) throws SQLException {
		String sql ="update SYS_GRADE set NAME = ?,SHORTNAME = ?,SORTNUM = ?,STAGE = ?  where ID = ?";
		Object[] param = {
				grade.getName(),
				grade.getShortName(),
				grade.getSortNum(),
				grade.getStage(),
				grade.getId()
		};		
		return this.executeUpdate(sql, param)>0;
	}

	@Override
	public boolean delete(Grade grade) throws SQLException {
		String sql ="delete from SYS_GRADE where ID = ?";
		return this.executeUpdate(sql, new Object[]{grade.getId()})>0;
	}

	@Override
	public List<Grade> getListNonePaged(Grade grade) throws SQLException {
		StringBuffer sql = new StringBuffer("select ID, NAME, SHORTNAME, SORTNUM, STAGE from SYS_GRADE where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(grade.getName())){
			sql.append("AND NAME LIKE concat(concat('%',?),'%')");
			param.add(grade.getName());
		}
		sql.append(" order by sortNum ");
		ResultSetHandler handler = new BeanListHandler(Grade.class);
		return this.executeQuery(sql.toString(),param.toArray(), handler);
	}

	@Override
	public List<Grade> getListPaged(Grade grade) throws SQLException {
		StringBuffer sql = new StringBuffer("select ID, NAME, SHORTNAME, SORTNUM, STAGE from SYS_GRADE where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(grade.getName())){
			sql.append("AND NAME LIKE concat(concat('%',?),'%')");
			param.add(grade.getName());
		}	
		sql.append("ORDER BY sortnum asc");
		ResultSetHandler handler = new BeanListHandler(Grade.class);
		return this.executeQueryPage(sql.toString(), grade.getBegin(), 
				grade.getEnd(), param.toArray(), handler);
	}

	@Override
	public int getListPagedCount(Grade grade) throws SQLException {
		StringBuffer sql = new StringBuffer("select count(*) from SYS_GRADE where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(grade.getName())){
			sql.append("AND NAME LIKE concat(concat('%',?),'%')");
			param.add(grade.getName());
		}
		return this.executeCount(sql.toString(), param.toArray());
	}

	@Override
	public Grade get(String grade) throws SQLException {
		String sql = "select ID, NAME, SHORTNAME, SORTNUM, STAGE from SYS_GRADE where id=?";
		ResultSetHandler handler = new BeanHandler(Grade.class);
		return (Grade) this.executeQueryObject(sql, new String[]{grade}, handler);
	}
	@Override
	public Grade getBySortNum(int sortNum) throws Exception {
		String sql="SELECT grade.id, grade.`name`, grade.shortname, grade.stage, grade.sortnum FROM sys_grade grade WHERE grade.sortnum=? ";
		ResultSetHandler handler = new BeanHandler(Grade.class);
		return (Grade) this.executeQueryObject(sql, new Object[]{sortNum}, handler);
	}
	@Override
	public Grade get(String name, Integer stage) throws SQLException {
		StringBuffer sql = new StringBuffer("SELECT t1.id, t1.`name`, t1.shortname, t1.stage, t1.sortnum FROM sys_grade t1 "
				+ "WHERE 1 = 1");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(name)){
			sql.append(" and t1.`name` like concat(concat('%',?),'%')");
			param.add(name);
		}
		if (stage>-1){
			sql.append(" and t1.stage=?");
			param.add(stage);
		}
		ResultSetHandler handler = new BeanHandler(Grade.class);
		return (Grade) this.executeQueryObject(sql.toString(), param.toArray(), handler);
	}

	@Override
	public boolean deleteAll(List<Grade> grades) throws SQLException {
		boolean result = true;
		if(grades!=null){
			for (Iterator iterator = grades.iterator(); iterator.hasNext();) {
				Grade grade = (Grade) iterator.next();
				result&=this.delete(grade);
			}
		}
		return result;
	}

	@Override
	public int getListByOrgGradeCount(String fid) throws SQLException {
		String sql = "select count(1) from school_grade where sysGradeId=? ";
		List param = new ArrayList();
		param.add(fid);
		return this.executeCount(sql, param.toArray());
	}
	@Override
	public int getMaxSortNum() throws SQLException {
		String sql = "select case  when max(sortnum) IS NULL then '0' else max(sortnum) end from sys_grade ";
		return executeCount(sql);
	}
	@Override
	public int getSortNum(String sortnum,String id) throws SQLException {
		String sql = "select count(*) from sys_grade where sortnum=? and id!=?";
		List param = new ArrayList();
		param.add(sortnum);
		param.add(id);
		return this.executeCount(sql, param.toArray());
	}


}
