package com.boyuyun.base.org.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.util.consts.Stage;


public interface SchoolDao {
	/**
	 * 查询子学校列表，并带有孙学校数目和年级数目 数据
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<School> getByParentIdWidthChildAndGradeNums(String id) throws SQLException;
	/**
	 * 查询一个学校所有学段（根据年级查询）
	 * @param schoolId
	 * @return
	 * @throws SQLException
	 */
	public List<Stage> getSchoolStageList(String schoolId)throws SQLException;
	public int getListPagedCount(School school) throws SQLException;
	public List<School> getListPaged(School school) throws SQLException;
	public boolean insert(School school) throws SQLException;
	public boolean update(School school) throws SQLException;
	public boolean delete(String id) throws SQLException;
	public School get(String id) throws SQLException;
	List<School> selectAll() throws SQLException;
	
	List<School> selectAllParent() throws SQLException;
	/**
	 * 查询所有子学校
	 * @param id
	 * @return
	 */
	public List<School> getByParentId(String id) throws SQLException;
	public List<School> getByGovernmentId(String id)  throws SQLException;
	
	List<School> findByGovIdsMap(List item) throws SQLException;
	
	List<School> selectSchoolByName(String name) throws SQLException;
	
	School selectFullByPrimaryKey(String id) throws SQLException;
	
	public int getListCodeCount(String code) throws SQLException ;
	
	/**
	 * 根据学校的组织机构代码查出学校
	 */
	public School getSchoolByCode(String code) throws SQLException;
	
	public int getSerialNumber() throws SQLException;
	
	public List<School> selectSchoolName(String name) throws SQLException;
	
	public int getSchoolJianPinCount(String jianpin) throws SQLException;
	public int getSchoolPinYinCount(String pinyin) throws SQLException;
	public int getSchoolSerialNumberCount(String serialNumber,String code,String id) throws SQLException; 
	
	public int validatName(String name,String id) throws SQLException;
}