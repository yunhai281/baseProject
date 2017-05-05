package com.boyuyun.base.course.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.course.dao.CourseDao;
import com.boyuyun.base.course.entity.Course;
import com.boyuyun.base.util.SortCondition;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CourseDaoImpl extends BaseDAO implements CourseDao {

	@Override
	public boolean insert(Course course)throws SQLException {
		String sql = "insert into SYS_COURSE (ID, COVER, GENERALCOURSE, "
				+ "HOURS, MAINCOURSE, NAME, SORTNUM, TYPE,subjectField,subjectCode,SCHOOLID, WDCODE)values (?,?,?,?,?,?,?,?,?,?,?,?)";
		Object [] param = new Object[]{
				course.getId(),
				course.getCover(),
				course.isGeneralCourse(),
				course.getHours(),
				course.isMainCourse(),
				course.getName(),
				course.getSortNum(),
				course.getType(),
				course.getSubjectField(),
				course.getSubjectCode(),
				course.getSchoolId(),
				course.getWdCode()
		};
		int i=this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean update(Course course)throws SQLException {
		String sql = " update SYS_COURSE set COVER = ?," +
				"      GENERALCOURSE = ?," +
				"      HOURS = ?," +
				"      MAINCOURSE = ?," +
				"      NAME = ?," +
				"      SORTNUM = ?," +
				"      TYPE = ?," +
				"      subjectField = ?," +
				"      subjectCode = ?," +
				"      SCHOOLID = ?," +
				"      WDCODE = ?" +
				"    where ID = ?";
		Object [] param = new Object[]{
				course.getCover(),
				course.isGeneralCourse(),
				course.getHours(),
				course.isMainCourse(),
				course.getName(),
				course.getSortNum(),
				course.getType(),
				course.getSubjectField(),
				course.getSubjectCode(),
				course.getSchoolId(),
				course.getWdCode(),
				course.getId()
		};
		int i=this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public List<Course> getListPaged(Course course) throws SQLException{
		StringBuffer sql = new StringBuffer("select t1.*, d1.NAME as TYPENAME ,d2.NAME as subjectFieldName FROM SYS_COURSE t1 left join "
				+ " dictionary_item d1 on d1.ID = t1.TYPE left join dictionary_item d2 on d2.ID = t1.subjectField where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(course.getSchoolId())){
			sql.append(" and t1.schoolId=? ");
			param.add(course.getSchoolId());
		}
		if(!Strings.isNullOrEmpty(course.getName())){
			sql.append(" and t1.name like concat(concat('%',?),'%') ");
			param.add(course.getName());		
		}
		if(course.getType()>0){
			sql.append(" and t1.type=? ");
			param.add(course.getType());
		}
		//排序
		if(course.getSortConditions().size()>0){
			sql.append(" order by  ");
			for (Iterator iterator = course.getSortConditions().iterator(); iterator.hasNext();) {
				SortCondition condition = (SortCondition) iterator.next();
				String alias = condition.getAliasSafely();
				String sort = condition.getSortSafely();
				if(sort!=null){
					sql.append(alias==null?"":(alias+".")+sort+" "+condition.getOrderSafely());
					if(iterator.hasNext()){
						sql.append(",");
					}
				}
			}
		}
		ResultSetHandler handler = new BeanListHandler(Course.class);
		return this.executeQueryPage(sql.toString(), course.getBegin(), 
				course.getEnd(), param.toArray(), handler);
	}

	@Override
	public int getListPagedCount(Course course)throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from sys_course where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(course.getSchoolId())){
			sql.append(" and schoolId=? ");
			param.add(course.getSchoolId());
		}
		if(!Strings.isNullOrEmpty(course.getName())){
			sql.append(" and name like concat(concat('%',?),'%') ");
			param.add(course.getName());		
		}
		if(course.getType()>0){
			sql.append(" and type=? ");
			param.add(course.getType());
		}
		return this.executeCount(sql.toString(), param.toArray());
	}

	@Override
	public boolean delete(Course course)throws SQLException {
		String sql = "delete from sys_course where id=?";
		int i = this.executeUpdate(sql, new String[]{course.getId()});
		return i>0;
	}

	@Override
	public boolean deleteAll(List<Course> list)throws SQLException {
		boolean result = true;
		if(list!=null){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Course course = (Course) iterator.next();
				result&=this.delete(course);
			}
		}
		return result;
	}

	@Override
	public Course get(String id) throws SQLException{
		String sql = "select * from sys_course where id=?";
		ResultSetHandler handler = new BeanHandler(Course.class);
		return (Course) this.executeQueryObject(sql, new String[]{id}, handler);
	}
	
	@Override
	public List<Course> getCourseBy(String name) throws SQLException{
		String sql = "select * from sys_course where name=?";
		List param = new ArrayList();
		param.add(name);	
		ResultSetHandler handler = new BeanListHandler(Course.class);
		return this.executeQuery(sql.toString(),param.toArray(), handler);
	}
	
	@Override
	public List<Course> getListNonePaged(Course course)throws SQLException {
		StringBuffer sql = new StringBuffer("select * from sys_course where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(course.getSchoolId())){
			sql.append(" and schoolId=? ");
			param.add(course.getSchoolId());
		}
		if(!Strings.isNullOrEmpty(course.getName())){
			sql.append(" and name like concat(concat('%',?),'%') ");
			param.add(course.getName());		
		}
		if(course.getType()>0){
			sql.append(" and type=? ");
			param.add(course.getType());
		}
		ResultSetHandler handler = new BeanListHandler(Course.class);
		return this.executeQuery(sql.toString(),param.toArray(), handler);
	}

	@Override
	public int getMaxSortNum() throws SQLException {
		String sql = "select case  when max(sortnum) IS NULL then '1' else max(sortnum)+1 end from sys_course";
		return executeCount(sql);
	}
}
