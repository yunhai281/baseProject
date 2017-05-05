package com.boyuyun.base.user.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.user.dao.ParentDao;
import com.boyuyun.base.user.entity.Parent;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ParentDaoImpl extends BaseDAO implements ParentDao {
	@Override
	public int insert(Parent parent) throws SQLException {
		String sql = "insert into parent(id,work,education)values(?,?,?)" ;
//		List params = new  ArrayList();
//		params.add(parent.getId());
		Object[] param = {
				parent.getId(),
				parent.getWork(),
				parent.getEducation()
		};
		//params.add(parent.getRelationId());
		int result = super.executeUpdate(sql, param);
		return result;
	}

	@Override
	public int delete(String id) throws SQLException {
		String sql = " delete from parent where id=? ";
		List params = new  ArrayList();
		params.add(id);
		int result = super.executeUpdate(sql, params.toArray());
		return result;
	}

	@Override
	public int update(Parent parent) throws SQLException {
		String strSql = "update parent set work = ?,education=? where ID = ?";
		List params = new  ArrayList();
		params.add(parent.getWork());
		params.add(parent.getEducation());
		params.add(parent.getId());
		int result = super.executeUpdate(strSql, params.toArray());
		return result;
	}
	
	public Parent getParent(Parent parent) throws SQLException {
		String sql =	"    select "+
						"		t.id,t.work,t.education,u.realName,u.userName,u.sex,u.userName,u.pwd,u.mobile,u.email,"+
						"		d1.name as sexName, d2.name as educationName" +
						"    from  parent  t " +
						"    left join user_base u on u.id=t.id"+
						"    left join dictionary_item d1 on d1.id=u.sex "+
						"    left join dictionary_item d2 on d2.id=t.education"+
						" 	 where t.Id=? ";
		return (Parent) super.executeQueryObject(sql, new Object []{parent.getId()}, new BeanHandler(parent.getClass()));
	}

	@Override
	public List<Parent> getList(Parent parent) throws SQLException {
		List paramList = new ArrayList();
		String sql =  " select "+
						"	t.id,t.work, u.realName,u.userName,u.sex,u.enable,u.userName,u.pwd,u.mobile,u.email,"+
						 "	 d1.name as sexName,d2.name as educationName "+
						 " from  parent  t " +
						 " left join user_base u on u.id=t.id"+
						 " left join dictionary_item d1 on d1.id=u.sex "+
						 " left join dictionary_item d2 on d2.id=t.education"+
						 " where 1=1 ";
		if (!Strings.isNullOrEmpty(parent.getRealName())) {
			sql += " and u.realName like concat(concat('%',?),'%') ";
			paramList.add(parent.getRealName());
		}
		if (!Strings.isNullOrEmpty(parent.getSchoolId())) {
			sql += " and u.id in (select relation.parentId from student as std,student_parent_relation as relation " +
					"where std.schoolId in ('"+StringUtils.join(parent.getSchoolId().split(","), "','")+"') ) ";
		}
		if(parent.getBegin()>0&&parent.getEnd()>0){
			sql+=" limit ?,?  ";
			paramList.add(parent.getBegin());
			paramList.add(parent.getEnd());
		}
		Object [] param = paramList.toArray();
		return super.executeQuery(sql, param, new BeanListHandler(parent.getClass()));
	}

	@Override
	public int getListPagedCount(Parent parent) throws SQLException {
		List paramList = new ArrayList();
		String sql  = "select " +
					  "    count(0) " +
					  " from parent t " +
					  " left join user_base u on u.id=t.id "+
					  " left join dictionary_item d1 on d1.id=u.sex "+
					  " left join dictionary_item d2 on d2.id=t.education"+
					  " where 1=1 ";
		if (!Strings.isNullOrEmpty(parent.getRealName())) {
			sql += " and u.realName like concat(concat('%',?),'%') ";
			paramList.add(parent.getRealName());
		}
		if (!Strings.isNullOrEmpty(parent.getSchoolId())) {
			sql += " and u.id in (select relation.parentId from student as std,student_parent_relation as relation " +
					"where std.schoolId in ('"+StringUtils.join(parent.getSchoolId().split(","), "','")+"') ) ";
		}
		return super.executeCount(sql, paramList.toArray());
	}

}
