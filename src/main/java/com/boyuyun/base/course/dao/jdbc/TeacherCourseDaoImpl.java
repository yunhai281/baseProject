package com.boyuyun.base.course.dao.jdbc;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.course.dao.TeacherCourseDao;
import com.boyuyun.base.course.entity.TeacherCourse;
import com.dhcc.common.db.BaseDAO;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TeacherCourseDaoImpl extends BaseDAO implements TeacherCourseDao {

	@Override
	public List<TeacherCourse> getTeacherCourse(String teacherId) throws SQLException {
		StringBuffer sql  = new StringBuffer("select * from teacher_sys_course where teacherId=?");
		Object[] param = new Object[]{teacherId};
		ResultSetHandler handler =  new BeanListHandler(TeacherCourse.class);
		return executeQuery(sql.toString(), param, handler);
	}

	@Override
	public int deleteByTeacher(String teacherId) throws SQLException {
		StringBuffer sql  = new StringBuffer("delete from teacher_sys_course where teacherId=?");
		Object[] param = new Object[]{teacherId};
		return super.executeUpdate(sql.toString(), param);
	}

	@Override
	public int insert(TeacherCourse course) throws SQLException {
		StringBuffer sql  = new StringBuffer("insert into teacher_sys_course(id,teacherId,courseId) values(?,?,?)");
		Object[] param = new Object[]{course.getId(),course.getTeacherId(),course.getCourseId()};
		return super.executeUpdate(sql.toString(), param);
	}

}
