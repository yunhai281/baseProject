package com.boyuyun.base.course.biz.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.boyuyun.base.course.biz.TeacherCourseBiz;
import com.boyuyun.base.course.dao.TeacherCourseDao;
import com.boyuyun.base.course.entity.TeacherCourse;
@Service
public class TeacherCourseBizImpl implements TeacherCourseBiz {

	@Resource
	private TeacherCourseDao teacherCourseMapper;

	@Override
	public List<TeacherCourse> getTeacherCourse(String teacherId)throws SQLException {
		return teacherCourseMapper.getTeacherCourse(teacherId);
	}

	@Override
	public void deleteByTeacher(String teacherId) throws SQLException{
		teacherCourseMapper.deleteByTeacher(teacherId);
	}
	@Override
	public int insert(TeacherCourse course) throws SQLException {
		return teacherCourseMapper.insert(course);
	}
	@Override
	public int addAll(List<TeacherCourse> teacherCourses) throws SQLException {
		for (TeacherCourse teacherCourse : teacherCourses) {
			teacherCourseMapper.insert(teacherCourse);
		}
		return teacherCourses.size();
	}
}
