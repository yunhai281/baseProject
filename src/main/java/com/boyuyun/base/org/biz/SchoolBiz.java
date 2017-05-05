package com.boyuyun.base.org.biz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.boyuyun.base.org.entity.School;

public interface SchoolBiz  {
	public boolean add(School school) throws Exception;
	public boolean update(School school) throws Exception;
	public boolean delete(School sch) throws Exception;
	public School get(String id) throws Exception ;
	public int getListPagedCount(School school) throws Exception;
	public List<School> getListPaged(School school) throws Exception;
	
	public List<School> selectAll() throws Exception;//选择所有的学校
	
	public List<School> selectAllParent() throws Exception;//选择所有为总校类型的学校
	/**
	 * 查询所有子学校
	 * @param id
	 * @return
	 */
	public List<School> getByParentId(String id) throws Exception;
	public List<School> getByGovernmentId(String id) throws Exception;
	/**
	 * 获取一个带学校和管理机构的混合树
	 * @param currentNodeType
	 * @param parentId
	 * @return
	 */
	public List getDynamicGovernmentAndSchoolTree(String currentNodeType,String parentId) throws Exception;
	public List getDynamicGovernmentSchoolAndGradeTree(String type, String parentId)throws Exception;
	List<School> findByGovIdsMap(List item) throws Exception;
	
	/**
	 * 根据名称，搜索学校，并以树的形式返回
	 * @author LHui
	 * @since 2017-3-13 上午10:23:37
	 * @param name
	 * @return
	 */
	public List searchByName(String name) throws Exception;
	
	public School selectFullByPrimaryKey(String id) throws Exception;
	
	public String insertImportExcel(Map<Integer, List> contentMap,List listForIds)throws Exception;
	public int getSchoolJianPinCount(String jianpin) throws SQLException;
	public int getSchoolPinYinCount(String pinyin) throws SQLException;
	public int getSchoolSerialNumberCount(String serialNumber,String code,String id) throws SQLException;
	
	public int validatName(String name,String id) throws SQLException;
}
