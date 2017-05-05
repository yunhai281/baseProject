package com.boyuyun.base.org.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.org.entity.SchoolGrade;
import com.boyuyun.base.util.consts.Stage;

public interface SchoolGradeDao {
	public List<SchoolGrade> getListNonePaged(SchoolGrade grade)throws Exception;
	public List<SchoolGrade> getListPaged(SchoolGrade grade)throws Exception;
	public int getListPagedCount(SchoolGrade grade)throws Exception;
	public boolean insert(SchoolGrade grade)throws Exception;
	public boolean update(SchoolGrade grade)throws Exception;
	public boolean delete(SchoolGrade grade)throws Exception;
	public boolean deleteAll(List<SchoolGrade> grades)throws Exception;
	public SchoolGrade get(String id)throws Exception;
	public SchoolGrade get(String gradeName,String schoolId)throws Exception;
	
	/**
	 * 按照学校和学段查询年级
	 * @param schoolId
	 * @param stage
	 * @return
	 */
	public List<SchoolGrade> getSchoolGradeBySchoolAndStage(String schoolId,Stage stage)throws Exception;
	
	public List<SchoolGrade> getSchoolGradeBySchoolAndStage(String schoolId) throws Exception;

	/**
	 * 删除学校年级时,查看有没有关联班级
	 * fid   class外键
	 */
	public int getListByOrgClassCount(String fid)throws Exception;
	
	public int getMaxSortNum()throws SQLException;
}
