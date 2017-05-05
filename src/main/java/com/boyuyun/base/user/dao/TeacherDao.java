package com.boyuyun.base.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.user.entity.Teacher;

public interface TeacherDao {
	public boolean insert(Teacher teacher)throws SQLException;
	public boolean update(Teacher teacher)throws SQLException;
	public boolean delete(Teacher teacher)throws SQLException;
	public boolean deleteAll(List<Teacher> teachers)throws SQLException;
	public boolean addAll(List<Teacher> teachers)throws SQLException;
	public List<Teacher> getListNonePaged(Teacher teacher)throws SQLException;
	public List<Teacher> getListPaged(Teacher teacher)throws SQLException;
	public int getListPagedCount(Teacher teacher)throws SQLException;
	public Teacher get(String id)throws SQLException;
	public List<Teacher> getTeacherList(Teacher teacher) throws SQLException;
	public int getTeacherNoCount(String teacherNo)throws SQLException;
	public boolean insertAll(List<Teacher> teachers) throws SQLException;
}