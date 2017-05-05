package com.boyuyun.base.course.biz;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.course.entity.TeacherCourse;

public interface TeacherCourseBiz  {

	public List<TeacherCourse> getTeacherCourse(String teacherId)throws SQLException;
	public void deleteByTeacher(String teacherId)throws SQLException;
	int insert(TeacherCourse course) throws SQLException;
	int addAll(List<TeacherCourse> teacherCourses) throws SQLException;
}
