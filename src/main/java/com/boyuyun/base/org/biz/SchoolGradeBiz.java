package com.boyuyun.base.org.biz;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.org.entity.SchoolGrade;
import com.boyuyun.base.util.consts.Stage;

public interface SchoolGradeBiz {
	public List<SchoolGrade> getListNonePaged(SchoolGrade grade)throws Exception;
	public List<SchoolGrade> getListPaged(SchoolGrade grade)throws Exception;
	public int getListPagedCount(SchoolGrade grade)throws Exception;
	/**
	 * 每次点击一级节点就会调用加载子节点
	 * 
	 */
	public boolean add(SchoolGrade grade)throws Exception;
	public boolean update(SchoolGrade grade)throws Exception;
	public boolean delete(SchoolGrade grade)throws Exception;
	public boolean deleteAll(List<SchoolGrade> grades)throws Exception;
	public SchoolGrade get(String id)throws Exception;
	
	public List<SchoolGrade> getSchoolGradeBySchoolAndStage(String schoolId,Stage stage)throws Exception;
	
	public List<SchoolGrade> getSchoolGradeBySchoolAndStage(String schoolId) throws Exception;
	/**
	 * 验证年纪是否关联班级
	 * @param fid Schoolclass外键
	 * @return
	 * @throws Exception
	 */
	public boolean getListByOrgClassCount(String fid)throws Exception; 
	/**
	 * 获取schoolGrade表中最大的sortNum+1,如果列表为空返回1
	 */
	public int getMaxSortNum()throws SQLException;
}
