package com.boyuyun.base.org.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.org.dao.SchoolClassDao;
import com.boyuyun.base.org.entity.SchoolClass;
import com.boyuyun.base.util.consts.Stage;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;

@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SchoolClassDaoImpl extends BaseDAO implements SchoolClassDao {

	@Override
	public boolean insert(SchoolClass schoolClass) throws SQLException {
		String sql = "insert into SCHOOL_CLASS (ID, NAME, SHORTNAME,schoolId, gradeId, CREATETIME,SORTNUM, STATUS, BEGINYEAR,"
				+ "STATUSTIME,stageId) values (?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[] { schoolClass.getId(), schoolClass.getName(), schoolClass.getShortName(), schoolClass.getSchoolId(),
				schoolClass.getGradeId(), schoolClass.getCreateTime(), schoolClass.getSortNum(), schoolClass.getStatus(),
				schoolClass.getBeginYear(), schoolClass.getStatusTime(), schoolClass.getStageId() };
		int i = this.executeUpdate(sql, param);
		return i > 0;
	}

	@Override
	public boolean update(SchoolClass schoolClass) throws SQLException {
		String sql = "update SCHOOL_CLASS set NAME = ? ,SHORTNAME = ? ,schoolId = ? ,gradeId = ? ,CREATETIME = ? ,SORTNUM = ? ,"
				+ "STATUS = ? ,BEGINYEAR = ? ,STATUSTIME = ?,stageId = ? where ID = ? ";
		Object[] param = new Object[] { schoolClass.getName(), schoolClass.getShortName(), schoolClass.getSchoolId(),
				schoolClass.getGradeId(), schoolClass.getCreateTime(), schoolClass.getSortNum(), schoolClass.getStatus(),
				schoolClass.getBeginYear(), schoolClass.getStatusTime(), schoolClass.getStageId(), schoolClass.getId() };
		int i = this.executeUpdate(sql, param);
		return i > 0;
	}

	@Override
	public boolean delete(SchoolClass schoolClass) throws SQLException {
		String sql = "delete from SCHOOL_CLASS where id=?";
		int i = this.executeUpdate(sql, new String[] { schoolClass.getId() });
		return i > 0;
	}

	@Override
	public boolean deleteAll(List<SchoolClass> schoolClasses) throws SQLException {
		boolean result = true;
		if (schoolClasses != null) {
			for (SchoolClass schoolClass : schoolClasses) {
				result&=delete(schoolClass);
			}
		}
		return result;
	}

	@Override
	public List<SchoolClass> getListNonePaged(SchoolClass schoolClass) throws SQLException {
		StringBuffer sql = new StringBuffer("select * from SCHOOL_CLASS where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(schoolClass.getSchoolId())){
			sql.append(" and schoolId=? ");
			param.add(schoolClass.getSchoolId());
		}
		if(!Strings.isNullOrEmpty(schoolClass.getGradeId())){
			sql.append(" and gradeId=? ");
			param.add(schoolClass.getGradeId());
		}
		if(!Strings.isNullOrEmpty(schoolClass.getName())){
			sql.append(" and NAME LIKE  concat(concat('%',?),'%') ");
			param.add(schoolClass.getName());
		}
		ResultSetHandler handler = new BeanListHandler(SchoolClass.class);
		return this.executeQuery(sql.toString(),param.toArray(), handler);
	}

	@Override
	public List<SchoolClass> getListPaged(SchoolClass schoolClass) throws SQLException {
		StringBuffer sql = new StringBuffer("SELECT\n" +
				"school_class.id,\n" +
				"school_class.`name`,\n" +
				"school_class.shortname,\n" +
				"school_class.schoolId,\n" +
				"school_class.gradeId,\n" +
				"school_class.createtime,\n" +
				"school_class.`status`,\n" +
				"school_class.beginyear,\n" +
				"school_class.stageId,\n" +
				"school_grade.`name` AS gradeName,\n" +
				"school_class.statustime,\n" +
				"school.`name` AS schoolName,\n" +
				"dictionary_item.`name` AS statusName\n" +
				"FROM\n" +
				"school_class\n" +
				"LEFT JOIN school_grade ON school_class.gradeId = school_grade.id\n" +
				"LEFT JOIN school ON school_class.schoolId = school.id\n" +
				"LEFT JOIN dictionary_item ON school_class.`status` = dictionary_item.id where 1=1");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(schoolClass.getSchoolId())){
			sql.append(" and school.id=? ");
			param.add(schoolClass.getSchoolId());
		}
		
		if(!Strings.isNullOrEmpty(schoolClass.getStageId())){
			sql.append(" and stageId=? ");
			param.add(schoolClass.getStageId());
		}
		if(!Strings.isNullOrEmpty(schoolClass.getGradeId())){
			sql.append(" and school_grade.id=? ");
			param.add(schoolClass.getGradeId());
		}
		if(!Strings.isNullOrEmpty(schoolClass.getShortName())){
			sql.append(" and school_class.name LIKE  concat(concat('%',?),'%') ");
			param.add(schoolClass.getShortName());
		}
		ResultSetHandler handler = new BeanListHandler(SchoolClass.class);
		return this.executeQueryPage(sql.toString(), schoolClass.getBegin(), schoolClass.getEnd(), param.toArray(), handler);
	}

	@Override
	public int getListPagedCount(SchoolClass schoolClass) throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from SCHOOL_CLASS LEFT JOIN school_grade ON school_class.gradeId = school_grade.id LEFT JOIN school ON school_class.schoolId = school.id where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(schoolClass.getSchoolId())){
			sql.append(" and school.id=? ");
			param.add(schoolClass.getSchoolId());
		}
		
		if(!Strings.isNullOrEmpty(schoolClass.getStageId())){
			sql.append(" and stageId=? ");
			param.add(schoolClass.getStageId());
		}
		if(!Strings.isNullOrEmpty(schoolClass.getGradeId())){
			sql.append(" and school_grade.id=? ");
			param.add(schoolClass.getGradeId());
		}
		if(!Strings.isNullOrEmpty(schoolClass.getShortName())){
			sql.append(" and school_class.name LIKE  concat(concat('%',?),'%') ");
			param.add(schoolClass.getShortName());
		}
		return this.executeCount(sql.toString(), param.toArray());
	}

	@Override
	public SchoolClass get(String id) throws SQLException {
		StringBuffer sql = new StringBuffer("SELECT\n" +
				"school_class.id,\n" +
				"school_class.`name`,\n" +
				"school_class.shortname,\n" +
				"school_class.schoolId,\n" +
				"school_class.gradeId,\n" +
				"school_class.createtime,\n" +
				"school_class.`status`,\n" +
				"school_class.beginyear,\n" +
				"school_class.stageId,\n" +
				"school_grade.`name` AS gradeName,\n" +
				"school_class.statustime,\n" +
				"school.`name` AS schoolName,\n" +
				"dictionary_item.`name` AS statusName\n" +
				"FROM\n" +
				"school_class\n" +
				"LEFT JOIN school_grade ON school_class.gradeId = school_grade.id\n" +
				"LEFT JOIN school ON school_class.schoolId = school.id\n" +
				"LEFT JOIN dictionary_item ON school_class.`status` = dictionary_item.id where 1=1");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(id)){
			sql.append(" and school_class.id=? ");
			param.add(id);
		}
		ResultSetHandler<SchoolClass> handler =  new BeanHandler(SchoolClass.class);
		return (SchoolClass) executeQueryObject(sql.toString(), param.toArray(), handler);
	}

	@Override
	public SchoolClass get(SchoolClass schoolClass) throws SQLException {
		StringBuffer sql = new StringBuffer("SELECT t1.id, t1.`name`, t1.shortname, t1.schoolId, t1.gradeId , t1.createtime, t1.sortnum, "
				+ "t1.`status`, t1.beginyear, t1.statustime , t1.stageId FROM school_class t1 WHERE 1 = 1");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(schoolClass.getSchoolId())){
			sql.append(" and t1.schoolId=? ");
			param.add(schoolClass.getSchoolId());
		}
		if(!Strings.isNullOrEmpty(schoolClass.getName())){
			sql.append(" and t1.`name` LIKE  concat(concat('%',?),'%')  ");
			param.add(schoolClass.getName());
		}
		if(!Strings.isNullOrEmpty(schoolClass.getGradeId())){
			sql.append(" and t1.gradeId=? ");
			param.add(schoolClass.getGradeId());
		}
		ResultSetHandler<SchoolClass> handler =  new BeanHandler(SchoolClass.class);
		return (SchoolClass) executeQueryObject(sql.toString(), param.toArray(), handler);
	}
	
	@Override
	public List<SchoolClass> getSchoolClassListBy(String schoolId,Stage stage, String gradeId ) throws SQLException {
		StringBuffer sql = new StringBuffer("SELECT\n" +
				"school_class.id,\n" +
				"school_class.`name`,\n" +
				"school_class.shortname,\n" +
				"school_class.schoolId,\n" +
				"school_class.gradeId,\n" +
				"school_class.createtime,\n" +
				"school_class.`status`,\n" +
				"school_class.beginyear,\n" +
				"school_class.stageId,\n" +
				"school_grade.`name` AS gradeName,\n" +
				"school_class.statustime,\n" +
				"school.`name` AS schoolName,\n" +
				"dictionary_item.`name` AS statusName\n" +
				"FROM\n" +
				"school_class\n" +
				"LEFT JOIN school_grade ON school_class.gradeId = school_grade.id\n" +
				"LEFT JOIN school ON school_class.schoolId = school.id\n" +
				"LEFT JOIN dictionary_item ON school_class.`status` = dictionary_item.id where 1=1");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(schoolId)){
			sql.append(" and school.id=? ");
			param.add(schoolId);
		}
		
		if(stage!=null){
			sql.append(" and stageId=? ");
			param.add(stage.ordinal());
		}
		if(!Strings.isNullOrEmpty(gradeId)){
			sql.append(" and school_grade.id=? ");
			param.add(gradeId);
		} 
		ResultSetHandler handler = new BeanListHandler(SchoolClass.class);
		return this.executeQuery(sql.toString(), param.toArray(), handler);
	}
	@Override
	public int getMaxSortNum() throws SQLException {
		String sql = "select case  when max(sortnum) IS NULL then '1' else max(sortnum)+1 end from school_class";
		return executeCount(sql);
	}
}
