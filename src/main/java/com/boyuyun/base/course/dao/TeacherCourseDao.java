package com.boyuyun.base.course.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.course.entity.TeacherCourse;

public interface TeacherCourseDao {
    
	List<TeacherCourse> getTeacherCourse(String teacherId)throws SQLException;
	
	int deleteByTeacher(String teacherId)throws SQLException;
	
	int insert(TeacherCourse course) throws SQLException;
}