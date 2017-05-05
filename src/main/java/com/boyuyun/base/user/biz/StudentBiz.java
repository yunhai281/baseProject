package com.boyuyun.base.user.biz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.boyuyun.base.user.entity.Student;


public interface StudentBiz {
	public boolean add(Student student)throws SQLException;
	public boolean update(Student student)throws SQLException;
	public boolean delete(Student student)throws SQLException;
	public boolean deleteAll(List<Student> students)throws SQLException;
	public List<Student> getListNonePaged(Student student)throws SQLException;
	public List<Student> getListPaged(Student student)throws SQLException;
	public int getListPagedCount(Student student)throws SQLException;
	public Student get(String id)throws SQLException;
	/**
	 * @param contentMap
	 * @param listForIds 此参数用于存放插入后的id
	 * @return
	 * @throws Exception
	 */
	public String insertImportStudentExcel(Map<Integer, List> contentMap,List listForIds) throws Exception;
	/**
	 * @param contentMap
	 * @param listForIds 此参数用于存放插入后的id
	 * @return
	 * @throws Exception
	 */
	public String importNationalStudentExcel(Map<Integer, List> contentMap,List listForIds) throws Exception;
}
