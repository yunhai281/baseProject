package com.boyuyun.base.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.user.entity.Student;

public interface StudentDao {
	public boolean insert(Student student)throws SQLException;
	public boolean update(Student student)throws SQLException;
	public boolean delete(Student student)throws SQLException;
	public boolean deleteAll(List<Student> students)throws SQLException;
	public List<Student> getListNonePaged(Student student)throws SQLException;
	public List<Student> getListPaged(Student student)throws SQLException;
	public int getListPagedCount(Student student)throws SQLException;
	public Student get(String id)throws SQLException;
}