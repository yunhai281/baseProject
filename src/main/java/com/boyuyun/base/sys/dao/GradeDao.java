package com.boyuyun.base.sys.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sys.entity.Grade;

public interface GradeDao {
	boolean insert(Grade grade)throws SQLException;
	boolean update(Grade grade)throws SQLException;
	boolean delete(Grade grade)throws SQLException;
	List<Grade> getListNonePaged(Grade grade)throws SQLException;
	List<Grade> getListPaged(Grade grade)throws SQLException;
	int getListPagedCount(Grade grade)throws SQLException;
	Grade get(String grade)throws SQLException;
	Grade getBySortNum(int sortNum)throws Exception;
	Grade get(String name,Integer stage)throws SQLException;
	boolean deleteAll(List<Grade> grades)throws SQLException;
	public int getSortNum(String sortnum,String id) throws SQLException;
	/**
	 * 删除前查看有没有关联学校年级
	 * @param fid
	 * @return
	 * @throws SQLException
	 */
	int getListByOrgGradeCount(String fid)throws SQLException;
	
	public int getMaxSortNum()throws SQLException;
}
