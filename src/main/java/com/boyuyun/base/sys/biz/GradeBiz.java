package com.boyuyun.base.sys.biz;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sys.entity.Grade;



public interface GradeBiz {
	public boolean add(Grade grade)throws Exception;
	public boolean update(Grade grade)throws Exception;
	public boolean delete(Grade grade)throws Exception;
	public List<Grade> getListNonePaged(Grade grade)throws Exception;
	public List<Grade> getListPaged(Grade grade)throws Exception;
	public int getListPagedCount(Grade grade)throws Exception;
	public Grade get(String grade)throws Exception;
	public Grade getBySortNum(int sortNum)throws Exception;
	public boolean deleteAll(List<Grade> grades)throws Exception;
	public int getSortNum(String sortnum,String id) throws SQLException;
	/**
	 * 删除前查看有没有关联学校年级
	 * @param fid
	 * @return
	 * @throws SQLException
	 */
	public boolean getListByOrgGradeCount(String fid)throws SQLException;
	/**
	 * 获取Grade表中最大的sortNum,如果列表为空返回0
	 */
	public int getMaxSortNum()throws SQLException;
	
	public int moveGradeSortNum(String id,String type) throws Exception;
}
