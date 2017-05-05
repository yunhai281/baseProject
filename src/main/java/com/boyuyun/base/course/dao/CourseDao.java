package com.boyuyun.base.course.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.course.entity.Course;


public interface CourseDao {
	public boolean insert(Course course)throws SQLException;
	public boolean update(Course course)throws SQLException;
	public List<Course> getListPaged(Course app)throws SQLException;
	public int getListPagedCount(Course app)throws SQLException;
	public boolean delete(Course course)throws SQLException;
	public boolean deleteAll(List<Course> list)throws SQLException;
	public Course get(String id)throws SQLException;
	public List<Course> getListNonePaged(Course app)throws SQLException;
	public List<Course> getCourseBy(String name) throws SQLException;
	public int getMaxSortNum()throws SQLException;
}