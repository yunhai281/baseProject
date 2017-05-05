package com.boyuyun.base.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.user.entity.StudentParentRelation;

public interface StudentParentDao {
	int insert(StudentParentRelation po) throws SQLException;
	int delete(String parentId) throws SQLException;
	List<StudentParentRelation> getList(StudentParentRelation po) throws SQLException;
}
