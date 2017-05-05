package com.boyuyun.base.org.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.org.entity.SchoolClass;
import com.boyuyun.base.util.consts.Stage;

public interface SchoolClassDao {
	public boolean insert(SchoolClass schoolClass)throws SQLException;
	public boolean update(SchoolClass schoolClass)throws SQLException;
	public boolean delete(SchoolClass schoolClass)throws SQLException;
	public boolean deleteAll(List<SchoolClass> schoolClasses)throws SQLException;
	public List<SchoolClass> getListNonePaged(SchoolClass schoolClass)throws SQLException;
	public List<SchoolClass> getListPaged(SchoolClass schoolClass)throws SQLException;
	public int getListPagedCount(SchoolClass schoolClass)throws SQLException;
	public SchoolClass get(String id)throws SQLException;
	
	/**
	 * 根据schoolId、name、gradeId查找
	 */
	public SchoolClass get(SchoolClass schoolClass)throws SQLException;
	public List<SchoolClass> getSchoolClassListBy(String schoolId,Stage stage, String gradeId ) throws SQLException ;
	public int getMaxSortNum()throws SQLException;
}
