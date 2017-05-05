package com.boyuyun.base.course.biz;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.course.entity.Course;


public interface CourseBiz{
	public boolean add(Course course)throws Exception;
	public boolean update(Course course)throws Exception;
	public List<Course> getListPaged(Course app)throws Exception;
	public int getListPagedCount(Course app)throws Exception;
	public boolean delete(Course course)throws Exception;
	public boolean deleteAll(List<Course> list)throws Exception;
	public Course get(String id)throws Exception;
	public List<Course> getListNonePaged(Course app)throws Exception;
	public int getMaxSortNum()throws SQLException;
}
