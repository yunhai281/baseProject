package com.boyuyun.base.org.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.org.dao.SchoolGradeDao;
import com.boyuyun.base.org.entity.SchoolGrade;
import com.boyuyun.base.util.consts.Stage;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;

@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SchoolGradeDaoImpl extends BaseDAO implements SchoolGradeDao {

	@Override
	public List<SchoolGrade> getListNonePaged(SchoolGrade grade) throws Exception {
		StringBuffer sql = new StringBuffer("select * from school_grade where 1=1 ");
		List param = new ArrayList();
		if (!Strings.isNullOrEmpty(grade.getSchoolId())) {
			sql.append(" and schoolId=? ");
			param.add(grade.getSchoolId());
		}
		if (!Strings.isNullOrEmpty(grade.getName())) {
			sql.append(" and NAME LIKE  concat(concat('%',?),'%') ");
			param.add(grade.getName());
		}
		ResultSetHandler handler = new BeanListHandler(SchoolGrade.class);
		return this.executeQuery(sql.toString(), param.toArray(), handler);
	}

	@Override
	public boolean insert(SchoolGrade grade) throws Exception {
		String sql = "insert into SCHOOL_GRADE (ID, NAME, SHORTNAME, schoolId, CREATETIME, SORTNUM ,sysGradeId)"
				+ " values(?,?,?,?,?,?,?) ";
		Object[] param = new Object[] { grade.getId(), grade.getName(), grade.getShortName(), grade.getSchoolId(), grade.getCreateTime(),
				grade.getSortNum(), grade.getSysGradeId() };
		int i = this.executeUpdate(sql, param);
		return i > 0;
	}

	@Override
	public boolean update(SchoolGrade grade) throws Exception {
		String sql = "update SCHOOL_GRADE set NAME = ?,SHORTNAME = ?,CREATETIME = ?,SORTNUM = ?,sysGradeId =?  where ID =?";
		Object[] param = new Object[] { grade.getName(), grade.getShortName(), grade.getCreateTime(),
				grade.getSortNum(), grade.getSysGradeId(), grade.getId() };
		int i = this.executeUpdate(sql, param);
		return i > 0;
	}

	@Override
	public boolean delete(SchoolGrade grade) throws Exception {
		String sql = "delete from school_grade where id=?";
		int i = this.executeUpdate(sql, new String[] { grade.getId() });
		return i > 0;
	}

	@Override
	public boolean deleteAll(List<SchoolGrade> grades) throws Exception {
		boolean result = true;
		if (grades != null) {
			for (Iterator iterator = grades.iterator(); iterator.hasNext();) {
				SchoolGrade schoolGrade = (SchoolGrade) iterator.next();
				result &= delete(schoolGrade);
			}
		}
		return result;
	}

	@Override
	public SchoolGrade get(String id) throws Exception {
		StringBuffer sql = new StringBuffer("select t1.ID, t1.NAME, t1.SHORTNAME, t1.schoolId as SCHOOLID, t1.CREATETIME, t1.SORTNUM, "
				+ "t2.id as sysgradeid ,t2.name as sysgradename,t2.shortname as sysgradeshortname,t2.stage as sysgradestage,school.Name as SCHOOLNAME "
				+ "from SCHOOL_GRADE t1 left join sys_grade t2 on t1.sysGradeId=t2.id left join school on school.id=t1.schoolId where 1=1");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(id)){
			sql.append(" and t1.ID =? ");
			param.add(id);
		}
		ResultSetHandler<SchoolGrade> handler =  new BeanHandler(SchoolGrade.class);
		return (SchoolGrade) executeQueryObject(sql.toString(), param.toArray(), handler);
	}

	@Override
	public SchoolGrade get(String gradeName, String schoolId) throws Exception {
		StringBuffer sql = new StringBuffer("SELECT t1.id, t1.`name`, t1.shortName, t1.schoolId, t1.createtime , t1.sortnum, t1.sysGradeId"
				+ " FROM school_grade t1 WHERE 1 = 1");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(schoolId)){
			sql.append(" and t1.schoolId=? ");
			param.add(schoolId);
		}
		if (!Strings.isNullOrEmpty(gradeName)) {
			sql.append(" and t1.`name` LIKE  concat(concat('%',?),'%') ");
			param.add(gradeName);
		}
		ResultSetHandler<SchoolGrade> handler =  new BeanHandler(SchoolGrade.class);
		return (SchoolGrade) executeQueryObject(sql.toString(), param.toArray(), handler);
	}
	@Override
	public List<SchoolGrade> getListPaged(SchoolGrade grade) throws Exception {
		StringBuffer sql = new StringBuffer("select t1.ID, t1.NAME, t1.SHORTNAME, t1.schoolId as SCHOOLID, t1.CREATETIME, t1.SORTNUM, "
				+ "t2.id as sysgradeid ,t2.name as sysgradename,t2.shortname as sysgradeshortname,t2.stage as sysgradestage,school.Name as SCHOOLNAME "
				+ "from SCHOOL_GRADE t1 left join sys_grade t2 on t1.sysGradeId=t2.id left join school on school.id=t1.schoolId where 1=1");
		List param = new ArrayList();
		if (!Strings.isNullOrEmpty(grade.getSchoolId())) {
			sql.append(" and school.id=? ");
			param.add(grade.getSchoolId());
		}
		if (!Strings.isNullOrEmpty(grade.getShortName())) {
			sql.append(" and t1.NAME LIKE  concat(concat('%',?),'%') ");
			param.add(grade.getShortName());
		}
		ResultSetHandler handler = new BeanListHandler(SchoolGrade.class);
		return this.executeQueryPage(sql.toString(),grade.getBegin(),grade.getEnd(), param.toArray(), handler);
	}

	@Override
	public int getListPagedCount(SchoolGrade grade) throws Exception {
		StringBuffer sql = new StringBuffer("select count(1) from school_grade where 1=1 ");
		List param = new ArrayList();
		if (!Strings.isNullOrEmpty(grade.getSchoolId())) {
			sql.append(" and schoolId=? ");
			param.add(grade.getSchoolId());
		}
		if (!Strings.isNullOrEmpty(grade.getShortName())) {
			sql.append(" and NAME LIKE  concat(concat('%',?),'%')  ");
			param.add(grade.getShortName());
		}
		return this.executeCount(sql.toString(), param.toArray());
	}

	@Override
	public List<SchoolGrade> getSchoolGradeBySchoolAndStage(String schoolId,
			Stage stage) throws Exception{
		String sql = "select grade.id,grade.name "
				+ " from school_grade grade left join " +
				"sys_grade sg on sg.id=grade.sysGradeId " +
				"where grade.schoolId=? and sg.stage=? order by grade.sortNum ";
		ResultSetHandler handler = new BeanListHandler(SchoolGrade.class);
		return this.executeQuery(sql.toString(), new Object[]{schoolId,stage.ordinal()}, handler);
	}

	/**
	 * Description 根据学校查询下面所有的年级=》为了取得该学校的学段
	 * @param schoolId
	 * @return
	 * @throws Exception 
	 * @see com.boyuyun.base.org.dao.SchoolGradeDao#getSchoolGradeBySchoolAndStage(java.lang.String)
	 */
	@Override
	public List<SchoolGrade> getSchoolGradeBySchoolAndStage(String schoolId) throws Exception{
		String sql = "select grade.id,grade.name,sg.name as sysGradeName, sg.id as sysGradeId,sg.shortname as sysGradeShortName, sg.stage as sysGradeStage  "
				+ " from school_grade grade left join " +
				" sys_grade sg on sg.id=grade.sysGradeId " +
				" where grade.schoolId=? order by grade.sortNum ";
		ResultSetHandler handler = new BeanListHandler(SchoolGrade.class);
		return this.executeQuery(sql.toString(), new Object[]{schoolId}, handler);
	}
	
	@Override
	public int getListByOrgClassCount(String fid) throws Exception {
		String sql = "select count(1) from SCHOOL_CLASS where gradeId=? ";
		List param = new ArrayList();
		param.add(fid);
		return this.executeCount(sql, param.toArray());
	}
	
	@Override
	public int getMaxSortNum() throws SQLException {
		String sql = "select case  when max(sortnum) IS NULL then '1' else max(sortnum)+1 end from school_grade";
		return executeCount(sql);
	}

}
